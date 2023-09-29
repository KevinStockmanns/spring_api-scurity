package com.security.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.security.domain.productos.Producto;
import com.security.domain.productos.ProductoRepository;
import com.security.domain.productos.dto.CrearProductoDTO;
import com.security.domain.productos.dto.RespuestaProductoDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class ProductoController {
    
    @Autowired
    private ProductoRepository productoRepository;

    @PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')")
    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> listarProductos(){
        return ResponseEntity.ok().body(productoRepository.findAll());
    }

    @PreAuthorize("hasAuthority('SAVE_ONE_PRODUCT')")
    @PostMapping("/producto")
    public ResponseEntity<RespuestaProductoDTO> crearProducto(@RequestBody @Valid CrearProductoDTO datos, UriComponentsBuilder UriComponentsBuilder){
        Producto producto = new Producto(datos);
        productoRepository.save(producto);

        URI uri = UriComponentsBuilder.path("/producto/{id}").buildAndExpand(producto.getId()).toUri();

        return ResponseEntity.created(uri).body(new RespuestaProductoDTO(producto));
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<RespuestaProductoDTO> getProducto(@PathVariable Long id){
        Producto producto = productoRepository.getReferenceById(id);
        return ResponseEntity.ok().body(new RespuestaProductoDTO(producto));
    }
}
