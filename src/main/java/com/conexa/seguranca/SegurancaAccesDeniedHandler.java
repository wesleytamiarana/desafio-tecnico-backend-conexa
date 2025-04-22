package com.conexa.seguranca;

import static lombok.AccessLevel.PROTECTED;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;


@Component
@NoArgsConstructor(access = PROTECTED)
public class SegurancaAccesDeniedHandler implements AccessDeniedHandler {

	@Autowired
	private ErrorAttributes errorAttributes;

	@Override
	public void handle(final HttpServletRequest request,
			final HttpServletResponse response,
			final AccessDeniedException accessDeniedException) throws IOException {

		WebRequest webRequest = new ServletWebRequest(request);

		Map<String, Object> error = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		response.setStatus(HttpStatus.FORBIDDEN.value());

		response.getWriter().write(new ObjectMapper().writeValueAsString(error));
	}
}
