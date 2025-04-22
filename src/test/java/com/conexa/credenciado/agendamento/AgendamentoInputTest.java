package com.conexa.credenciado.agendamento;

import static com.conexa.AplicacaoMessages.agendamento;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import com.conexa.api.AbstractInputTest;

import jakarta.validation.ConstraintViolation;

@SuppressWarnings("static-access")
public class AgendamentoInputTest extends AbstractInputTest {

	private final DateTimeFormatter formatadorData =  ofPattern("yyyy-MM-dd HH:mm:ss");

	@Test
	void ddataAgendamentoDeveSerUmaDataFutura() {
		String dataAgendamento = LocalDateTime.now().minusDays(1).format(formatadorData);

		AgendamentoPacienteInput paciente = new AgendamentoPacienteInput("124.797.750-11", "Sr. Joao");

		AgendamentoInput unitUnderTest = new AgendamentoInput(dataAgendamento, paciente);

		assertThat(validar(unitUnderTest))
		.isNotEmpty()
		.hasSize(1)
		.extracting(ConstraintViolation::getMessageTemplate)
		.contains(agendamento.dataAgendamentoPrazoInvalido);
	}
}
