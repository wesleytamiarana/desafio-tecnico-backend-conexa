package com.conexa.credenciado.agendamento;

import static com.conexa.api.Constantes.rootPath;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping(rootPath)
@RequiredArgsConstructor(access = PROTECTED)
public class AgendamentoResource {

	@Autowired
	private final AgendamentoProcess agendamentoProcessor;


	@PostMapping(path = { "/attendance" })
	public ResponseEntity<String> agendar(@Valid @RequestBody final AgendamentoInput request) {
		return ofNullable(request)
				.flatMap(agendamentoProcessor::process)
				.map(uuid -> ResponseEntity.created(null).body(uuid))
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}
}
