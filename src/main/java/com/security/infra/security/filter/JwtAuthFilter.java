package com.security.infra.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.domain.usuarios.Usuario;
import com.security.domain.usuarios.UsuarioRepository;
import com.security.infra.security.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //obtener el header que contiene jwt
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
            

        //obtener jwt desde el header
        // String jwt = authHeader.replace("Bearer ", "");
        String jwt = authHeader.split(" ")[1];

        //obtener subject/username desde el jwt
        String username = jwtService.extractUsername(jwt);

        //Settear un objeto auth dentro del SecurityContext
        Usuario usuario = usuarioRepository.findByUsername(username).get();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            username, null, usuario.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);


        //Ejecutar el resto de filtros
        filterChain.doFilter(request, response);
    }
    
}
