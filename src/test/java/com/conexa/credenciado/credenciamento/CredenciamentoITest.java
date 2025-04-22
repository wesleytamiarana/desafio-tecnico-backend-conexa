package com.conexa.credenciado.credenciamento;

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
public class CredenciamentoITest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void beforeEach() {}

	@Test
	void testCredenciamentoComDadosValidos() throws Exception {
		CredenciamentoInput payload = new CredenciamentoInput("clinica_geral@gmail.com",
				"itIs@Secret",
				"itIs@Secret",
				"Clinica Geral",
				"124.797.750-11",
				"07/07/1970",
				"(81) 98342-2258");

		MvcResult response = mockMvc
				.perform(post(Constantes.rootPath.concat("/signup"))
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(payload)))
				.andExpect(status().isCreated())
				.andReturn();

		assertThat(response.getResponse().getContentAsString())
		.isNotNull()
		.isNotBlank();
	}
}
