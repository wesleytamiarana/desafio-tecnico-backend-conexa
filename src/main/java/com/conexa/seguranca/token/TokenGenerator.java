package com.conexa.seguranca.token;

import static java.time.Instant.now;
import static java.util.Date.from;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.conexa.seguranca.token.exceptions.TokenSubjectException;

import io.jsonwebtoken.Jwts;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
@Component
@NoArgsConstructor(access = PROTECTED)
public class TokenGenerator extends TokenHandle {

	@Value("${application.security.token.duration}")
	private long duration;


	private final Optional<String> generate(final String subject, final Map<String, Object> claims) {
		String token = Jwts
				.builder()
				.subject(subject)
				.claims(claims)
				.issuedAt(from(now()))
				.expiration(from(now().plusMillis(duration)))
				.signWith(assinatura())
				.compact();

		return of(token);
	}


	public final Optional<String> generate(final String input) {
		Optional<String> subject = ofNullable(trimToNull(input));

		subject.orElseThrow(() -> new TokenSubjectException("seguranca.token.subject.obrigatorio"));

		return subject.flatMap(source -> this.generate(source, new HashMap<>()));
	}
}
