package com.conexa.credenciado.credenciamento;

import static com.conexa.AplicacaoMessages.credenciamento;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import com.conexa.api.AbstractInputTest;

import jakarta.validation.ConstraintViolation;

@SuppressWarnings("static-access")
public class CredenciamentoInputTest extends AbstractInputTest {

	@Test
	void dataNascimentoDeveEstarNoPassado() {
		String dataNoFuturo = LocalDate
				.now()
				.plusYears(1)
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		CredenciamentoInput unitUnderTest = new CredenciamentoInput("clinica_geral@gmail.com",
				"mesmasenha",
				"mesmasenha",
				"Clinica Geral",
				cpfValido,
				dataNoFuturo,
				"(81) 98342-2258");

		assertThat(validar(unitUnderTest))
		.isNotEmpty()
		.extracting(ConstraintViolation::getMessageTemplate)
		.contains(credenciamento.dataNascimentoInvalida);
	}

	@Test
	void emailObrigatorio() {
		CredenciamentoInput unitUnderTest = new CredenciamentoInput(null,
				"mesmasenha",
				"mesmasenha",
				"Clinica Geral",
				cpfValido,
				"07/07/1979",
				"(81) 98342-2258");

		assertThat(validar(unitUnderTest))
		.isNotEmpty()
		.extracting(ConstraintViolation::getMessageTemplate)
		.contains(credenciamento.emailObrigatorio);
	}


	@Test
	void emailInvalido() {
		CredenciamentoInput unitUnderTest = new CredenciamentoInput("clinica_geral#google.com",
				"mesmasenha",
				"mesmasenha",
				"Clinica Geral",
				cpfValido,
				"07/07/1979",
				"(81) 98342-2258");

		assertThat(validar(unitUnderTest))
		.isNotEmpty()
		.extracting(ConstraintViolation::getMessageTemplate)
		.contains(credenciamento.emailInvalido);
	}

	@Test
	void senhasNaoInformadas() {
		CredenciamentoInput unitUnderTest = new CredenciamentoInput("clinica_geral@gmail.com",
				null,
				null,
				"Clinica Geral",
				cpfValido,
				"07/07/1979",
				"(81) 98342-2258");

		assertThat(validar(unitUnderTest))
		.isNotEmpty()
		.extracting(ConstraintViolation::getMessageTemplate)
		.contains(credenciamento.senhaObrigatoria)
		.contains(credenciamento.confirmacaoSenhaObrigatoria);
	}


	@Test
	void senhaInformadaComConfirmacaoNaoInformada() {
		CredenciamentoInput unitUnderTest = new CredenciamentoInput("clinica_geral@gmail.com",
				"umasenha",
				null,
				"Clinica Geral",
				cpfValido,
				"07/07/1979",
				"(81) 98342-2258");

		assertThat(validar(unitUnderTest))
		.isNotEmpty()
		.extracting(ConstraintViolation::getMessageTemplate)
		.contains(credenciamento.confirmacaoSenhaObrigatoria)
		.contains(credenciamento.confirmacaoSenhaDivergente);
	}

	@Test
	void senhaNaoInformadaComConfirmacaoInformada() {
		CredenciamentoInput unitUnderTest = new CredenciamentoInput("clinica_geral@gmail.com",
				null,
				"outrasenha",
				"Clinica Geral",
				cpfValido,
				"07/07/1979",
				"(81) 98342-2258");

		assertThat(validar(unitUnderTest))
		.isNotEmpty()
		.extracting(ConstraintViolation::getMessageTemplate)
		.contains(credenciamento.senhaObrigatoria)
		.contains(credenciamento.confirmacaoSenhaDivergente);
	}


	@Test
	void senhasDivergentes() {
		CredenciamentoInput unitUnderTest = new CredenciamentoInput("clinica_geral@gmail.com",
				"umasenha",
				"outrasenha",
				"Clinica Geral",
				cpfValido,
				"07/07/1979",
				"(81) 98342-2258");

		assertThat(validar(unitUnderTest))
		.isNotEmpty()
		.extracting(ConstraintViolation::getMessageTemplate)
		.contains(credenciamento.confirmacaoSenhaDivergente);
	}


	@Test
	void cpfInvalido() {
		CredenciamentoInput unitUnderTest = new CredenciamentoInput("clinica_geral@gmail.com",
				"mesmasenha",
				"mesmasenha",
				"Clinica Geral",
				"719.963.390-30",
				"07/07/1979",
				"(81) 98342-2258");

		assertThat(validar(unitUnderTest))
		.isNotEmpty()
		.extracting(ConstraintViolation::getMessageTemplate)
		.contains(credenciamento.cpfInvalido);
	}

	@Test
	void cpfValido() {
		CredenciamentoInput unitUnderTest = new CredenciamentoInput("clinica_geral@gmail.com",
				"mesmasenha",
				"mesmasenha",
				"Clinica Geral",
				cpfValido,
				"07/07/1979",
				"(81) 98342-2258");

		assertThat(validar(unitUnderTest))
		.extracting(ConstraintViolation::getMessageTemplate)
		.doesNotContain(credenciamento.cpfInvalido);
	}
}
