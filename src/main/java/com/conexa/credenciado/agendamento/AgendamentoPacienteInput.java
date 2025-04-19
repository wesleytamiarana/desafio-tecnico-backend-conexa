package com.conexa.credenciado.agendamento;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import org.apache.commons.lang3.builder.ToStringBuilder;

public record AgendamentoPacienteInput(
		String cpf,
		String nome) {

	@Override
	public String toString() {
		return new ToStringBuilder(this, JSON_STYLE)
				.append("cpf", this.cpf)
				.append("nome", this.nome)
				.toString();
	}
}
