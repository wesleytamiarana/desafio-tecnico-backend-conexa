package com.conexa.seguranca.token.exceptions;

public class TokenExpiradoException extends TokenException {

	private static final long serialVersionUID = 1L;

	public TokenExpiradoException(final String msg) {
		super(msg);
	}

	public TokenExpiradoException(final String msg, final Throwable cause) {
		super(msg, cause);
	}


}
