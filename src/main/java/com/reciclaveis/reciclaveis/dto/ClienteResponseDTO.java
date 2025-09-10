package com.reciclaveis.reciclaveis.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClienteResponseDTO(
        UUID clienteId,
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
        LocalDateTime createdAt
) {}
