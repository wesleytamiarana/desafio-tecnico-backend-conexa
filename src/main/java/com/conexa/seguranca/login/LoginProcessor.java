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

import com.conexa.seguranca.token.criacao.CriacaoTokenProcess;

import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@NoArgsConstructor(access = PROTECTED)
public class LoginProcessor implements LoginProcess {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CriacaoTokenProcess criacaoTokenProcess;


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


	private  Optional<String> process(final Optional<LoginInput> input) {
		input.orElseThrow(() -> new IllegalArgumentException("seguranca.login.dados.requeridos"));

		return input
				.flatMap(this::autenticar)
				.map(Authentication::getPrincipal)
				.map(UserDetails.class::cast)
				.map(UserDetails::getUsername)
				.flatMap(criacaoTokenProcess::process);
	}


	@Override
	public Optional<String> process(final LoginInput input) {
		return this.process(ofNullable(input));
	}
}
