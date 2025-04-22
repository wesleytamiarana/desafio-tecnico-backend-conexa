package com.conexa.seguranca.token;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yaml")
public class TokenCacheManagerTest {

	@Autowired
	private TokenGenerator tokenGenerator;

	@Autowired
	private TokenCacheManager tokenCacheManager;

	@Test
	public void revogarUmTokenEnviado() {
		String token = tokenGenerator
				.generate("tokenToBeStore@email.com")
				.get();

		tokenCacheManager.store(token);

		assertThat(tokenCacheManager.find(token))
		.isNotEmpty()
		.get()
		.isEqualTo(token);
	}
}
