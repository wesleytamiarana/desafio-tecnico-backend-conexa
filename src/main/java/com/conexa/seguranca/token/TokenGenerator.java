package com.conexa.seguranca.token;

import static java.time.Instant.now;
import static java.util.Date.from;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
@Component
@NoArgsConstructor(access = PROTECTED)
public class TokenGenerator extends TokenHandle {

	@Value("${application.security.token.duration}")
	private long duration;


	public Date proximaExpiracao() {
		return from(now().plusMillis(duration));
	}


	private final Optional<String> generate(final String subject, final Map<String, Object> claims) {
		String token = Jwts
				.builder()
				.subject(subject)
				.claims(claims)
				.issuedAt(from(now()))
				.expiration(proximaExpiracao())
				.signWith(assinatura())
				.compact();

		return of(token);
	}


	private final Optional<String> generate(final String subject, final Optional<Claims> claims) {
		return claims.map(source -> (Map<String, Object>) source).flatMap(source -> generate(subject, source));
	}


	public final Optional<String> generate(final String input) {
		return ofNullable(trimToNull(input)).flatMap(source -> this.generate(source, new HashMap<>()));
	}


	public final Optional<String> extender(final Token token) {
		return ofNullable(token).flatMap(source -> this.generate(source.email(), this.claimsOf(source.valor())));
	}
}
