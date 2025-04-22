package com.conexa.seguranca.token.exceptions;

public class TokenSubjectException extends TokenException {

	private static final long serialVersionUID = 1L;

	public TokenSubjectException(final String msg) {
		super(msg);
	}

	public TokenSubjectException(final String msg, final Throwable cause) {
		super(msg, cause);
	}


}
