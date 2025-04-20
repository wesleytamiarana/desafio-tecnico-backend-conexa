package com.conexa.seguranca;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.replace;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.conexa.seguranca.token.Token;
import com.conexa.seguranca.token.TokenCacheManager;
import com.conexa.seguranca.token.TokenReader;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = PROTECTED)
public class SegurancaFiltro extends OncePerRequestFilter {

	private static final String bearerName = "Bearer";

	private static final String authorizationHeader = "Authorization";

	@Autowired
	private TokenReader tokenReader;

	@Autowired
	private TokenCacheManager tokenCacheManager;

	@Autowired
	private UserDetailsService userDetailsService;


	private Optional<String> autorizationHeader(final Optional<HttpServletRequest> request) {
		return request
				.map(source -> source.getHeader(authorizationHeader))
				.map(header -> replace(header, bearerName, ""))
				.map(StringUtils::trimToNull);
	}


	private void atualizarContexto(final Authentication autenticacao) {
		SecurityContextHolder.getContext().setAuthentication(autenticacao);
	}


	private void atualizarContexto(final HttpServletRequest request, final UsernamePasswordAuthenticationToken autenticacao) {
		autenticacao.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		atualizarContexto(autenticacao);
	}


	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws ServletException, IOException {
		Optional<String> autorizationHeader = this.autorizationHeader(ofNullable(request));

		if(autorizationHeader.isPresent()) {
			Token token = null;

			Optional<String> email;

			if(isNull(SecurityContextHolder.getContext().getAuthentication())) {
				email = autorizationHeader.flatMap(tokenReader::email);

				token = email
						.flatMap(tokenCacheManager::get)
						.orElseThrow(() -> new RuntimeException("seguranca.token.nao.encontrado"));

				try {
					tokenReader
					.email(token)
					.map(userDetailsService::loadUserByUsername)
					.map(source -> new UsernamePasswordAuthenticationToken(source, null, source.getAuthorities()))
					.ifPresent(auth -> atualizarContexto(request, auth));

				} catch (ExpiredJwtException e) {
					tokenCacheManager.evict(token);

					throw new RuntimeException("seguranca.token.expirado");
				}
			}
		}

		chain.doFilter(request, response);
	}
}
