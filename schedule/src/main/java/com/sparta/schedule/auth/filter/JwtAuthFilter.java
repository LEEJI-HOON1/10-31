package com.sparta.schedule.auth.filter;


import com.sparta.schedule.auth.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/api")) {

            String token = jwtTokenProvider.extractJwtToken(request.getHeader("Authorization"));

            if (jwtTokenProvider.validateToken(token)) {
                filterChain.doFilter(request, response);
            } else {
                extracted(response);
            }
        }

        if (requestURI.contains("/auth")) {
            filterChain.doFilter(request, response);
        }
    }

    private void extracted(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 설정
        response.setContentType("application/json"); // 응답 타입을 JSON으로 설정
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = "{\"error\": \"토큰이 만료되었습니다.\"}";
        response.getWriter().write(jsonResponse); // 응답 본문에 JSON 메시지 작성
    }

}
