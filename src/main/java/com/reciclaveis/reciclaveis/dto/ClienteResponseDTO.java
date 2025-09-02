package com.reciclaveis.reciclaveis.dto;

import java.util.UUID;
import java.time.LocalDateTime;

public record ClienteResponseDTO(
        UUID id,
        String clienteNome,
        String clienteCnpj,
        String clienteCpf,
        String clienteTelefone,
        String clienteEmail,
        String clienteCelular,
        String clienteEndereco,
        String clienteBairro,
        String clienteCidade,
        String clienteEstado,
        String clienteNacionalidade,
        String clienteNumeroCasa,
        String clienteComplemento,
        String clienteInscricaoMunicipal,
        String clienteEstadual,
        String clienteSituacao,
        String situacao, LocalDateTime createdAt


) {}
