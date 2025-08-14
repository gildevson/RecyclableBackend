package com.reciclaveis.reciclaveis.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import java.util.UUID;

@Data
@Entity(name = "cliente") // é utilizando para criar tabela de acordo com jpa
public class Cliente {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String clienteNome;
    private String clienteCnpj;
    private String clienteCpf;
    private String clienteTelefone;
    private String clienteEmail;
    private String clienteCelular;
    private String cliente;
}
