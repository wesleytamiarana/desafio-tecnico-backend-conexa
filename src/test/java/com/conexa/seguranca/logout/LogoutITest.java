package com.conexa.seguranca.logout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.conexa.seguranca.token.TokenCacheManager;
import com.conexa.seguranca.token.TokenReader;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yaml")
public class LogoutITest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TokenReader tokenReader;

	@Autowired
	private TokenCacheManager tokenCacheManager;

	@Test
	void testLogoutComCredenciaisValidas() throws Exception {
		String token;

		MvcResult response;

		LoginInput payload = LoginInput.of("master02@cnx.com", "itIs@Secret");

		response = mockMvc
				.perform(post(Constantes.rootPath.concat("/login"))
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(payload)))
				.andExpect(status().isOk())
				.andReturn();

		token = response.getResponse().getContentAsString();

		assertThat(tokenReader.email(token))
		.isNotEmpty()
		.get()
		.isEqualTo(payload.email());

		mockMvc.perform(post(Constantes.rootPath.concat("/logoff"))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andExpect(status().isOk());

		assertThat(tokenCacheManager.find(token))
		.isNotEmpty()
		.get()
		.isEqualTo(token);
	}
}
