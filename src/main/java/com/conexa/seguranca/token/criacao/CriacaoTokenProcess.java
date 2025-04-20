package com.conexa.seguranca.token.criacao;

import java.util.Optional;

public interface CriacaoTokenProcess {

	public Optional<String> process(final String input);
}
