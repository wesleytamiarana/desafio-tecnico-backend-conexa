package com.conexa.credenciado.credenciamento;

import static com.conexa.api.Constantes.rootPath;
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
public class CredenciamentoResource {

	@Autowired
	private final CredenciamentoProcess credenciamentoProcessor;

	@PostMapping(path = { "/signup" })
	public ResponseEntity<String> credenciar(@Valid @RequestBody final CredenciamentoInput request) {
		return credenciamentoProcessor
				.process(request)
				.map(uuid -> ResponseEntity.created(null).body(uuid))
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}
}
