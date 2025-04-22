package com.conexa.seguranca.logout;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public interface LogoutProcess {

	void process(@Valid @NotBlank final String token);
}
