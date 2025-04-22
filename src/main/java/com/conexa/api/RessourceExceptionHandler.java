package com.conexa.api;

import static lombok.AccessLevel.PROTECTED;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.conexa.api.excecoes.ProcessException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

@RestControllerAdvice
@NoArgsConstructor(access = PROTECTED)
public class RessourceExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ErrorAttributes errorAttributes;

	private record Issue (String message) {}

	private record HttpIssue (int status, String message) {}

	private record FieldValidationIssue (String field, String message) {}

	private record TypeValidationIssue (int status, String message, List<FieldValidationIssue> errors) {}


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<TypeValidationIssue> handleValidationErrors(
			final MethodArgumentNotValidException ex, final HttpServletRequest request) {

		Locale locale = request.getLocale();

		List<FieldValidationIssue> issues = ex
				.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(e -> new FieldValidationIssue(e.getField(), messageSource.getMessage(e, locale)))
				.toList();

		return ResponseEntity.badRequest().body(new TypeValidationIssue(HttpStatus.BAD_REQUEST.value(), "Validation", issues));
	}


	@ExceptionHandler(ProcessException.class)
	public ResponseEntity<?> processException(final ProcessException ex, final HttpServletRequest request) {

		String message = messageSource.getMessage(ex.getMessageKey(), ex.getArgs(), request.getLocale());

		return ResponseEntity.badRequest().body(new Issue(message));
	}


	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<?> processException(final MissingRequestHeaderException ex, final HttpServletRequest request) throws Exception {

		String message = messageSource.getMessage("seguranca.header.obrigatorio", new String[] { ex.getHeaderName() }, request.getLocale());

		return ResponseEntity.badRequest().body(new HttpIssue(HttpStatus.BAD_REQUEST.value(), message));
	}


	//	@ExceptionHandler(Exception.class)
	//	public ResponseEntity<?> processException(final Exception ex, final HttpServletRequest request) throws Exception {
	//
	//		WebRequest webRequest = new ServletWebRequest(request);
	//
	//		Map<String, Object> error = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
	//
	//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	//	}
}
