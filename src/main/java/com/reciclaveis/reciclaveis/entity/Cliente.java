package com.reciclaveis.reciclaveis.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Hibernate 6+
    private UUID clienteid;
    private String clienteNome;
    private String clienteRazao;

    @Column(unique = true)
    private String clienteCnpjCpf;
    private String clienteEmail;
    private String clienteTelefone;
    private String clienteCelular;
    private String clienteNomeContato;
    private String clienteEndereco;
    private String clienteBairro;
    private String clienteCidade;
    private String clienteEstado;
    private String clienteNacionalidade;
    private String clienteNumeroCasa;
    private String clienteComplemento;
    private String clienteInscricaoMunicipal;
    private String clienteInscricaoEstadual;
    private String clienteSituacao;

    @CreationTimestamp
    private LocalDateTime createdAt; // renomeado

    // Nada de getters/setters manuais: o Lombok @Data já gera.
}
