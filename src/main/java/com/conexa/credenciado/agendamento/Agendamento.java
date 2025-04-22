package com.conexa.credenciado.agendamento;

import static com.conexa.credenciado.agendamento.AgendamentoMessages.*;
import static com.conexa.credenciado.agendamento.AgendamentoMessages.dataAgendamentoObrigatoria;
import static com.conexa.credenciado.agendamento.AgendamentoMessages.pacienteObrigatorio;
import static jakarta.persistence.GenerationType.UUID;
import static jakarta.persistence.TemporalType.TIMESTAMP;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.trimToNull;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.conexa.api.validador.constraints.DataFutura;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

	@Transient
	private final DateTimeFormatter formatadorData =  ofPattern("yyyy-MM-dd HH:mm:ss");

	@Id
	@Column(name = "uuid")
	@GeneratedValue(strategy = UUID)
	private String uuid;

	@Temporal(TIMESTAMP)
	@Column(name = "data_hora")
	@NotNull(message = dataAgendamentoObrigatoria)
	@DataFutura(message = dataAgendamentoPrazoInvalido)
	private LocalDateTime dataHora;

	@Valid
	@Setter
	@ManyToOne()
	@NotNull(message = credenciadoObrigatorio)
	@JoinColumn(name = "uuid_credenciado", referencedColumnName = "uuid")
	private Credenciado medico;

	@Setter
	@ManyToOne()
	@NotNull(message = pacienteObrigatorio)
	@JoinColumn(name = "uuid_paciente", referencedColumnName = "uuid")
	private Paciente paciente;


	public Agendamento dataHora(final LocalDateTime dataHora) {
		this.dataHora = dataHora;
		return this;
	}


	public Agendamento dataHora(final Optional<String> dataHora) {
		return this.dataHora(dataHora.map(formatadorData::parse).map(LocalDateTime::from).orElse(null));
	}


	public Agendamento dataHora(final String dataHora) {
		return this.dataHora(ofNullable(trimToNull(dataHora)));
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
}
