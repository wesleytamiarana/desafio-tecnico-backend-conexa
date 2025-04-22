package com.conexa;

import static lombok.AccessLevel.PRIVATE;

import com.conexa.credenciado.agendamento.AgendamentoMessages;
import com.conexa.credenciado.credenciamento.CredenciamentoMessages;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class AplicacaoMessages {

	public static final CredenciamentoMessages credenciamento = CredenciamentoMessages.SELF;

	public static final AgendamentoMessages agendamento = AgendamentoMessages.SELF;
}
