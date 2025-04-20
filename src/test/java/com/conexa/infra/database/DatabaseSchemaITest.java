package com.conexa.infra.database;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yaml")
public class DatabaseSchemaITest {

	@Autowired
	private DataSource dataSource;

	@Test
	void tabelaCredenciasDeveExistir() throws Exception {
		try (Connection conn = dataSource.getConnection()) {
			ResultSet rs = conn.getMetaData().getTables(null, null, "CREDENCIAIS", null);
			assertTrue(rs.next(), "Tabela CREDENCIAIS deve existir.");
		}
	}

	@Test
	void tabelaDeCredenciadosDeveExistir() throws Exception {
		try (Connection conn = dataSource.getConnection()) {
			ResultSet rs = conn.getMetaData().getTables(null, null, "CREDENCIADOS", null);
			assertTrue(rs.next(), "Tabela CREDENCIADOS deve existir.");
		}
	}

	@Test
	void tabelaDePacientesDeveExistir() throws Exception {
		try (Connection conn = dataSource.getConnection()) {
			ResultSet rs = conn.getMetaData().getTables(null, null, "PACIENTES", null);
			assertTrue(rs.next(), "Tabela PACIENTES deve existir.");
		}
	}

	@Test
	void tabelaDeAgendamentosDeveExistir() throws Exception {
		try (Connection conn = dataSource.getConnection()) {
			ResultSet rs = conn.getMetaData().getTables(null, null, "AGENDAMENTOS", null);
			assertTrue(rs.next(), "Tabela AGENDAMENTOS deve existir.");
		}
	}


	@Test
	void tabelaDeTokenDeveExistir() throws Exception {
		try (Connection conn = dataSource.getConnection()) {
			ResultSet rs = conn.getMetaData().getTables(null, null, "TOKENS", null);
			assertTrue(rs.next(), "Tabela TOKENS deve existir.");
		}
	}
}
