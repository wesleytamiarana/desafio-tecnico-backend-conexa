package com.conexa.seguranca.logout;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexa.seguranca.token.TokenCacheManager;
import com.conexa.seguranca.token.TokenReader;

import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@NoArgsConstructor(access = PROTECTED)
public class LogoutProcessor implements LogoutProcess {

	@Autowired
	private TokenReader tokenReader;

	@Autowired
	private TokenCacheManager tokenCacheManager;

	public void process(final Optional<String> token) {
		Optional<String> email = token
				.map(value -> trimToNull(value.replace("Bearer", "")))
				.flatMap(tokenReader::email);

		email.orElseThrow(() -> new RuntimeException("seguranca.logout.token.invalido"));

		email.ifPresent(tokenCacheManager::evict);

		//SecurityContextHolder.getContext().setAuthentication(null);
	}


	@Override
	public void process(final String token) {
		this.process(ofNullable(trimToNull(token)));
	}

}
