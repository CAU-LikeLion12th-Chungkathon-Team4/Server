package com.chungkathon.squirrel.jwt;

import com.chungkathon.squirrel.config.MemberAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.chungkathon.squirrel.jwt.JwtValidationType.VALID_JWT;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private static final String[] EXCLUDED_PATHS = {"/join", "/login", "/api/v1/check", "/join/check"};
    private static final String DYNAMIC_PATH_PATTERN = "^/dynamic/[a-zA-Z0-9\\-]+$";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // 특정 경로는 필터를 건너뛰기
        if (isExcludedPath(requestURI) || isDynamicPath(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = getJwtFromRequest(request);
            JwtValidationType jwtValidationType = jwtTokenProvider.validateToken(token);
            if (jwtValidationType == VALID_JWT) {
                String username = jwtTokenProvider.getUsernameFromAccessToken(token);
                MemberAuthentication authentication = MemberAuthentication.createMemberAuthentication(username);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.error("현재 상태: " + jwtValidationType.toString());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    private boolean isExcludedPath(String requestURI) {
        for (String path : EXCLUDED_PATHS) {
            if (path.equals(requestURI)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDynamicPath(String requestURI) {
        // 정규식으로 동적 경로 확인
        return requestURI.matches(DYNAMIC_PATH_PATTERN);
    }
}
