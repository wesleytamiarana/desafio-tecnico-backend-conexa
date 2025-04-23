package com.conexa.api.validador.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER })
@Constraint(validatedBy = CPF.Validator.class)
public @interface CPF {

	String message() default "validation.constraint.cpf.message";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default { };

	public static final class Validator implements ConstraintValidator<CPF, Object> {

		@Override
		public void initialize(final CPF constraint) {}


		@Override
		public boolean isValid(final Object target, final ConstraintValidatorContext context) {
			boolean result;

			if( !(result = isNull(target)) ) {
				String cpf = null;

				if (target instanceof Number) {
					cpf = target.toString();
				}

				if (target instanceof String) {
					cpf = (String) target;
				}

				if(nonNull(cpf)) {
					cpf = cpf.replaceAll("[^\\d]", "");

					if ( !(cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) ) {
						try {
							int d1 = 0, d2 = 0;
							for (int i = 0; i < 9; i++) {
								int digito = Character.getNumericValue(cpf.charAt(i));
								d1 += digito * (10 - i);
								d2 += digito * (11 - i);
							}
							d1 = 11 - (d1 % 11);
							if (d1 >= 10) {
								d1 = 0;
							}
							d2 += d1 * 2;
							d2 = 11 - (d2 % 11);
							if (d2 >= 10) {
								d2 = 0;
							}

							result  = (d1 == Character.getNumericValue(cpf.charAt(9)) &&
									d2 == Character.getNumericValue(cpf.charAt(10)));
						} catch (Exception e) {
							result = false;
						}
					}
				}
			}

			return result;
		}
	}
}