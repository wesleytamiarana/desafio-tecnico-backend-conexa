package com.conexa.credenciado.agendamento;

import java.util.Optional;

public interface AgendamentoProcess {

	public Optional<String> process(final AgendamentoInput input);
}
