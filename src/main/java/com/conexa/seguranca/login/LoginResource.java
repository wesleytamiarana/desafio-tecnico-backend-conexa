package com.conexa.seguranca.login;

import static com.conexa.api.Constantes.rootPath;
import static lombok.AccessLevel.PROTECTED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.NoArgsConstructor;


@RestController
@RequestMapping(rootPath)
@NoArgsConstructor(access = PROTECTED)
public class LoginResource {

	@Autowired
	private LoginProcess loginProcess;


	@PostMapping(path = { "/login" })
	public ResponseEntity<String> efetuar(@Valid @RequestBody final LoginInput request) {
		return loginProcess
				.process(request)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}
