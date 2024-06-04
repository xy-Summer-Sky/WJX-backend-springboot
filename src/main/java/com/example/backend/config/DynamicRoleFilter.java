package com.example.backend.config;

import com.example.backend.entity.UserWithRole;
import com.example.backend.service.JwtTokenProvider;
import com.example.backend.service.SurveyServiceImpl;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import java.util.Collection;
import java.util.List;

@Component
public class DynamicRoleFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;


    private final SurveyServiceImpl surveyService;

    public DynamicRoleFilter(JwtTokenProvider tokenProvider, SurveyServiceImpl surveyService) {
        this.tokenProvider = tokenProvider;
        this.surveyService = surveyService;
    }

    static final String GUEST_TOKEN = "1234567890";
    static final String DEVELOPER_TOKEN = "123456";
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

        if (jwt == null) {
            System.out.println("JWT is null");
        }
        if(jwt != null && jwt.equals(GUEST_TOKEN)){
            // 对于游客用户, 其具有特殊的token
            UserDetails guestUser = new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return List.of(new SimpleGrantedAuthority("GUEST"));
                }

                @Override
                public String getPassword() {
                    return ""; // 游客用户没有密码
                }

                @Override
                public String getUsername() {
                    return "guest"; // 游客用户的用户名
                }

                @Override
                public boolean isAccountNonExpired() {
                    return true;
                }

                @Override
                public boolean isAccountNonLocked() {
                    return true;
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return true;
                }

                @Override
                public boolean isEnabled() {
                    return true;
                }
            };

            UserWithRole userWithRole = new UserWithRole(guestUser, "GUEST");
            System.out.println(userWithRole.getAuthorities());
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userWithRole, null, userWithRole.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        else if(jwt != null && jwt.equals(DEVELOPER_TOKEN)){
            // 开发者
            UserDetails guestUser = new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return List.of(new SimpleGrantedAuthority("DEVELOPER"));
                }

                @Override
                public String getPassword() {
                    return ""; // 游客用户没有密码
                }

                @Override
                public String getUsername() {
                    return "developer"; // 游客用户的用户名
                }

                @Override
                public boolean isAccountNonExpired() {
                    return true;
                }

                @Override
                public boolean isAccountNonLocked() {
                    return true;
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return true;
                }

                @Override
                public boolean isEnabled() {
                    return true;
                }
            };

            UserWithRole userWithRole = new UserWithRole(guestUser, "DEVELOPER");
            System.out.println(userWithRole.getAuthorities());
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userWithRole, null, userWithRole.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        // 验证JWT, 并设置角色
        else if (jwt != null && tokenProvider.validateToken(jwt)) {
            System.out.println(jwt);
            String username = tokenProvider.getUsernameFromJWT(jwt);
            System.out.println("username: " + username);
            UserDetails userDetails = surveyService.loadUserByUsername(username);

            String surveyId = request.getHeader("surveyId");  // 假设问卷ID作为请求参数传递
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

            if (userDetails != null) {
                System.out.println(userDetails.getAuthorities());
            }else {
                System.out.println("userDetails is null");
            }
        }

        filterChain.doFilter(request, response);
    }
}

