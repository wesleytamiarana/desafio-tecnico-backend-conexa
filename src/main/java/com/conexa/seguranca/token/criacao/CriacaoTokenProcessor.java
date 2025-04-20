package com.conexa.seguranca.token.criacao;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexa.seguranca.credenciais.Credenciais;
import com.conexa.seguranca.credenciais.CredenciaisRepository;
import com.conexa.seguranca.token.Token;
import com.conexa.seguranca.token.TokenGenerator;
import com.conexa.seguranca.token.TokenRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;


@Log
@Service
@NoArgsConstructor(access = PROTECTED)
public class CriacaoTokenProcessor implements CriacaoTokenProcess {

	@Autowired
	private TokenGenerator tokenGenerator;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private CredenciaisRepository credenciaisRepository;



	private Optional<Token> process(final Optional<String> subject) {
		subject.orElseThrow(() -> new IllegalArgumentException("securanca.token.geracao.subject.required"));

		Optional<Token> token = subject.flatMap(tokenRepository::findByEmail);

		if(!token.isPresent()) {
			Credenciais credenciais = subject
					.flatMap(credenciaisRepository::findByEmail)
					.orElseThrow(() ->  new EntityNotFoundException("securanca.token.geracao.credenciais"));

			token = subject.flatMap(tokenGenerator::generate)
					.map(valor -> Token.of(credenciais, valor))
					.map(tokenRepository::save);
		}

		return token;
	}


	@Override
	public Optional<String> process(final String input) {
		return this.process(ofNullable(input)).map(Token::valor);
	}
}
