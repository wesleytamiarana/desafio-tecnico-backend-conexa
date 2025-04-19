package com.conexa.credenciado.agendamento;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
	private  AgendamentoRepository credenciadoRepository;


	@BeforeEach
	void beforeEach() {
		credenciadoRepository.deleteAll();
	}

	@Test
	void testAgendamentoPacienteExistente() throws Exception {
		AgendamentoPacienteInput paciente = new AgendamentoPacienteInput("124.797.750-11", "Sr. Joao");

		AgendamentoInput payload = new AgendamentoInput("17-07-2026 07:07:00", paciente);

		MvcResult response = mockMvc
				.perform(post(Constantes.rootPath.concat("/attendance"))
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(payload)))
				.andExpect(status().isCreated())
				.andReturn();

		assertThat(response.getResponse().getContentAsString())
		.isNotNull()
		.isNotBlank();
	}
}
