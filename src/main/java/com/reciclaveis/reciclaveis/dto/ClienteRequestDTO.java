package com.reciclaveis.reciclaveis.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequestDTO(
        @NotBlank String clienteNome,
        @NotBlank String clienteCnpj,
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
        String clienteSituacao
) {}
