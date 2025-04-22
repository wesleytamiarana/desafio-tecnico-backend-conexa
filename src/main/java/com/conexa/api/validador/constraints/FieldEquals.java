package com.conexa.api.validador.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;


@Documented
@Target({ TYPE })
@Retention( RUNTIME )
@Constraint(validatedBy = FieldEquals.Validator.class)
public @interface FieldEquals {
	String message() default "atributo.com.valores.divergente";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};


	@Documented
	@Target({ FIELD })
	@Retention( RUNTIME )
	public static @interface To {
		String message() default "atributo.com.valor.divergente";

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};

		String value() default "default";
	}


	public static final class Validator implements ConstraintValidator<FieldEquals, Object> {
		private List<Field> fields = new ArrayList<>();

		private Map<String, String> mensagens = new HashMap<>();

		@Override
		public void initialize(final FieldEquals constraintAnnotation) {}


		private void inicializarCampos(final Object objeto) {
			if (!fields.isEmpty()) {
				return;
			}

			for (Field campo : objeto.getClass().getDeclaredFields()) {
				if (campo.isAnnotationPresent(FieldEquals.To.class)) {

					campo.setAccessible(true);

					fields.add(campo);

					String mensagem = campo.getAnnotation(FieldEquals.To.class).message();

					mensagens.put(campo.getName(), mensagem);
				}
			}
		}


		@Override
		public boolean isValid(final Object objeto, final ConstraintValidatorContext context) {
			if (objeto == null) {
				return true;
			}

			inicializarCampos(objeto);

			if (fields.size() < 2) {
				return true;
			}

			Object valorReferencia = null;

			try {
				valorReferencia = fields.get(0).get(objeto);
			} catch (IllegalAccessException e) {
				return false;
			}

			boolean todosIguais = true;

			for (Field campo : fields) {
				try {
					Object valor = campo.get(objeto);
					if ((valorReferencia == null && valor != null) || (valorReferencia != null && !valorReferencia.equals(valor))) {

						todosIguais = false;


						context.disableDefaultConstraintViolation();

						context.buildConstraintViolationWithTemplate(mensagens.get(campo.getName()))
						.addPropertyNode(campo.getName())
						.addConstraintViolation();
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					return false;
				}
			}

			return todosIguais;
		}
	}
}


//		private List<Object> groupValues(final Object targetObject, final String targetGroup) {
//			FieldEquals annotation = null;
//
//			Class<?> clazz = targetObject.getClass();
//
//			List<Object> values = new ArrayList<Object>();
//
//			for (Field field : clazz.getDeclaredFields()) {
//				if (field.isAnnotationPresent(FieldEquals.class)) {
//
//					annotation = field.getAnnotation(FieldEquals.class);
//
//					if (targetGroup.equals(annotation.value())) {
//						try {
//							field.setAccessible(true);
//
//							values.add(field.get(targetObject));
//						} catch (IllegalAccessException e) {}
//					}
//				}
//			}
//
//			return values;
//		}




//
//			return groupValues(sourceObject, groupName)
//					.stream()
//					.allMatch(targetValue -> Objects.equals(targetValue, sourceValue));
