package com.conexa.credenciado.agendamento;

import static com.conexa.credenciado.agendamento.AgendamentoMessages.dataAgendamentoObrigatoria;
import static com.conexa.credenciado.agendamento.AgendamentoMessages.dataAgendamentoPrazoInvalido;
import static com.conexa.credenciado.agendamento.AgendamentoMessages.dataAgendamentoTamanhoInvalido;
import static com.conexa.credenciado.agendamento.AgendamentoMessages.pacienteObrigatorio;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;

import com.conexa.api.validador.constraints.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AgendamentoInput (

		@NotBlank(message = dataAgendamentoObrigatoria)
		@Length(min = 16, message = dataAgendamentoTamanhoInvalido)
		@Data.Futura(message = dataAgendamentoPrazoInvalido)
		String dataHora,

		@Valid
		@NotNull(message = pacienteObrigatorio )
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
