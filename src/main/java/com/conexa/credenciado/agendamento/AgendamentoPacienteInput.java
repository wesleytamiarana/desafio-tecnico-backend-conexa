package com.conexa.credenciado.agendamento;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.conexa.credenciado.agendamento.AgendamentoMessages.PacienteMessages.*;


public record AgendamentoPacienteInput(
		@NotBlank(message = cpfObrigatorio)
		@Size(min = 12, max = 14, message = cpfTananhoInvalido)
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
