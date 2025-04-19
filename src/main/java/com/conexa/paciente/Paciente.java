package com.conexa.paciente;

import static jakarta.persistence.GenerationType.UUID;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.trimToNull;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "pacientes")
@NoArgsConstructor(access = PROTECTED)
@Accessors(fluent = true, chain = true)
public class Paciente {

	@Id
	@Getter
	@Column(name = "uuid")
	@GeneratedValue(strategy = UUID)
	private String uuid;

	@Getter
	@Column(name = "cpf", nullable = false, unique = true)
	private String cpf;

	@Getter
	@Column(name = "nome", nullable = false)
	private String nome;


	public Paciente cpf(final String cpf) {
		this.cpf = trimToNull(cpf);
		return this;
	}


	public Paciente nome(final String nome) {
		this.nome = trimToNull(nome);
		return this;
	}


	@Override
	public String toString() {
		return new ToStringBuilder(this, JSON_STYLE)
				.append("uuid", this.uuid)
				.append("cpf", this.cpf)
				.append("nome", this.nome)
				.toString();
	}


	public static Paciente of() {
		return new Paciente();
	}
}
