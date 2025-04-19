package com.conexa.credenciado.agendamento;

import static jakarta.persistence.GenerationType.UUID;
import static jakarta.persistence.TemporalType.TIMESTAMP;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.conexa.credenciado.credenciamento.Credenciado;
import com.conexa.paciente.Paciente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Entity
@Table(name = "agendamentos")
@NoArgsConstructor(access = PROTECTED)
@Accessors(fluent = true, chain = true)
public class Agendamento {

	@Id
	@Column(name = "uuid")
	@GeneratedValue(strategy = UUID)
	private String uuid;

	@Temporal(TIMESTAMP)
	@Column(name = "data_hora")
	private Date dataHora;

	@Setter
	@ManyToOne()
	@JoinColumn(name = "uuid_credenciado", referencedColumnName = "uuid")
	private Credenciado medico;

	@Setter
	@ManyToOne()
	@JoinColumn(name = "uuid_paciente", referencedColumnName = "uuid")
	private Paciente paciente;


	public Agendamento dataHora(final Date dataHora) {
		this.dataHora = dataHora;
		return this;
	}


	public Agendamento dataHora(final String dataHora) {
		return this;
	}


	@Override
	public String toString() {
		return new ToStringBuilder(this, JSON_STYLE)
				.append("uuid", this.uuid)
				.append("dataHora", this.dataHora)
				.append("medico", this.medico)
				.append("paciente", this.paciente)
				.toString();
	}


	public static Agendamento of() {
		return new Agendamento();
	}


	public static Agendamento of(final Paciente paciente) {
		return new Agendamento().paciente(paciente);
	}


	public static Agendamento agendadoPara(final String dataHora) {
		return new Agendamento().dataHora(dataHora);
	}
}
