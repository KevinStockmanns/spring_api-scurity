package com.security.infra.security;

import java.security.Key;
import java.sql.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.security.domain.usuarios.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    // @Value("${security.jwt.secret-key}")
    // private String SECRET_KEY;

    public String generateToken(Usuario usuario, Map<String, Object> extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiracion = new Date(issuedAt.getTime() + (30 * 1000 * 60));

        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(usuario.getUsername())
            .setIssuedAt(issuedAt)
            .setExpiration(expiracion)
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .signWith(generateClave(), SignatureAlgorithm.HS256)
            .compact();
    }

    private Key generateClave() {
        return Keys.hmacShaKeyFor("una-clave-larga-y-segura-de-al-menos-32-caracteres".getBytes());
    }

    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateClave()).build()
            .parseClaimsJws(jwt).getBody();
    }
    
}
