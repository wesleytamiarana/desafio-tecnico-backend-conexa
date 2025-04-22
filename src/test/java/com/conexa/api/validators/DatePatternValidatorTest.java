package com.conexa.api.validators;

import jakarta.validation.Validator;


public class DatePatternValidatorTest {

	private Validator validator;

	//	@BeforeEach
	//	void setup() {
	//		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	//		validator = factory.getValidator();
	//	}
	//
	//
	//	static record Evento (
	//			@DateTimePattern(pattern = "dd/MM/yyyy HH:mm:ss", tipo = DateTimePattern.Tipo.DATETIME)
	//			String inicio) {}
	//
	//	@Test
	//	void deveAceitarDataHoraCorreta() {
	//		var dto = new Evento("07/07/1979 15:30:00");
	//		Set violations = validator.validate(dto);
	//		assertTrue(violations.isEmpty(), "A data e hora devem ser válidas");
	//	}
	//
	//	@Test
	//	void deveRejeitarDataComFormatoErrado() {
	//		var dto = new Evento("1979/07/07");
	//		Set violations = validator.validate(dto);
	//		assertFalse(violations.isEmpty(), "Formato errado deve ser inválido");
	//	}
	//
	//	@Test
	//	void deveRejeitarDataIncompleta() {
	//		var dto = new Evento("07/07/1979");
	//		Set violations = validator.validate(dto);
	//		assertFalse(violations.isEmpty(), "Data sem hora deve ser inválida");
	//	}
	//
	//	@Test
	//	void deveRejeitarDataComHoraFaltandoSegundos() {
	//		var dto = new Evento("07/07/1979 15:30");
	//		Set violations = validator.validate(dto);
	//		assertFalse(violations.isEmpty(), "Hora sem segundos deve ser inválida");
	//	}
	//
	//	@Test
	//	void deveRejeitarDataComSeparadorErrado() {
	//		var dto = new Evento("07-07-1979 15:30:00");
	//		Set violations = validator.validate(dto);
	//		assertFalse(violations.isEmpty(), "Separador errado deve ser inválido");
	//	}
	//
	//	@Test
	//	void deveAceitarDataComZeroEmMinutosESegundos() {
	//		var dto = new Evento("01/01/2024 00:00:00");
	//		Set violations = validator.validate(dto);
	//		assertTrue(violations.isEmpty(), "Data com hora zerada deve ser válida");
	//	}
}

