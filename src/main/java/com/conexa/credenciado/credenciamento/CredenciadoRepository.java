package com.conexa.credenciado.credenciamento;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CredenciadoRepository extends JpaRepository<Credenciado, String> {

	@Query("SELECT c FROM Credenciado c WHERE c.credenciais.email = :email AND c.credenciais IS NOT NULL")
	Optional<Credenciado> findByEmail(@Param("email") final String email);
}
