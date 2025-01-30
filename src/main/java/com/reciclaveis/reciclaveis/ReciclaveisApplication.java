package com.reciclaveis.reciclaveis;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReciclaveisApplication {

	private static final Logger logger = LoggerFactory.getLogger(ReciclaveisApplication.class);

	public static void main(String[] args) {
		// Carregar variáveis do .env
		Dotenv dotenv = Dotenv.configure()
				.directory("reciclaveis") // Caminho do .env
				.filename(".env")
				.load();

		// Validar variáveis do .env
		validarVariaveis(dotenv);

		// Configurar variáveis como propriedades do sistema
		System.setProperty("jwt.secret", dotenv.get("jwt.secret"));
		System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
		System.setProperty("DB_PORT", dotenv.get("DB_PORT"));
		System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		// Logs para depuração
		logger.info("Iniciando a aplicação com as variáveis de ambiente carregadas.");
		logger.info("DB_HOST: {}", dotenv.get("DB_HOST"));
		logger.info("DB_PORT: {}", dotenv.get("DB_PORT"));

		SpringApplication.run(ReciclaveisApplication.class, args);
	}

	private static void validarVariaveis(Dotenv dotenv) {
		String[] variaveisObrigatorias = {"DB_HOST", "DB_PORT", "DB_NAME", "DB_USERNAME", "DB_PASSWORD", "jwt.secret"};

		for (String variavel : variaveisObrigatorias) {
			if (dotenv.get(variavel) == null || dotenv.get(variavel).isEmpty()) {
				throw new IllegalStateException("Erro: A variável de ambiente '" + variavel + "' está ausente ou vazia. Verifique o arquivo .env.");
			}
		}
	}
}
