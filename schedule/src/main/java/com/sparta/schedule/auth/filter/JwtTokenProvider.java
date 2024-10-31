package com.sparta.schedule.auth;


import com.sparta.schedule.entity.User;
import com.sparta.schedule.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final UserRepository userRepository;
//토큰과 관련된 기능 틀래스

    //토크을 가지오 오는 키
    @Value("${secret.key}")
    private String secretKey;
    //유효시간
    private final long validityInMilliseconds = 3600000; // 1시간

    //토큰생성
    public String createToken(User user) {
        Claims claims = Jwts.claims()
                .setSubject(user.getEmail());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    //토큰에서 이메일 추출
    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //토큰검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //요청에서 멤버 추출- Bearer 토큰 JWT를 추출 데이터베이스에서 멤버를 찾아 그 ID를 반환
    public User getLoginedUser() {
        HttpServletRequest request = getCurrentHttpRequest();  // 현재 요청 객체 가져오기
        if (request != null) {
            String bearerToken = request.getHeader("Authorization");
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                String token = extractJwtToken(bearerToken);
                String email = getEmailFromToken(token);  // 토큰에서 이메일 추출

                return userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("user not found"));
            }
        }
        return null;
    }

    //JWT 추출- Authorization 헤더에서 Bearer 접두사를 제거하고 실제 JWT만 반환
    public String extractJwtToken(String tokenWithBearer) {
        if (tokenWithBearer != null && tokenWithBearer.startsWith("Bearer ")) {
            return tokenWithBearer.substring(7);
        }

        return tokenWithBearer;
    }

    //현재 HTTP 요청 가져오기
    private HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }
}
