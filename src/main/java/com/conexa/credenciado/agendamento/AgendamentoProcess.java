package com.conexa.credenciado.agendamento;

import java.util.Optional;

import jakarta.validation.Valid;

public interface AgendamentoProcess {

	public Optional<String> process(@ Valid final AgendamentoInput input);
}
