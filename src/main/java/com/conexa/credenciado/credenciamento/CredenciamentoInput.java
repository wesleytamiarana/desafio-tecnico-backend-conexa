package com.conexa.credenciado.credenciamento;

import static com.conexa.credenciado.credenciamento.CredenciamentoMessages.confirmacaoSenhaDivergente;
import static com.conexa.credenciado.credenciamento.CredenciamentoMessages.confirmacaoSenhaObrigatoria;
import static com.conexa.credenciado.credenciamento.CredenciamentoMessages.cpfObrigatorio;
import static com.conexa.credenciado.credenciamento.CredenciamentoMessages.cpfTananhoInvalido;
import static com.conexa.credenciado.credenciamento.CredenciamentoMessages.dataNascimentoObrigatorio;
import static com.conexa.credenciado.credenciamento.CredenciamentoMessages.dataNascimentoTamanhoInvalido;
import static com.conexa.credenciado.credenciamento.CredenciamentoMessages.emailObrigatorio;
import static com.conexa.credenciado.credenciamento.CredenciamentoMessages.especialidadeObrigatoria;
import static com.conexa.credenciado.credenciamento.CredenciamentoMessages.senhaDivergente;
import static com.conexa.credenciado.credenciamento.CredenciamentoMessages.senhaObrigatoria;
import static com.conexa.credenciado.credenciamento.CredenciamentoMessages.telefoneObrigatorio;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;

import com.conexa.api.validador.constraints.FieldEquals;

import jakarta.validation.constraints.NotBlank;

@FieldEquals
public record CredenciamentoInput (
		@NotBlank(message = emailObrigatorio)
		String email,

		@FieldEquals.To(message = senhaDivergente)
		@NotBlank(message = senhaObrigatoria)
		String senha,

		@FieldEquals.To(message = confirmacaoSenhaDivergente)
		@NotBlank(message = confirmacaoSenhaObrigatoria)
		String confirmacaoSenha,

		@NotBlank(message = especialidadeObrigatoria)
		String especialidade,

		@NotBlank(message = cpfObrigatorio)
		@Length(max = 14, message = cpfTananhoInvalido)
		String cpf,

		@NotBlank(message = dataNascimentoObrigatorio)
		@Length(min = 8, max = 10, message = dataNascimentoTamanhoInvalido)
		String dataNascimento,

		@NotBlank(message = telefoneObrigatorio)
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
