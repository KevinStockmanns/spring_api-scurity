package com.security.domain.productos.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public record CrearProductoDTO(
    @NotBlank(message = "El nombre es obligatorio.")
    String nombre,

    @DecimalMin(value = "0.01", inclusive = true, message = "El precio debe ser al menos 0.01")
    BigDecimal precio
) {
    
}
