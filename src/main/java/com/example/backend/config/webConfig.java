//package com.example.backend.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class webConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)  // 使用新的配置方法禁用 CSRF
//                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
//                .httpBasic(Customizer.withDefaults());// 允许所有请求，不需要身份验证
////                        .anyRequest().permitAll()  // 允许所有请求，不需要身份验证
//
//
//        return http.build();
//    }
//}
