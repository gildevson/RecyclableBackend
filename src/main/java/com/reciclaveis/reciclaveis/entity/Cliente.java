package com.reciclaveis.reciclaveis.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "cliente") // é utilizando para criar tabela de acordo com jpa
public class Cliente {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotBlank
    private String clienteNome;

    @Column(unique = true)

    @CNPJ(message = "CNPJ INVALIDO")
    @NotBlank(message = "o cnpj do cliente é obrigatorio")
    private String clienteCnpj;
    private String clienteCpf;
    private String clienteTelefone;

    @Email
    private String clienteEmail;

    private String clienteCelular;


    @CreationTimestamp
    private LocalDateTime createAt;
}
