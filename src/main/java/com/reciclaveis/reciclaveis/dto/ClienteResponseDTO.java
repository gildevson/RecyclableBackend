package com.reciclaveis.reciclaveis.dto;

import java.util.UUID;
import java.time.LocalDateTime;

public record ClienteResponseDTO(
        UUID id,
        String clienteNome,
        String clienteCnpj,
        String clienteEmail,
        String clienteTelefone,
        String clienteCelular,
        LocalDateTime createdAt
) {}
