package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)  // 禁用 CSRF 防护
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.DELETE).permitAll().requestMatchers(HttpMethod.PUT).permitAll().requestMatchers(HttpMethod.POST).permitAll());  // 允许所有请求，不需要身份验证
//                 // 启用 HTTP 基础认证
//
//        return http.build();
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String encodePassword(String password) {
        return passwordEncoder().encode(password);
    }
}
