package com.reciclaveis.reciclaveis.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ClienteRequestDTO(
        @NotBlank String clienteNome,
        String clienteRazao,
        @NotBlank String clienteCnpjCpf,
        @Email String clienteEmail,
        String clienteTelefone,
        String clienteCelular,
        String clienteEndereco,
        String clienteBairro,
        String clienteCidade,
        String clienteEstado,
        String clienteNacionalidade,
        String clienteNumeroCasa,
        String clienteComplemento,
        String clienteInscricaoMunicipal,
        String clienteInscricaoEstadual,
        String clienteSituacao,
        LocalDateTime createdAt
) {}
