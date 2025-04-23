package com.conexa.credenciado.credenciamento;

import static com.conexa.credenciado.credenciamento.CredenciadoMessages.*;
import static com.conexa.credenciado.credenciamento.CredenciadoMessages.cpfTananhoInvalido;
import static com.conexa.credenciado.credenciamento.CredenciadoMessages.credenciaisObrigatorias;
import static com.conexa.credenciado.credenciamento.CredenciadoMessages.dataNascimentoObrigatoria;
import static com.conexa.credenciado.credenciamento.CredenciadoMessages.especialidadeObrigatoria;
import static com.conexa.credenciado.credenciamento.CredenciadoMessages.telefoneObrigatorio;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.REFRESH;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.TemporalType.DATE;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.trimToNull;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;

import com.conexa.api.validador.constraints.CPF;
import com.conexa.seguranca.credenciais.Credenciais;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "credenciados")
@NoArgsConstructor(access = PROTECTED)
@Accessors(fluent = true, chain = true)
public class Credenciado {

	@Transient
	private final DateTimeFormatter formatadorData = ofPattern("dd/MM/yyyy");

	@Id
	@Getter
	private String uuid;

	@Getter
	@CPF(message = cpfInvalido)
	@NotBlank(message = cpfObrigatorio)
	@Length(min = 11, max = 14, message = cpfTananhoInvalido)
	@Column(name = "cpf", nullable = false, unique = true)
	private String cpf;

	@Getter
	@Temporal(DATE)
	@Column(name = "data_nascimento")
	@NotNull(message = dataNascimentoObrigatoria)
	private LocalDate dataNascimento;

	@Getter
	@Column(name = "telefone")
	@NotBlank(message = telefoneObrigatorio)
	private String telefone;

	@Getter
	@Column(name = "especialidade")
	@NotBlank(message = especialidadeObrigatoria)
	private String especialidade;

	@MapsId
	@Valid
	@OneToOne(cascade = { MERGE, REFRESH, REMOVE })
	@JoinColumn(name = "uuid")
	@NotNull(message = credenciaisObrigatorias)
	private Credenciais credenciais;


	private Credenciado credenciais(final Credenciais credenciais) {
		this.credenciais = credenciais;
		return this;
	}


	public Credenciado cpf(final String cpf) {
		this.cpf = ofNullable(trimToNull(cpf))
				.map(source -> source.replaceAll("[^\\d]", ""))
				.orElse(null);
		return this;
	}


	public Credenciado dataNascimento(final LocalDate dataHora) {
		this.dataNascimento = dataHora;
		return this;
	}


	private Credenciado dataNascimento(final Optional<String> dataHora) {
		return this.dataNascimento(dataHora.map(formatadorData::parse).map(LocalDate::from).orElse(null));
	}


	public Credenciado dataNascimento(final String dataHora) {
		return this.dataNascimento(ofNullable(trimToNull(dataHora)));
	}


	public Credenciado telefone(final String telefone) {
		this.telefone = ofNullable(trimToNull(telefone))
				.map(source -> source.replaceAll("[^\\d]", ""))
				.orElse(null);
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

	public String senha() {
		return ofNullable(this.credenciais).map(Credenciais::senha).orElse(null);
	}


	@Override
	public String toString() {
		return new ToStringBuilder(this, JSON_STYLE)
				.append("uuid", this.uuid)
				.append("cpf", this.cpf)
				.append("dataNascimento", this.dataNascimento)
				.append("telefone", this.telefone)
				.append("especialidade", this.especialidade)
				.append("email", this.email())
				.append("senha", "********")
				.toString();
	}


	public static Credenciado of() {
		return new Credenciado().credenciais(Credenciais.of());
	}
}
