package com.example.demo.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;



@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
        private final UserAuthProvider userAuthProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(AbstractHttpConfigurer::disable)
                    .addFilterBefore(new JWTAuthFilter(userAuthProvider), BasicAuthenticationFilter.class)
                    .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests((requests) ->
                            requests.requestMatchers(HttpMethod.POST, "/api/users/login", "/api/users/signup").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
                                    .requestMatchers(HttpMethod.PUT, "/api/cart/**").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/api/products/categories").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/api/products/search").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/api/feedback/add").permitAll()
                                    .anyRequest().authenticated()
                    );
            return http.build();
        }
}

