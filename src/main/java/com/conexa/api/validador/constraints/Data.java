package com.conexa.api.validador.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.time.format.ResolverStyle.SMART;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;


abstract class AbstractDataValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

	protected boolean throwOnFailure;

	protected Map<String, DateTimeFormatter> formatters;

	protected static final List<String> timePatterns = Arrays.asList("H", "h", "m", "s", "z", "Z","X");


	protected void withPatterns(final String... patterns) {
		formatters = Arrays
				.stream(patterns)
				.collect(Collectors.toMap(
						pattern -> pattern,
						pattern -> ofPattern(pattern).withResolverStyle(SMART)));

	}

	private boolean containsTimePattern(final String pattern) {
		return  ofNullable(pattern)
				.map(value -> timePatterns.stream().anyMatch(value::contains))
				.orElse(false);
	}


	private BiFunction<String, Entry<String, DateTimeFormatter>, LocalDateTime> stringToDateAndTimeConverter(){
		return (value, entry) -> containsTimePattern(entry.getKey())
				? LocalDateTime.parse(value, entry.getValue()) : LocalDate.parse(value, entry.getValue()).atStartOfDay();

	}


	private Optional<LocalDateTime> dateAndTtimeOf(final String value) {
		final BiFunction<String, Entry<String, DateTimeFormatter>, LocalDateTime> stringToDateTimeConverter = stringToDateAndTimeConverter();

		return ofNullable(value)
				.flatMap(source -> formatters
						.entrySet()
						.stream()
						.map(entry -> stringToDateTimeConverter.apply(source, entry))
						.findFirst());
	}


	protected boolean isValid(final Object target, final ConstraintValidatorContext context, final Function<LocalDateTime, Boolean> preicate) {
		boolean result = true;

		if(!(result = isNull(target))) {
			Optional<LocalDateTime>  dateTime = Optional.empty();

			if (target instanceof LocalDateTime) {
				dateTime = Optional.of((LocalDateTime) target);
			}

			if (target instanceof LocalDate) {
				dateTime = Optional
						.of((LocalDate) target)
						.map(LocalDate::atStartOfDay);
			}

			if(target instanceof String) {
				dateTime = dateAndTtimeOf((String) target);
			}

			if(dateTime.isEmpty()) {
				if (throwOnFailure) {
					throw new IllegalArgumentException(String.format("DataFutura: Padrao incompativel para a data: %o", target));
				}
			}

			result = dateTime.map(preicate).orElse(false);
		}

		return result;
	}
}


public @interface Data {

	public static final String defaultDatePattern = "yyyy-MM-dd";

	public static final String defaultDateAndTimePattern = "yyyy-MM-dd HH:mm:ss";

	public static final DateTimeFormatter defaultDateFormater = ofPattern(defaultDatePattern).withResolverStyle(SMART);

	public static final DateTimeFormatter defaultDateAndTimeFormatter = ofPattern(defaultDateAndTimePattern).withResolverStyle(SMART);


	@Documented
	@Retention(RUNTIME)
	@Target({ FIELD, METHOD, PARAMETER })
	@Constraint(validatedBy = Data.DataPassadaValidator.class)
	public @interface Passada {
		String [] patterns() default { Data.defaultDateAndTimePattern };

		String message() default "validation.constraint.data.passada.message";

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default { };

		boolean throwOnFailure() default false;
	}


	@Documented
	@Retention(RUNTIME)
	@Target({ FIELD, METHOD, PARAMETER })
	@Constraint(validatedBy = Data.DataFuturaValidator.class)
	public @interface Futura {
		String [] patterns() default { Data.defaultDateAndTimePattern };

		String message() default "validation.constraint.data.futura.message";

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default { };

		boolean throwOnFailure() default false;
	}


	public static final class DataFuturaValidator extends AbstractDataValidator<Futura, Object> {

		@Override
		public void initialize(final Futura constraint) {
			throwOnFailure = constraint.throwOnFailure();

			withPatterns(constraint.patterns());
		}

		@Override
		public boolean isValid(final Object value, final ConstraintValidatorContext context) {
			return super.isValid(value, context, data -> data.isAfter(LocalDateTime.now()));
		}
	}

	public static final class DataPassadaValidator extends AbstractDataValidator<Passada, Object> {

		@Override
		public void initialize(final Passada constraint) {
			throwOnFailure = constraint.throwOnFailure();

			withPatterns(constraint.patterns());
		}

		@Override
		public boolean isValid(final Object value, final ConstraintValidatorContext context) {
			return super.isValid(value, context, data -> data.isBefore(LocalDateTime.now()));
		}
	}
}
