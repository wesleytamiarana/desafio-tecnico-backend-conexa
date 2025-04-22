package com.conexa.api;

import static lombok.AccessLevel.PROTECTED;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;


@Component
@NoArgsConstructor(access = PROTECTED)
public class ResourceErrorAttributes extends DefaultErrorAttributes {

	@Autowired
	private MessageSource messageSource;


	private String customMessage(final Throwable error, final Locale locale) {
		if (error == null) {
			return messageSource.getMessage("error.unknown", null, locale);
		}

		if (error instanceof NullPointerException) {
			return messageSource.getMessage("error.null.poiter", null, locale);
		}

		return messageSource.getMessage("error.unexpected", null, locale);
	}


	@Override
	public Map<String, Object> getErrorAttributes(final WebRequest webRequest, final ErrorAttributeOptions options) {
		String reason;

		HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();

		Locale locale = request.getLocale();

		Map<String, Object> defaultAttributes = super.getErrorAttributes(webRequest, options);

		int status = (int) defaultAttributes.getOrDefault("status", 500);

		try {
			reason = HttpStatus.valueOf(status).getReasonPhrase();
		} catch (Exception e) {
			status =  500;

			reason = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
		}

		String message = customMessage(getError(webRequest), locale);

		Map<String, Object> customResponse = new LinkedHashMap<>();

		customResponse.put("status", status);

		customResponse.put("error", reason);

		customResponse.put("message", message);

		customResponse.put("path", defaultAttributes.getOrDefault("path", "unknown"));

		return customResponse;
	}





}
