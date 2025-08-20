package com.reciclaveis.reciclaveis.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequestDTO(
        @NotBlank String clienteNome,
        @NotBlank String clienteCnpj,
        @Email String clienteEmail,
        String clienteTelefone,
        String clienteCelular
) {}
