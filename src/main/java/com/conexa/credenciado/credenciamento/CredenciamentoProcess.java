package com.conexa.credenciado.credenciamento;

import java.util.Optional;

import jakarta.validation.Valid;

public interface CredenciamentoProcess {

	public Optional<String> process(@Valid final CredenciamentoInput input);
}
