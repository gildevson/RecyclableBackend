# Getting Started

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

### 

* [config → Contém classes relacionadas à configuração da aplicação, como configurações do Spring Boot, beans, segurança, etc.](...)
* [controller → Contém os controladores (Controllers) da aplicação, responsáveis por receber as requisições HTTP e direcioná-las para os serviços apropriados.](...)
* [dto (Data Transfer Object) → Contém classes utilizadas para transferir dados entre diferentes camadas da aplicação, sem expor diretamente as entidades do banco de dados.](...)
* [entity → Contém as classes que representam as entidades do banco de dados, geralmente mapeadas com anotações JPA (@Entity, @Table, @Id, etc.).](...)
* [initializer → Provavelmente contém classes de inicialização, como configuração de dados iniciais ou eventos que devem ocorrer no início da aplicação.](...)
* [repository → Contém as interfaces que fazem a comunicação com o banco de dados, geralmente estendendo JpaRepository ou CrudRepository.](...)
* [service → Contém as regras de negócio da aplicação. As classes aqui servem como intermediárias entre os controllers e os repositories.](...)
* [util → Contém classes auxiliares ou utilitárias, como funções de formatação, manipulação de datas, validações, etc.](...)