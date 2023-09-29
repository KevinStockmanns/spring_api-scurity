package com.security.domain.usuarios.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(
    @NotBlank(message = "El username es obligatorio.")
    String username,

    @NotBlank(message = "La clave es obligatoria.")
    String clave
) {
    
}
