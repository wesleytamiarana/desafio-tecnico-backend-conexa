package com.conexa.seguranca.token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

	@Query("SELECT t FROM Token t WHERE t.credenciais.email = :email AND t.credenciais IS NOT NULL")
	Optional<Token> findByEmail(@Param("email") final String email);
}
