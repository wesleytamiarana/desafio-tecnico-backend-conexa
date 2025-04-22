package com.conexa.seguranca.token.exceptions;

public class TokenRevogadoException extends TokenException  {

	private static final long serialVersionUID = 1L;

	public TokenRevogadoException(final String msg) {
		super(msg);
	}

	public TokenRevogadoException(final String msg, final Throwable cause) {
		super(msg, cause);
	}


}
