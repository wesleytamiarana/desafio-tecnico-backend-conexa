package com.conexa.seguranca;

import static lombok.AccessLevel.PROTECTED;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.conexa.seguranca.token.exceptions.TokenException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;


@Component
@NoArgsConstructor(access = PROTECTED)
public class SegurancaAuthEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	private MessageSource messageSource;


	@Autowired
	private ErrorAttributes errorAttributes;

	@Override
	public void commence(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final AuthenticationException cause) throws IOException, ServletException {

		WebRequest webRequest = new ServletWebRequest(request);

		Map<String, Object> error = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());

		error.put("status", HttpStatus.UNAUTHORIZED.value());

		error.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		response.setStatus(HttpStatus.UNAUTHORIZED.value());

		if (cause instanceof TokenException ex) {
			error.put("message", messageSource.getMessage(ex.getMessage(), null, request.getLocale()));
		}

		response.getWriter().write(new ObjectMapper().writeValueAsString(error));
	}
}
