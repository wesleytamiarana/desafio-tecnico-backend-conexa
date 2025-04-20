package com.conexa.seguranca.credenciais;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredenciaisRepository extends JpaRepository<Credenciais, String> {

	Optional<Credenciais> findByEmail(final String email);
}
