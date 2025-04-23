package com.conexa.credenciado.agendamento;

import static com.conexa.credenciado.agendamento.AgendamentoMessages.PacienteMessages.cpfInvalido;
import static com.conexa.credenciado.agendamento.AgendamentoMessages.PacienteMessages.cpfObrigatorio;
import static com.conexa.credenciado.agendamento.AgendamentoMessages.PacienteMessages.cpfTananhoInvalido;
import static com.conexa.credenciado.agendamento.AgendamentoMessages.PacienteMessages.nomeObrigatorio;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;

import com.conexa.api.validador.constraints.CPF;

import jakarta.validation.constraints.NotBlank;


public record AgendamentoPacienteInput(

		@CPF(message = cpfInvalido)
		@NotBlank(message = cpfObrigatorio)
		@Length(min = 11, max = 14, message = cpfTananhoInvalido)
		String cpf,

		@NotBlank(message = nomeObrigatorio)
		String nome) {

	@Override
	public String toString() {
		return new ToStringBuilder(this, JSON_STYLE)
				.append("cpf", this.cpf)
				.append("nome", this.nome)
				.toString();
	}
}
