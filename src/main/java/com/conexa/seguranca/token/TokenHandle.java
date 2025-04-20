package com.conexa.seguranca.token;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.util.Base64;
import java.util.Optional;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
@NoArgsConstructor(access = PROTECTED)
public abstract class TokenHandle {

	@Value("${application.security.token.signature-key}")
	private String signatureKey;


	protected final SecretKey assinatura() {
		return Keys.hmacShaKeyFor(Base64.getDecoder().decode(this.signatureKey));
	}


	protected final Optional<Claims> claimsOf(final String token) {
		return ofNullable(trimToNull(token))
				.map(target -> Jwts
						.parser()
						.verifyWith(assinatura())
						.build()
						.parseSignedClaims(target)
						.getPayload());
	}


	protected final <T> Optional<T> extract(final Claims reivindicacoes, final Function<Claims, T> extrator) {
		return ofNullable(reivindicacoes).map(extrator);
	}


	protected final <T> Optional<T> extract(final String token, final Function<Claims, T> extrator) {
		return claimsOf(token).flatMap(claims -> extract(claims, extrator));
	}


	protected final Optional<String> subjectOf(final String token) {
		return extract(token, Claims::getSubject);
	}
}
