package com.gen.marketrss.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class KakaoOauthController {

    @GetMapping("/oauth")
    public Mono<ResponseEntity<Void>> test(@RequestParam(name = "code" , defaultValue = "code") String code){
        return Mono.just(new ResponseEntity<Void>(HttpStatus.OK));
    }
}
