package com.conexa.credenciado.credenciamento;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.ToStringBuilder;

public record CredenciamentoInput (
		String email,
		String senha,
		String confirmacaoSenha,
		String especialidade,
		String cpf,
		LocalDate dataNascimento,
		String telefone) {

	@Override
	public String toString() {
		return new ToStringBuilder(this, JSON_STYLE)
				.append("cpf", this.cpf)
				.append("data_nascimento", this.dataNascimento)
				.append("telefone", this.telefone)
				.append("especialidade", this.especialidade)
				.append("email", this.email())
				.append("senha", "********")
				.append("confirmacaoSenha", "********")
				.toString();
	}
}
