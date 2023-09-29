package com.security.domain.productos;

import java.math.BigDecimal;

import com.security.domain.productos.dto.CrearProductoDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data @AllArgsConstructor @NoArgsConstructor
public class Producto {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    
    private BigDecimal precio;


    
    public Producto(@Valid CrearProductoDTO datos) {
        this.nombre = datos.nombre();
        this.precio = datos.precio();
    }
}
