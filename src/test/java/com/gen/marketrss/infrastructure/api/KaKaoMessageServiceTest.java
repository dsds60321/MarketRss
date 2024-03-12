package com.gen.marketrss.infrastructure.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KaKaoMessageServiceTest {

    @Autowired
    KaKaoMessageService kaKaoMessageService;

    @Test
    void sendAuthToken() {
        kaKaoMessageService.getAuthCode();
    }

    @Test
    void getMessageByList() {
        kaKaoMessageService.getMessageByList();
    }

    @Test
    void sendCustomMessage() {
        kaKaoMessageService.sendCustomMessage();
    }
}