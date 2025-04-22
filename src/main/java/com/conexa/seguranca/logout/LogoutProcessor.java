package com.conexa.seguranca.logout;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.conexa.seguranca.token.TokenCacheManager;
import com.conexa.seguranca.token.TokenReader;
import com.conexa.seguranca.token.exceptions.TokenSubjectException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;


@Log
@Service
@Validated
@NoArgsConstructor(access = PROTECTED)
public class LogoutProcessor implements LogoutProcess {

	@Autowired
	private TokenReader tokenReader;

	@Autowired
	private TokenCacheManager tokenCacheManager;


	public void process(final Optional<String> token) {
		token
		.flatMap(tokenReader::email)
		.orElseThrow(() -> new TokenSubjectException("logout.token.invalido"));

		token.ifPresent(input -> tokenCacheManager.store(input));

		SecurityContextHolder.getContext().setAuthentication(null);
	}


	@Override
	public void process(@Valid @NotBlank final String token) {
		this.process(ofNullable(token).map(value -> trimToNull(value.replace("Bearer", ""))));
	}

}
