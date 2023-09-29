package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.domain.usuarios.dto.AuthRequestDTO;
import com.security.domain.usuarios.dto.AuthResponseDTO;
import com.security.infra.security.AuthentiactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthentiactionService authentiactionService;
    
    @PreAuthorize("permitAll")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthRequestDTO authRequestDTO){

        AuthResponseDTO jwtDto = authentiactionService.login(authRequestDTO);
        return ResponseEntity.ok().body(jwtDto);

    }

    @PreAuthorize("permitAll")
    @GetMapping("/public")
    public String publico(){
        return "Hola, acceso publico";
    }
}
