package com.conexa.api.validador.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Objects.nonNull;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target({FIELD, PARAMETER})
@Constraint(validatedBy = DataFutura.Validator.class)
public @interface DataFutura {

	String pattern() default "yyyy-MM-dd HH:mm:ss";

	String message() default "validation.constraint.data.futura.message";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default { };

	public static final class Validator implements ConstraintValidator<DataFutura, Object> {

		private String formato;

		@Override
		public void initialize(final DataFutura constraint) {
			formato = constraint.pattern();
		}


		@Override
		public boolean isValid(final Object value, final ConstraintValidatorContext context) {
			boolean result = true;

			if(nonNull(value)) {
				try {
					if( value instanceof ChronoLocalDate data) {
						result = data.isAfter(LocalDate.now());
					}

					if( value instanceof ChronoLocalDateTime<?> data) {
						result = data.isAfter(LocalDateTime.now());
					}

					if (value instanceof String source) {
						result = LocalDateTime
								.from(ofPattern(formato).parse(source))
								.isAfter(LocalDateTime.now());
					}
				} catch (DateTimeException e) {
					System.out.printf("Data futura -> Formato invalido: %s\n", formato);

					System.out.printf(e.getMessage());

					result = false;
				}
			}

			return result;
		}
	}
}