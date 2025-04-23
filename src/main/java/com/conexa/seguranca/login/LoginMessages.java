package com.conexa.seguranca.login;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class LoginMessages {
	public static final String emailInvalido = "login.email.invalido";

	public static final String emailObrigatorio = "login.email.obrigatorio";

	public static final String senhaObrigatoria = "login.senha.obrigatoria";
}