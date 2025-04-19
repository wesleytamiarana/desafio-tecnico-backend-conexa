package com.conexa.credenciado.credenciamento;

import java.util.Optional;

public interface CredenciamentoProcess {

	public Optional<String> process(final CredenciamentoInput input);
}
