package com.conexa.seguranca.credenciais;

import static java.util.Optional.of;
import static lombok.AccessLevel.PROTECTED;

import java.util.Objects;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;


@Log
@Service
@RequiredArgsConstructor(access = PROTECTED)
public class CredenciaisService {

	private Optional<Authentication> autenticacao() {
		return of(SecurityContextHolder.getContext().getAuthentication());
	}

	private Optional<UserDetails> dadosAuthenticados() {
		return autenticacao()
				.filter(Objects::nonNull)
				.filter(Authentication::isAuthenticated)
				.map(Authentication::getPrincipal)
				.map(UserDetails.class::cast);
	}


	public Optional<String> emailAutenticado() {
		return dadosAuthenticados().map(UserDetails::getUsername);
	}
}
