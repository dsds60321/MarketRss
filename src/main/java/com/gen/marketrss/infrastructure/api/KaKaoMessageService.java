package com.gen.marketrss.infrastructure.api;

import com.gen.marketrss.domain.kakao.message.Message;
import com.gen.marketrss.infrastructure.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.gen.marketrss.infrastructure.common.constant.Key.KAKAO_TOKEN;

@Service
@RequiredArgsConstructor
public class KaKaoMessageService {

    @Value("${kakao.api.token}")
    private String authApi;

    @Value("${kakao.api.message.default-me}")
    private String meApi;

    @Value("${kakao.api.message.custom-me}")
    private String meCustomApi;

    @Value("${kakao.token.rest}")
    private String restKey;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private final WebClientUtil webClientUtil;

    private final StringRedisTemplate stringRedisTemplate;

    // kakao token 얻기 위한 메소드
    public void getAuthToken(){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", restKey);
        params.add("redirect_uri", redirectUri);
        params.add("response_type", "code");
        webClientUtil.sendFormPostWithParams(authApi, params, String.class);
    }

    public void sendCustomMessage() {
        String token = stringRedisTemplate.opsForValue().get(KAKAO_TOKEN);
    }
}
