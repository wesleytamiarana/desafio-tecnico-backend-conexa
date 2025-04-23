package com.conexa.credenciado.credenciamento;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class CredenciamentoMessages {

	public static final String dadosObrigatorios = "credenciamento.dados.obrigatorio";

	public static final String emailInvalido = "credenciamento.email.invalido";

	public static final String emailObrigatorio = "credenciamento.email.obrigatorio";

	public static final String credenciadoExistente = "credenciamento.credenciado.com.email.or.cpf.cadastrado";

	public static final String senhaObrigatoria = "credenciamento.senha.obrigatoria";

	public static final String senhaDivergente = "credenciamento.senha.divergente";

	public static final String confirmacaoSenhaObrigatoria = "credenciamento.confimacao.senha.obrigatoria";

	public static final String confirmacaoSenhaDivergente = "credenciamento.confimacao.senha.divergente";

	public static final String especialidadeObrigatoria = "credenciamento.especialidade.obrigatoria";

	public static final String cpfInvalido =	"credenciamento.cpf.invalido";

	public static final String cpfObrigatorio = "credenciamento.cpf.obrigatorio";

	public static final String cpfTananhoInvalido =	"credenciamento.cpf.tamanho.invalido";

	public static final String dataNascimentoInvalida = "credenciamento.data.nascimento.invalida";

	public static final String dataNascimentoTamanhoInvalido = "credenciamento.data.nascimento.tamanho.invalido";

	public static final String dataNascimentoObrigatorio = "credenciamento.data.nascimento.obrigatorio";

	public static final String telefoneObrigatorio = "credenciamento.telefone.obrigatorio";



	public static final CredenciamentoMessages SELF = new CredenciamentoMessages();
}