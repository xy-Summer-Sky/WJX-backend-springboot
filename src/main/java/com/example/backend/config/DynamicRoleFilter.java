package com.example.backend.config;

import com.example.backend.entity.UserWithRole;
import com.example.backend.service.JwtTokenProvider;
import com.example.backend.service.SurveyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DynamicRoleFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private SurveyServiceImpl surveyService;  // 服务类，用于查询问卷信息



    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 过滤器, 用于动态设置角色, 并将用户信息放入SecurityContextHolder, 供后续的请求使用
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);

        if (jwt != null && tokenProvider.validateToken(jwt)) {
            String username = tokenProvider.getUsernameFromJWT(jwt);
            UserDetails userDetails = surveyService.loadUserByUsername(username);

            String surveyId = request.getParameter("surveyId");  // 假设问卷ID作为请求参数传递
            if (surveyId != null) {
                boolean isCreator = surveyService.isSurveyCreator(username, surveyId);
                if (isCreator) {
                    userDetails = new UserWithRole(userDetails, "SURVEY_CREATOR");
                } else {
                    userDetails = new UserWithRole(userDetails, "SURVEY_RESPONDENT");
                }
            }

            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}

