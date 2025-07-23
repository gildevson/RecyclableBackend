package com.reciclaveis.reciclaveis;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class ReciclaveisApplication {

    private static final Logger logger = LoggerFactory.getLogger(ReciclaveisApplication.class);

    public static void main(String[] args) {
        // Carregar variáveis do .env
        Dotenv dotenv = Dotenv.configure()
                .directory(".") // Diretório atual
                .filename(".env")
                .load();

        validarVariaveis(dotenv);      // Validar se todas existem
        setarPropriedades(dotenv);     // Definir como propriedades do sistema

        logger.info("Iniciando a aplicação com as variáveis de ambiente carregadas.");
        logger.info("DB_HOST: {}", dotenv.get("DB_HOST"));
        logger.info("DB_PORT: {}", dotenv.get("DB_PORT"));

        SpringApplication.run(ReciclaveisApplication.class, args);
    }

    private static void validarVariaveis(Dotenv dotenv) {
        String[] obrigatorias = {
                "DB_HOST", "DB_PORT", "DB_NAME", "DB_USERNAME", "DB_PASSWORD",
                "jwt.secret", "EMAIL_HOST", "EMAIL_PORT", "EMAIL_USERNAME", "EMAIL_PASSWORD", "EMAIL_PROTOCOL"
        };

        for (String var : obrigatorias) {
            if (dotenv.get(var) == null || Objects.requireNonNull(dotenv.get(var)).isEmpty()) {
                throw new MissingEnvironmentVariableException("Erro: A variável '" + var + "' está ausente ou vazia.");
            }
        }
    }

    private static void setarPropriedades(Dotenv dotenv) {
        // JWT
        System.setProperty("jwt.secret", dotenv.get("jwt.secret"));

        // Datasource (montar a URL manualmente)
        String dbUrl = String.format("jdbc:postgresql://%s:%s/%s",
                dotenv.get("DB_HOST"),
                dotenv.get("DB_PORT"),
                dotenv.get("DB_NAME"));

        System.setProperty("spring.datasource.url", dbUrl);
        System.setProperty("spring.datasource.username", dotenv.get("DB_USERNAME"));
        System.setProperty("spring.datasource.password", dotenv.get("DB_PASSWORD"));

        // Email
        // Email (usando prefixos que o Spring entende)
        System.setProperty("spring.mail.host", dotenv.get("EMAIL_HOST"));
        System.setProperty("spring.mail.port", dotenv.get("EMAIL_PORT"));
        System.setProperty("spring.mail.username", dotenv.get("EMAIL_USERNAME"));
        System.setProperty("spring.mail.password", dotenv.get("EMAIL_PASSWORD"));
        System.setProperty("spring.mail.protocol", dotenv.get("EMAIL_PROTOCOL"));
        System.setProperty("spring.mail.properties.mail.smtp.auth", "true");
        System.setProperty("spring.mail.properties.mail.smtp.starttls.enable", "true");
        System.setProperty("spring.mail.properties.mail.smtp.ssl.enable", "true"); // necessário para a porta 465


        // Profile (opcional)
        if (dotenv.get("spring.profiles.active") != null) {
            System.setProperty("spring.profiles.active", dotenv.get("spring.profiles.active"));
        }
    }
}

class MissingEnvironmentVariableException extends RuntimeException {
    public MissingEnvironmentVariableException(String message) {
        super(message);
    }
}
