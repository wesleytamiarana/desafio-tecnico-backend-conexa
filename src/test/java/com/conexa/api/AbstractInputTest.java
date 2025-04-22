package com.conexa.api;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;


public abstract class AbstractInputTest {

	private Validator validador;

	@BeforeEach
	void beforeEach() {
		validador = Validation
				.buildDefaultValidatorFactory()
				.getValidator();
	}

	protected <T> Set<ConstraintViolation<T>> validar(final T target) {
		return validador.validate(target);
	}
}
