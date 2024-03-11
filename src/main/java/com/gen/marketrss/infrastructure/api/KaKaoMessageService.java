package com.gen.marketrss.infrastructure.api;

import com.gen.marketrss.infrastructure.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KaKaoMessageService {

    @Value("${kakao.message.api.default-me}")
    private String api;

    @Value("${kakao.token}")
    private String token;

    private final WebClientUtil webClientUtil;

    public void sendCustomMessage() {
        webClientUtil.post(api,token,"",String.class);
    }
}
