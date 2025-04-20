package com.conexa.credenciado.agendamento;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.conexa.credenciado.credenciamento.Credenciado;
import com.conexa.credenciado.credenciamento.CredenciadoRepository;
import com.conexa.paciente.Paciente;
import com.conexa.paciente.PacienteRepository;
import com.conexa.seguranca.credenciais.CredenciaisService;

import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@NoArgsConstructor(access = PROTECTED)
public class AgendamentoProcessor implements AgendamentoProcess {

	@Autowired
	private CredenciaisService credenciaisService;

	@Autowired
	private CredenciadoRepository credenciadoRepository;

	@Autowired
	private PacienteRepository pacienteRepository;

	@Autowired
	private AgendamentoRepository agendamentoRepository;


	public Optional<Agendamento> process(final Optional<AgendamentoInput> input) {
		Agendamento agendamento = null;

		input.orElseThrow(() -> new IllegalArgumentException("agendamento.dados.requeridos"));

		Optional<String> emailCredenciado = credenciaisService.emailAutenticado();

		emailCredenciado.orElseThrow(() -> new BadCredentialsException("agendamento.credenciado.nao.autenticado"));

		Optional<Credenciado> credenciado = emailCredenciado.flatMap(credenciadoRepository::findByEmail);

		Optional<Paciente> paciente = input.map(AgendamentoInput::cpfPaciente).flatMap(pacienteRepository::findByCpf);

		if(!paciente.isPresent()) {
			paciente = input
					.map(source -> Paciente.of()
							.cpf(source.cpfPaciente())
							.nome(source.nomePaciente()))
					.map(pacienteRepository::save);
		}

		agendamento = Agendamento.of();

		input.map(AgendamentoInput::dataHora).ifPresent(agendamento::dataHora);

		credenciado.ifPresent(agendamento::medico);

		paciente.ifPresent(agendamento::paciente);

		return of(agendamento).map(agendamentoRepository::save);
	}


	@Override
	public Optional<String> process(final AgendamentoInput input) {
		return this.process(ofNullable(input)).map(Agendamento::uuid);
	}
}
