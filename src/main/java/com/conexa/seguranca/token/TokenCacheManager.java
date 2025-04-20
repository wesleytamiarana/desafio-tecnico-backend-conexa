package com.conexa.seguranca.token;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
@Component
@NoArgsConstructor(access = PROTECTED)
public class TokenCacheManager {

	private static final String cacheName = "tokens";

	@Autowired
	private CacheManager tokenCacheManager;


	private final Cache cache() {
		return tokenCacheManager.getCache(cacheName);
	}


	public final Optional<Token> get(final Optional<String> email) {
		return email
				.map(cache()::get)
				.map(ValueWrapper::get)
				.map(Token.class::cast);
	}


	public final Optional<Token> get(final String email) {
		return this.get(ofNullable(email));
	}


	public final Optional<Token> get(final Token token) {
		return this.get(ofNullable(token).map(Token::email));
	}


	public final Optional<Token> put(final Optional<Token> token){
		token.ifPresent(source -> cache().put(source.email(), source));

		return this.get(token.map(Token::email));
	}


	public final Optional<Token> put(final Token token){
		return this.put(ofNullable(token));
	}


	public final void evict(final String email){
		cache().evictIfPresent(email);
	}


	public final void evict(final Optional<Token> token){
		token.map(Token::email).ifPresent(this::evict);
	}


	public final void evict(final Token token){
		this.evict(ofNullable(token));
	}
}
