package com.conexa.seguranca.token;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.trimToNull;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.conexa.seguranca.credenciais.Credenciais;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(staticName = "of", access = PROTECTED)
@Accessors(fluent = true, chain = true)
public class Token {

	private String valor;

	@Setter
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