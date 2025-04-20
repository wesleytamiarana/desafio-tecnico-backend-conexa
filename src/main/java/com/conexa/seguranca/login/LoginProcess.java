package com.conexa.seguranca.login;

import java.util.Optional;


public interface LoginProcess {

	public Optional<String> process(final LoginInput input);
}
