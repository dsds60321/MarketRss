package com.gen.marketrss.infrastructure.common.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-time}")
    private long accessTimeout;

    @Value("${jwt.refresh-time}")
    private long refreshTimeout;

    // 토큰에서 사용자 이름 추출
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 토큰에서 만료일 추출
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 토큰에서 정보 추출을 위한 범용 메소드
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰에서 정보 추출을 위한 범용 메소드
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateAccessToken(String userId) {
        return create(userId, Date.from(Instant.now().plus(accessTimeout, ChronoUnit.HOURS)));
    }

    public String generateRefreshToken(String userId) {
        return create(userId, Date.from(Instant.now().plus(refreshTimeout, ChronoUnit.DAYS)));
    }

    private String create(String userId, Date expiredDate) {
        return Jwts.builder()
                .signWith(getKey())
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .compact();
    }

    public String validate(String jwt) {
        try {
            Boolean isExpired = isTokenExpired(jwt);

            if (!isExpired) {
                return null;
            }

            return extractUsername(jwt);

        } catch (Exception e ) {
            e.printStackTrace();
            return null;
        }

    }


}
