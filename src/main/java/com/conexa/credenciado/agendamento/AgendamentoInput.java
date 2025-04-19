package com.conexa.credenciado.agendamento;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import org.apache.commons.lang3.builder.ToStringBuilder;

public record AgendamentoInput (
		String dataHora,
		AgendamentoPacienteInput paciente) {

	public String cpfPaciente() {
		return ofNullable(this.paciente).map(AgendamentoPacienteInput::cpf).orElse(null);
	}

	public String nomePaciente() {
		return ofNullable(this.paciente).map(AgendamentoPacienteInput::nome).orElse(null);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, JSON_STYLE)
				.append("dataHora", this.dataHora)
				.append("paciente", this.paciente)
				.toString();
	}
}
