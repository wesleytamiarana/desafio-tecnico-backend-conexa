package com.conexa.credenciado.credenciamento;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@RequiredArgsConstructor(access = PROTECTED)
public class CredenciamentoProcessor implements CredenciamentoProcess {

	private final CredenciadoRepository credenciadoRepository;

	@Override
	public Optional<String> process(final CredenciamentoInput input) {
		Optional<String> credenciado = ofNullable(input)
				.map(data -> Credenciado
						.of()
						.cpf(data.cpf())
						.dataNascimento(data.dataNascimento())
						.telefone(data.telefone())
						.especialidade(data.especialidade())
						.email(data.email())
						.senha(data.senha()))
				.map(credenciadoRepository::save)
				.map(Credenciado::uuid);

		credenciado.orElseThrow(() -> new IllegalArgumentException("Dados para credenciamento invalidos"));

		return credenciado;
	}
}
