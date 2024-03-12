package com.gen.marketrss.infrastructure.api;

import com.gen.marketrss.domain.kakao.message.Message;
import com.gen.marketrss.infrastructure.util.WebClientUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KaKaoMessageServiceTest {

    @Autowired
    KaKaoMessageService kaKaoMessageService;

    @Test
    void sendAuthToken() {
        kaKaoMessageService.getAuthToken();
    }

    @Test
    void sendCustomMessage() {
        kaKaoMessageService.sendCustomMessage();
    }

    @Test
    void make() {
    }
}