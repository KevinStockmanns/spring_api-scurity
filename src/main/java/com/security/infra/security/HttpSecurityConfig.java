package com.security.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security.infra.security.filter.JwtAuthFilter;
import com.security.utils.enums.Permisos;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
public class HttpSecurityConfig {
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http
    ) throws Exception{
        http.csrf(csrf-> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(builderRequestConfig());
        return http.build();
    }

    private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> builderRequestConfig() {
        return auth -> {
            auth.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/auth/public").permitAll();
            auth.requestMatchers("/error").permitAll();

            auth.requestMatchers(HttpMethod.GET, "/productos").hasAuthority(Permisos.READ_ALL_PRODUCTS.name());
            auth.requestMatchers(HttpMethod.POST, "/producto").hasAuthority(Permisos.SAVE_ONE_PRODUCT.name());

            auth.anyRequest().denyAll();

        };
    }
}
