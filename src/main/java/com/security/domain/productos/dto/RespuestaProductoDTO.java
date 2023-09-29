package com.security.domain.productos.dto;

import java.math.BigDecimal;

import com.security.domain.productos.Producto;

public record RespuestaProductoDTO(
    Long id,
    String nombre,
    BigDecimal precio
) {
    public RespuestaProductoDTO(Producto producto){
        this(producto.getId(), producto.getNombre(), producto.getPrecio());
    }
}
