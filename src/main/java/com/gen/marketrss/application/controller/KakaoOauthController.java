package com.gen.marketrss.application.controller;

import com.gen.marketrss.infrastructure.api.KaKaoMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoOauthController {

    private final StringRedisTemplate stringRedisTemplate;
    private final KaKaoMessageService kaKaoMessageService;

    @GetMapping("/oauth")
    public Mono<ResponseEntity<Void>> test(@RequestParam(name = "code" , defaultValue = "code") String code){
        System.out.println(code);
        kaKaoMessageService.getOauthToken(code);
        return Mono.just(new ResponseEntity<Void>(HttpStatus.OK));
    }
}
