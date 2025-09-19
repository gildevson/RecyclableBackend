# Sistemas de Gestão de reiclaveis

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.1/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.1/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.1/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.4.1/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.4.1/reference/using/devtools.html)
* [Validation](https://docs.spring.io/spring-boot/3.4.1/reference/io/validation.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)

### Maven Parent overrides

Due to Maven's design, Yes elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

### Referencia das dependencias 

spring-boot-starter-data-jpa
Fornece JPA/Hibernate, transações e integração com bancos relacionais. É a base para @Entity, JpaRepository, @Transactional, etc.

spring-boot-starter-security
Adiciona autenticação/autorização, filtros de segurança e integrações (HTTP Basic, form login, filtros JWT customizados, BCrypt, etc.).

spring-boot-starter-validation
Suporte a validações Bean Validation/Jakarta Validation (@NotNull, @Email, @Valid) em DTOs/controladores.

spring-boot-starter-web
Cria APIs REST com Spring MVC (Tomcat embutido, Jackson para JSON, @RestController, @ControllerAdvice, etc.).

io.github.cdimascio:java-dotenv (5.2.2)
Carrega variáveis a partir de um arquivo .env em tempo de execução. Útil em projetos simples, mas no Spring Boot normalmente preferimos application.properties/variáveis de ambiente.

spring-boot-devtools (runtime, optional)
Hot reload/autorestart para desenvolvimento, cache desativado, LiveReload. Não usar em produção.

org.postgresql:postgresql (runtime)
Driver JDBC do PostgreSQL. Necessário para o Hibernate/JPA conversarem com o banco.

spring-boot-starter-test (test)
JUnit, AssertJ, Spring Test, Mockito, etc., para escrever testes unitários e de integração.

net.sf.jasperreports:jasperreports (7.0.3)
Engine do JasperReports para preencher e exportar relatórios (.jrxml/.jasper). Costuma puxar dependências transientes (commons, beans, etc.).

com.lowagie:itext (2.1.7)
Biblioteca antiga do iText para gerar PDF. Compatível com Jasper em muitos cenários, porém é muito desatualizada e com licenciamento sensível (veja notas abaixo).

net.logstash.logback:logstash-logback-encoder (7.2)
Encoder do Logback para emitir logs em JSON (ótimo para ELK/Logstash). Permite configurar pattern JSON no logback-spring.xml.

org.springdoc:springdoc-openapi-ui (1.7.0)
Gera documentação OpenAPI e expõe o Swagger UI (ex.: /swagger-ui.html / /swagger-ui/index.html). Atenção: a série 1.x foi pensada para Spring Boot 2; para Boot 3.x o recomendado é a série 2.x.

org.projectlombok:lombok (provided)
Anotações como @Getter, @Builder, @Data, reduzindo boilerplate. Usada apenas em compilação (IDE precisa do plugin Lombok).

io.jsonwebtoken:jjwt-api/jjwt-impl/jjwt-jackson (0.11.5)
Geração/validação de tokens JWT.

jjwt-api: interfaces e tipos públicos;

jjwt-impl (runtime): implementação;

jjwt-jackson (runtime): serialização/deserialização JSON via Jackson.

spring-security-crypto
Utilitários criptográficos (ex.: BCryptPasswordEncoder) fora do contexto de Web/Security completo.

spring-boot-starter-mail (aparece duas vezes)
Envio de e-mails com JavaMailSender (SMTP), templates, etc. Remova a duplicata.
