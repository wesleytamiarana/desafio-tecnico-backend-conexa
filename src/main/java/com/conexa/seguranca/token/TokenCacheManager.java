package com.conexa.seguranca.token;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.util.Objects;
import java.util.Optional;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
@Component
@NoArgsConstructor(access = PROTECTED)
public class TokenCacheManager {

	@Cacheable(value = "tokens", key = "#token")
	public Optional<String> find(final String token) {
		return Optional.empty();
	}


	@CachePut(value = "tokens", key = "#token")
	public Optional<String> store(final String token){
		return ofNullable(trimToNull(token)).filter(Objects::nonNull);
	}
}
