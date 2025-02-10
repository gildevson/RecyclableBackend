package com.reciclaveis.reciclaveis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"spring.datasource.url=",
		"spring.datasource.username=",
		"spring.datasource.password=",
		"spring.jpa.hibernate.ddl-auto=none"
})
public class ReciclaveisApplicationTests {

	@Test
	void contextLoads() {
		// Teste básico para verificar se o contexto carrega corretamente
	}
}
