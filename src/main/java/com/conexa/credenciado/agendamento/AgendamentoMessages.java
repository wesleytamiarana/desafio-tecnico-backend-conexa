package com.conexa.credenciado.agendamento;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;


@NoArgsConstructor(access = PRIVATE)
public final class AgendamentoMessages {

	@NoArgsConstructor(access = PRIVATE)
	public static final class PacienteMessages {
		public static final PacienteMessages SELF = new PacienteMessages();

		public static final String cpfObrigatorio = "agendamento.paciente.cpf.obrigatorio";

		public static final String cpfTananhoInvalido = "agendamento.paciente.cpf.tamanho.invalido";

		public static final String nomeObrigatorio = "agendamento.paciente.nome.obrigatorio";
	}


	public static final PacienteMessages paciente =  PacienteMessages.SELF;

	public static final String pacienteObrigatorio = "agendamento.paciente";

	public static final String credenciadoObrigatorio = "agendamento.credenciado";

	public static final String dataAgendamentoObrigatoria = "agendamento.data.obrigatoria";

	public static final String	dataAgendamentoTamanhoInvalido = "agendamento.data.tamanho.invalido";

	public static final String	dataAgendamentoFormatoInvalido = "agendamento.data.formato.invalido";

	public static final String	dataAgendamentoPrazoInvalido = "agendamento.data.prazo.invalido";

	public static final AgendamentoMessages SELF = new AgendamentoMessages();



}