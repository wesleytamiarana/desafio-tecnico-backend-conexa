package com.conexa.credenciado.credenciamento;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class CredenciadoMessages {
	public static final String especialidadeObrigatoria = "credenciado.especialidade.obrigatoria";

	public static final String cpfObrigatorio = "credenciado.cpf.obrigatorio";

	public static final String cpfTananhoInvalido =	"credenciado.cpf.tamanho.invalido";

	public static final String dataNascimentoObrigatoria = "credenciado.data.nascimento.obrigatorio";

	public static final String dataNascimentoFormatoInvalido = "credenciado.data.nascimento.formato.invalido";

	public static final String dataNascimentoTamanhoInvalido = "credenciado.data.nascimento.tamanho.invalido";

	public static final String telefoneObrigatorio = "credenciado.telefone.obrigatorio";

	public static final String credenciaisObrigatorias = "credenciado.credenciais.obrigatorias";
}
