package com.conexa.seguranca.login;

import static org.apache.commons.lang3.StringUtils.trimToNull;

public record LoginOutput(String token) {

	public LoginOutput(final String token) {
		this.token = trimToNull(token);
	}

	public static LoginOutput of(final String token) {
		return new LoginOutput(token);
	}
}
