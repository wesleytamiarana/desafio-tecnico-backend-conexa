package com.conexa.seguranca.login;

import java.util.Optional;

import jakarta.validation.Valid;


public interface LoginProcess {

	public Optional<LoginOutput> process(@Valid final LoginInput input);
}
