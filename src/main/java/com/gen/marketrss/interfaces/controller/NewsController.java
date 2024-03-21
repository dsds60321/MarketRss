package com.gen.marketrss.interfaces.controller;

import com.gen.marketrss.domain.entity.UsersEntity;
import com.gen.marketrss.interfaces.dto.payload.UserPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
@Slf4j
public class NewsController {

    @GetMapping
    public String getNews(@AuthenticationPrincipal UserPayload user) {
        log.info("stg : {} " , user.getUserId());
        return "test";
    }
}
