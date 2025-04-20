package com.conexa.seguranca.credenciais;

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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Getter
@Table(name = "credenciais")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(staticName = "of", access = PROTECTED)
@Accessors(fluent = true, chain = true)
public class Credenciais {

	@Id
	@Column(name = "uuid")
	@GeneratedValue(strategy = UUID)
	private String uuid;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@NotNull
	@Column(name = "senha", nullable = false)
	private String senha;


	public Credenciais email(final String email) {
		this.email = trimToNull(email);
		return this;
	}


	public Credenciais senha(final String senha) {
		this.senha = trimToNull(senha);
		return this;
	}


	@Override
	public String toString() {
		return new ToStringBuilder(this, JSON_STYLE)
				.append("uuid", this.uuid)
				.append("email", this.email)
				.append("senha", "********")
				.toString();
	}


	public static Credenciais of() {
		return new Credenciais();
	}
}
