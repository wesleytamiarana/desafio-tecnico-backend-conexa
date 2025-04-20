package com.conexa.seguranca.logout;

import static com.conexa.api.Constantes.rootPath;
import static lombok.AccessLevel.PROTECTED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;


@RestController
@RequestMapping(rootPath)
@NoArgsConstructor(access = PROTECTED)
public class LogoutResource {

	@Autowired
	private LogoutProcess logoutProcess;


	@PostMapping(path = { "/logoff" })
	public ResponseEntity<Void> efetuar(@NotNull @RequestHeader("Authorization") final String token) {
		logoutProcess.process(token);

		return ResponseEntity.ok().build();
	}
}
