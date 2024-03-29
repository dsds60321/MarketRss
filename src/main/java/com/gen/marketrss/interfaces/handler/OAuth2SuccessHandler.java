package com.gen.marketrss.interfaces.handler;

import com.gen.marketrss.domain.entity.CustomOAuth2User;
import com.gen.marketrss.domain.entity.UsersEntity;
import com.gen.marketrss.infrastructure.common.provider.JwtProvider;
import com.gen.marketrss.infrastructure.common.util.RedisUtil;
import com.gen.marketrss.infrastructure.repository.UsersRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisUtil redisUtil;
    private final UsersRepository usersRepository;

    @Value("${jwt.access-time}")
    private long accessTimeout;

    @Value("${jwt.refresh-time}")
    private long refreshTimeout;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String userId = oAuth2User.getName();
        UsersEntity usersEntity = usersRepository.findByUserId(userId);

        String accessToken = jwtProvider.generateAccessToken(userId);
        String refreshToken = jwtProvider.generateRefreshToken(userId);

        stringRedisTemplate.opsForValue().set(jwtProvider.getRefreshRedisKey(userId), refreshToken, refreshTimeout , TimeUnit.SECONDS);

        redisUtil.set(userId, usersEntity.toPayload(), Duration.ofSeconds(refreshTimeout));

        response.sendRedirect("/auth/oauth-response?accessToken=" + accessToken +"&refreshToken=" + refreshToken);
    }
}
