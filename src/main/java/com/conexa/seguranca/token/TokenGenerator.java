package com.conexa.seguranca.token;

import static java.time.Instant.now;
import static java.util.Date.from;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
@Component
@NoArgsConstructor(access = PROTECTED)
public class TokenGenerator extends TokenHandle {

	@Value("${application.security.token.duration}")
	private long duration;


	public final Optional<String> generate(final String input) {
		return ofNullable(trimToNull(input))
				.map(subject -> Jwts
						.builder()
						.subject(subject)
						.issuedAt(from(now()))
						.expiration(from(now().plusMillis(duration)))
						.signWith(assinatura())
						.compact());
	}
}
