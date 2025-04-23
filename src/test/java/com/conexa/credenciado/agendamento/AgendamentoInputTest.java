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
	void agendamentoSemPacienteDefinido() {
		String dataAgendamento = LocalDateTime.now().plusDays(1).format(formatadorData);

		AgendamentoInput unitUnderTest = new AgendamentoInput(dataAgendamento, null);

		assertThat(validar(unitUnderTest))
		.isNotEmpty()
		.extracting(ConstraintViolation::getMessageTemplate)
		.contains(agendamento.pacienteObrigatorio);
	}


	@Test
	void agendamentoDePacienteComCpfInvalido() {
		String dataAgendamento = LocalDateTime.now().plusDays(1).format(formatadorData);

		AgendamentoPacienteInput paciente = new AgendamentoPacienteInput("124.797.750-xx", "Sr. Joao");


		AgendamentoInput unitUnderTest = new AgendamentoInput(dataAgendamento, paciente);

		assertThat(validar(unitUnderTest))
		.isNotEmpty()
		.extracting(ConstraintViolation::getMessageTemplate)
		.contains(agendamento.paciente.cpfInvalido);
	}

	@Test
	void agendamentoDePacienteNomeNaoDefininido() {
		String dataAgendamento = LocalDateTime.now().plusDays(1).format(formatadorData);

		AgendamentoPacienteInput paciente = new AgendamentoPacienteInput(cpfValido, null);


		AgendamentoInput unitUnderTest = new AgendamentoInput(dataAgendamento, paciente);

		assertThat(validar(unitUnderTest))
		.isNotEmpty()
		.extracting(ConstraintViolation::getMessageTemplate)
		.contains(agendamento.paciente.nomeObrigatorio);
	}

	@Test
	void dataAgendamentoDeveSerUmaDataFutura() {
		String dataAgendamento = LocalDateTime.now().minusDays(1).format(formatadorData);

		AgendamentoPacienteInput paciente = new AgendamentoPacienteInput(cpfValido, "Sr. Joao");

		AgendamentoInput unitUnderTest = new AgendamentoInput(dataAgendamento, paciente);

		assertThat(validar(unitUnderTest))
		.isNotEmpty()
		.extracting(ConstraintViolation::getMessageTemplate)
		.contains(agendamento.dataAgendamentoPrazoInvalido);
	}


	@Test
	void dataAgendamentoComTodosOsDadosCorretos() {
		String dataAgendamento = LocalDateTime.now().plusDays(1).format(formatadorData);

		AgendamentoPacienteInput paciente = new AgendamentoPacienteInput(cpfValido, "Sr. Joao");

		AgendamentoInput unitUnderTest = new AgendamentoInput(dataAgendamento, paciente);

		assertThat(validar(unitUnderTest)).isEmpty();
	}
}
