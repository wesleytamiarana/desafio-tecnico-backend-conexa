package com.conexa.api.excecoes;

public class ProcessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final String messageKey;

	private final Object[] args;

	public ProcessException(final String messageKey, final Object... args) {
		super(messageKey);

		this.messageKey = messageKey;

		this.args = args;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public Object[] getArgs() {
		return args;
	}
}
