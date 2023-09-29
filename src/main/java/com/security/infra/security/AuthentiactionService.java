package com.security.infra.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.security.domain.usuarios.Usuario;
import com.security.domain.usuarios.UsuarioRepository;
import com.security.domain.usuarios.dto.AuthRequestDTO;
import com.security.domain.usuarios.dto.AuthResponseDTO;

import jakarta.validation.Valid;

@Service
public class AuthentiactionService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    public AuthResponseDTO login(@Valid AuthRequestDTO authRequestDTO) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            authRequestDTO.username(), authRequestDTO.clave()
        );
        authenticationManager.authenticate(authToken);

        Usuario usuario = usuarioRepository.findByUsername(authRequestDTO.username()).get();

        String jwt = jwtService.generateToken(usuario, generateExtraClaims(usuario));

        return new AuthResponseDTO(jwt);
    }

    private Map<String, Object> generateExtraClaims(Usuario usuario) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", usuario.getNombre());
        extraClaims.put("rol", usuario.getRol());
        extraClaims.put("permisos", usuario.getAuthorities());

        return extraClaims;
    }
    

}
