package com.conexa.seguranca.login;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.conexa.seguranca.credenciais.Credenciais;
import com.conexa.seguranca.credenciais.CredenciaisRepository;
import com.conexa.seguranca.token.Token;
import com.conexa.seguranca.token.TokenCacheManager;
import com.conexa.seguranca.token.TokenGenerator;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@NoArgsConstructor(access = PROTECTED)
public class LoginProcessor implements LoginProcess {

	@Autowired
	private AuthenticationManager authenticationManager;


	@Autowired
	private TokenCacheManager tokenCacheManager;

	@Autowired
	private TokenGenerator tokenGenerator;


	@Autowired
	private CredenciaisRepository credenciaisRepository;

	private Optional<Authentication> autenticar(final UsernamePasswordAuthenticationToken input) {
		Optional<Authentication> autenticacao = ofNullable(input).map(authenticationManager::authenticate);

		autenticacao.ifPresent(SecurityContextHolder.getContext()::setAuthentication);

		return autenticacao;
	}


	private Optional<Authentication> autenticar(final String email, final String senha) {
		return autenticar(new UsernamePasswordAuthenticationToken(trimToNull(email), trimToNull(senha)));
	}


	private Optional<Authentication> autenticar(final LoginInput input) {
		return ofNullable(input).flatMap(source -> this.autenticar(source.email(), source.senha()));
	}


	private Optional<String> gerarToken(final String input) {
		Optional<String> subject = ofNullable(input);

		subject.orElseThrow(() -> new IllegalArgumentException("securanca.token.geracao.subject.required"));

		Optional<Token> token = subject.flatMap(tokenCacheManager::get);

		if(!token.isPresent()) {
			Credenciais credenciais = subject
					.flatMap(credenciaisRepository::findByEmail)
					.orElseThrow(() ->  new RuntimeException("securanca.token.geracao.credenciais.nao.encontradas"));

			token = subject.flatMap(tokenGenerator::generate).map(valor -> Token.of(credenciais, valor));
		}

		try {
			token = token.flatMap(source -> tokenGenerator.extender(source).map(source::valor));
		} catch (ExpiredJwtException e) {
			token = token.flatMap(source -> tokenGenerator.generate(source.email()).map(source::valor));
		}


		return token.flatMap(tokenCacheManager::put).map(Token::valor);
	}


	private  Optional<String> process(final Optional<LoginInput> input) {
		input.orElseThrow(() -> new IllegalArgumentException("seguranca.login.dados.requeridos"));

		return input
				.flatMap(this::autenticar)
				.map(Authentication::getPrincipal)
				.map(UserDetails.class::cast)
				.map(UserDetails::getUsername)
				.flatMap(this::gerarToken);
	}


	@Override
	public Optional<String> process(final LoginInput input) {
		return this.process(ofNullable(input));
	}
}
