package com.conexa.seguranca.login;

import static com.conexa.seguranca.login.LoginMessages.emailObrigatorio;
import static com.conexa.seguranca.login.LoginMessages.senhaObrigatoria;

import jakarta.validation.constraints.NotBlank;

public record LoginInput(
		@NotBlank(message = emailObrigatorio)
		String email,

		@NotBlank(message = senhaObrigatoria)
		String senha) {

	public static LoginInput of(final String email, final String senha) {
		return new LoginInput(email, senha);
	}
}