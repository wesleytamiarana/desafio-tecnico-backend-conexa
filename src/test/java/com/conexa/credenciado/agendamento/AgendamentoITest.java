package com.conexa.credenciado.agendamento;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.conexa.api.Constantes;
import com.conexa.seguranca.login.LoginInput;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yaml")
public class AgendamentoITest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private  AgendamentoRepository agendamentoRepository;


	@BeforeEach
	void beforeEach() {
		agendamentoRepository.deleteAll();
	}

	@Test
	void testAgendamentoPacienteExistente() throws Exception {
		LoginInput credenciado = LoginInput.of("cardiologia@gmail.com", "itIs@Secret");

		AgendamentoPacienteInput paciente = new AgendamentoPacienteInput("124.797.750-11", "Sr. Joao");

		AgendamentoInput agendemento = new AgendamentoInput(LocalDateTime.now().plusDays(1), paciente);

		MvcResult loginResponse = mockMvc
				.perform(post(Constantes.rootPath.concat("/login"))
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(credenciado)))
				.andExpect(status().isOk())
				.andReturn();

		String token = loginResponse.getResponse().getContentAsString();

		MvcResult agendamentoResponse = mockMvc
				.perform(post(Constantes.rootPath.concat("/attendance"))
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", token)
						.content(objectMapper.writeValueAsString(agendemento)))
				.andExpect(status().isCreated())
				.andReturn();

		assertThat(agendamentoResponse.getResponse().getContentAsString())
		.isNotNull()
		.isNotBlank();
	}
}
