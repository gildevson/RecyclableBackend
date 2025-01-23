package com.reciclaveis.reciclaveis;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReciclaveisApplication {

	public static void main(String[] args) {
		// Exibir o diretório de execução para depuração
		System.out.println("Diretório atual: " + System.getProperty("user.dir"));

		// Carregar as variáveis do .env
		Dotenv dotenv = Dotenv.configure()
				.directory("reciclaveis")
				.filename(".env") // Diretório relativo (raiz do projeto)
				.load();

		// Validar as variáveis do .env
		validarVariaveis(dotenv);

		// Testar se as variáveis foram carregadas
		System.out.println("DB_HOST: " + dotenv.get("DB_HOST"));
		System.out.println("DB_PORT: " + dotenv.get("DB_PORT"));
		System.out.println("DB_NAME: " + dotenv.get("DB_NAME"));
		System.out.println("DB_USERNAME: " + dotenv.get("DB_USERNAME"));
		System.out.println("DB_PASSWORD: " + dotenv.get("DB_PASSWORD"));

		// Definir as variáveis como propriedades do sistema
		System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
		System.setProperty("DB_PORT", dotenv.get("DB_PORT"));
		System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		// Iniciar a aplicação Spring Boot
		SpringApplication.run(ReciclaveisApplication.class, args);
	}

	private static void validarVariaveis(Dotenv dotenv) {
		String[] variaveisObrigatorias = {"DB_HOST", "DB_PORT", "DB_NAME", "DB_USERNAME", "DB_PASSWORD"};

		for (String variavel : variaveisObrigatorias) {
			if (dotenv.get(variavel) == null || dotenv.get(variavel).isEmpty()) {
				throw new IllegalStateException("Erro: A variável de ambiente '" + variavel + "' está ausente ou vazia. Verifique o arquivo .env.");
			}
		}
	}
}
