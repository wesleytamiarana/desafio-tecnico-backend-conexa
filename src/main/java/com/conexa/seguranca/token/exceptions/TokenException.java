package com.conexa.seguranca.token.exceptions;

import org.springframework.security.core.AuthenticationException;

public abstract class TokenException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public TokenException(final String msg, final Throwable cause) {
		super(msg, cause);
	}

	public TokenException(final String msg) {
		super(msg);
	}
}
