package com.conexa.credenciado.credenciamento;

import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.trimToNull;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.conexa.seguranca.Credenciais;

import static jakarta.persistence.CascadeType.*;
import static java.util.Optional.ofNullable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import static jakarta.persistence.TemporalType.DATE;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "credenciados")
@NoArgsConstructor(access = PROTECTED)
@Accessors(fluent = true, chain = true)
public class Credenciado {

	@Id
	@Getter
	private String uuid;

	@Getter
	@Column(name = "cpf")
	private String cpf;

	@Getter
	@Temporal(DATE)
	@Column(name = "data_nascimento")
	private Date dataNascimento;

	@Getter
	@Column(name = "telefone")
	private String telefone;

	@Getter
	@Column(name = "especialidade")
	private String especialidade;

	@MapsId
	@OneToOne(cascade = { MERGE, REFRESH, REMOVE })
	@JoinColumn(name = "uuid")
	private Credenciais credenciais;


	private Credenciado credenciais(final Credenciais credenciais) {
		this.credenciais = credenciais;
		return this;
	}


	public Credenciado cpf(final String cpf) {
		this.cpf = trimToNull(cpf);
		return this;
	}


	public Credenciado dataNascimento(final Date data) {
		this.dataNascimento = data;
		return this;
	}


	public Credenciado dataNascimento(final String data) {
		return this;
	}


	public Credenciado telefone(final String telefone) {
		this.telefone = telefone;
		return this;
	}


	public Credenciado especialidade(final String especialidade) {
		this.especialidade = trimToNull(especialidade);
		return this;
	}


	public Credenciado email(final String email) {
		ofNullable(this.credenciais)
		.orElseGet(() -> this.credenciais = Credenciais.of())
		.email(email);

		return this;
	}


	public String email() {
		return ofNullable(this.credenciais).map(Credenciais::email).orElse(null);
	}


	public Credenciado senha(final String senha) {
		ofNullable(this.credenciais)
		.orElseGet(() -> this.credenciais = Credenciais.of())
		.senha(senha);

		return this;
	}


	@Override
	public String toString() {
		return new ToStringBuilder(this, JSON_STYLE)
				.append("uuid", this.uuid)
				.append("cpf", this.cpf)
				.append("data_nascimento", this.dataNascimento)
				.append("telefone", this.telefone)
				.append("especialidade", this.especialidade)
				.append("email", this.email())
				.append("senha", "********")
				.toString();
	}


	public String senha() {
		return ofNullable(this.credenciais).map(Credenciais::senha).orElse(null);
	}


	public static Credenciado of() {
		return new Credenciado().credenciais(Credenciais.of());
	}
}
