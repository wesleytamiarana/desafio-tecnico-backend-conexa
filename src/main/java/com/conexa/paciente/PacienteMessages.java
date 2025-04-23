package com.conexa.paciente;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class PacienteMessages {

	public static final String cpfObrigatorio = "paciente.cpf.obrigatorio";

	public static final String cpfInvalido = "paciente.cpf.invalido";

	public static final String cpfTananhoInvalido = "paciente.cpf.tamanho.invalido";

	public static final String nomeObrigatorio = "paciente.nome.obrigatorio";
}
