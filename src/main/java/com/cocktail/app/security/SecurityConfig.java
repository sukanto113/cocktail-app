package com.cocktail.app.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import javax.crypto.SecretKey;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecretKey secretKey;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auths) -> auths
                        .requestMatchers("/user/register").permitAll()
                        .requestMatchers("/user/login").permitAll()
                        .requestMatchers("/categories/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/file/profile-picture").authenticated()
                        .requestMatchers(HttpMethod.GET, "/file/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder())));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

}