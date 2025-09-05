package com.reciclaveis.reciclaveis.dto;

public record ClienteResponseDTO(
        java.util.UUID id,
        String clienteNome,
        String clienteCnpjCpf,
        String clienteEmail,
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
        String situacao, java.time.LocalDateTime createdAt
) {}
