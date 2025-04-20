package com.conexa.seguranca.login;

public record LoginInput(
		String email,
		String senha) {

	public static LoginInput of(final String email, final String senha) {
		return new LoginInput(email, senha);
	}
}