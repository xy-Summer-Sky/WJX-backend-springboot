package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // 禁用 CSRF 防护
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/**").permitAll()  // 公共访问，不需要身份验证
                        .requestMatchers("/survey/create/**").hasRole("SURVEY_CREATOR")  // 仅问卷发布者角色可以访问
                        .requestMatchers("/survey/respond/**").hasRole("SURVEY_RESPONDENT")  // 仅问卷填写者角色可以访问
                        .anyRequest().authenticated())  // 其他请求需要身份验证
                .addFilterBefore(dynamicRoleFilter(), UsernamePasswordAuthenticationFilter.class);  // 添加动态角色检查过滤器

        return http.build();
    }

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String encodePassword(String password) {
        return passwordEncoder().encode(password);
    }
    @Bean
    public DynamicRoleFilter dynamicRoleFilter() {
        return new DynamicRoleFilter();
    }
}
