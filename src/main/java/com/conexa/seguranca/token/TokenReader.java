package com.conexa.seguranca.token;

import static lombok.AccessLevel.PROTECTED;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
@Component
@NoArgsConstructor(access = PROTECTED)
public class TokenReader extends TokenHandle {

	public Optional<String> email(final String token) {
		return subjectOf(token);
	}
}
