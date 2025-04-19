package com.conexa.credenciado.agendamento;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexa.credenciado.credenciamento.Credenciado;
import com.conexa.credenciado.credenciamento.CredenciadoRepository;
import com.conexa.paciente.Paciente;
import com.conexa.paciente.PacienteRepository;

import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@NoArgsConstructor(access = PROTECTED)
public class AgendamentoProcessor implements AgendamentoProcess {

	@Autowired
	private CredenciadoRepository credenciadoRepository;

	@Autowired
	private PacienteRepository pacienteRepository;

	@Autowired
	private AgendamentoRepository agendamentoRepository;

	@Override
	public Optional<String> process(final AgendamentoInput input) {
		Agendamento agendamento;

		Optional<Credenciado> credenciado = credenciadoRepository.findByEmail("cardiologia@gmail.com");

		Optional<Paciente> paciente = pacienteRepository.findByCpf(input.cpfPaciente());

		if(!paciente.isPresent()) {
			paciente = ofNullable(pacienteRepository.save(Paciente.of().cpf(input.cpfPaciente()).nome(input.nomePaciente())));
		}

		agendamento = Agendamento.agendadoPara(input.dataHora());

		credenciado.ifPresent(agendamento::medico);

		paciente.ifPresent(agendamento::paciente);

		return of(agendamento).map(agendamentoRepository::save).map(Agendamento::uuid);
	}
}
