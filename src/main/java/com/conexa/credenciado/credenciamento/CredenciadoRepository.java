package com.conexa.credenciado.credenciamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredenciadoRepository extends JpaRepository<Credenciado, String>{}
