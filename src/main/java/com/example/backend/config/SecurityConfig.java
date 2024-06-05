package com.example.backend.config;

import com.example.backend.service.JwtTokenProvider;
import com.example.backend.service.SurveyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SurveyServiceImpl surveyService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // 禁用 CSRF 防护
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/responses/**").hasAnyRole("GUEST", "SURVEY_RESPONDENT","DEVELOPER")  // 填写者可以是非问卷发布者或者游客
                        .requestMatchers("/api/message").permitAll() // 基本验证
                        .requestMatchers("/api/surveys/create").hasAnyRole("SURVEY_CREATOR", "SURVEY_RESPONDENT","DEVELOPER")  // 问卷创建需要登录用户才可以访问
                        .requestMatchers("/api/surveys/**").hasAnyRole("SURVEY_CREATOR", "DEVELOPER")  // 仅问卷发布者角色可以访问
                        .requestMatchers("/api/users/**").permitAll()  // 注册和登录不需要身份验证
                        .requestMatchers("/api/**").hasRole("DEVELOPER") // 开发者角色可以访问所有接口
                        .anyRequest().authenticated())  // 其他请求需要身份验证,一般会被 Spring Security 自动拦截并返回一个错误响应
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
        return new DynamicRoleFilter(jwtTokenProvider(),surveyService);
    }

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
    }

}
