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
import org.springframework.validation.annotation.Validated;

import com.conexa.api.excecoes.ProcessException;
import com.conexa.seguranca.credenciais.CredenciaisRepository;
import com.conexa.seguranca.token.TokenGenerator;

import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@Validated
@NoArgsConstructor(access = PROTECTED)
public class LoginProcessor implements LoginProcess {

	@Autowired
	private TokenGenerator tokenGenerator;


	@Autowired
	private AuthenticationManager authenticationManager;


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


	private  Optional<LoginOutput> process(final Optional<LoginInput> input) {
		input.orElseThrow(() -> new ProcessException("login.credenciais.obrigatorias"));

		input
		.map(LoginInput::email)
		.flatMap(credenciaisRepository::findByEmail)
		.orElseThrow(() ->  new ProcessException("login.credenciais.nao.encontradas"));

		Optional<String> token = input
				.flatMap(source -> this.autenticar(source.email(), source.senha()))
				.map(Authentication::getPrincipal)
				.map(UserDetails.class::cast)
				.map(UserDetails::getUsername)
				.flatMap(tokenGenerator::generate);

		return token.map(LoginOutput::of);
	}


	@Override
	public Optional<LoginOutput> process(@Valid final LoginInput input) {
		return this.process(ofNullable(input));
	}
}
