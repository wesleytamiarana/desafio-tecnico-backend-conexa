package com.conexa.seguranca.token;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.trimToNull;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.conexa.seguranca.credenciais.Credenciais;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Table(name = "tokens")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(staticName = "of", access = PROTECTED)
@Accessors(fluent = true, chain = true)
public class Token {

	@Id
	private String uuid;

	@Column(name = "valor", nullable = false, unique = true)
	private String valor;

	@MapsId
	@OneToOne
	@Setter
	@JoinColumn(name = "uuid")
	private Credenciais credenciais;


	public Token valor(final String valor) {
		this.valor = trimToNull(valor);
		return this;
	}

	public String email() {
		return ofNullable(this.credenciais).map(Credenciais::email).orElse(null);
	}


	@Override
	public String toString() {
		return new ToStringBuilder(this, JSON_STYLE)
				.append("uuid", this.uuid)
				.append("crededenciais", this.credenciais)
				.append("valor", this.valor)
				.toString();
	}


	public static Token of() {
		return new Token();
	}

	public static Token of(final Credenciais credenciais) {
		return of().credenciais(credenciais);
	}


	public static Token of(final Credenciais credenciais, final String valor) {
		return of(credenciais).valor(valor);
	}
}
