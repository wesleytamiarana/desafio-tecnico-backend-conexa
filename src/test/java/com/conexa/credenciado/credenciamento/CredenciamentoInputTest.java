package com.conexa.credenciado.credenciamento;

import static com.conexa.AplicacaoMessages.credenciamento;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.conexa.api.AbstractInputTest;

import jakarta.validation.ConstraintViolation;

@SuppressWarnings("static-access")
public class CredenciamentoInputTest extends AbstractInputTest {

	@Test
	void senhasNaoInformadas() {
		CredenciamentoInput unitUnderTest = new CredenciamentoInput("clinica_geral@gmail.com",
				null,
				null,
				"Clinica Geral",
				"719.963.390-44",
				"07/07/1979",
				"(81) 98342-2258");

		assertThat(validar(unitUnderTest))
		.isNotEmpty()
		.hasSize(2)
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
				"719.963.390-44",
				"07/07/1979",
				"(81) 98342-2258");

		assertThat(validar(unitUnderTest))
		.isNotEmpty()
		.hasSize(2)
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
				"719.963.390-44",
				"07/07/1979",
				"(81) 98342-2258");

		assertThat(validar(unitUnderTest))
		.isNotEmpty()
		.hasSize(2)
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
				"719.963.390-44",
				"07/07/1979",
				"(81) 98342-2258");

		assertThat(validar(unitUnderTest))
		.isNotEmpty()
		.hasSize(1)
		.extracting(ConstraintViolation::getMessageTemplate)
		.contains(credenciamento.confirmacaoSenhaDivergente);
	}
}
