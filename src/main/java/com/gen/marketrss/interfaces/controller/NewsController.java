package com.gen.marketrss.interfaces.controller;

import com.gen.marketrss.interfaces.dto.payload.UserPayload;
import com.gen.marketrss.interfaces.dto.response.news.NewsResponseDto;
import com.gen.marketrss.interfaces.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<? super NewsResponseDto> getNews(@AuthenticationPrincipal UserPayload user,@RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return newsService.newsPaging(user.getUserId(),page , size);
    }

}
