package com.conexa.credenciado.credenciamento;

import static com.conexa.credenciado.credenciamento.CredenciamentoMessages.credenciadoExistente;
import static com.conexa.credenciado.credenciamento.CredenciamentoMessages.dadosObrigatorios;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@Validated
@RequiredArgsConstructor(access = PROTECTED)
public class CredenciamentoProcessor implements CredenciamentoProcess {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private final CredenciadoRepository credenciadoRepository;


	private Optional<String> processSave(final Optional<CredenciamentoInput> input) {
		Optional<Credenciado> output;

		input
		.orElseThrow(() -> new RuntimeException (dadosObrigatorios));

		input
		.flatMap(data -> credenciadoRepository
				.findByEmailOrCpf(data.email(), data.cpf()))
		.ifPresent(data -> { throw new BadCredentialsException(credenciadoExistente); });

		output = input
				.map(data -> Credenciado
						.of()
						.cpf(data.cpf())
						.dataNascimento(data.dataNascimento())
						.telefone(data.telefone())
						.especialidade(data.especialidade())
						.email(data.email())
						.senha(passwordEncoder.encode(data.senha())))
				.map(credenciadoRepository::save);

		return output.map(Credenciado::uuid);
	}


	@Override
	public Optional<String> process(@Valid final CredenciamentoInput input) {
		return this.processSave(ofNullable(input));
	}
}
