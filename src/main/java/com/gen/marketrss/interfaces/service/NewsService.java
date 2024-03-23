package com.gen.marketrss.interfaces.service;

import com.gen.marketrss.interfaces.dto.response.news.NewsResponseDto;
import org.springframework.http.ResponseEntity;

public interface NewsService {
    ResponseEntity<? super NewsResponseDto> newsPaging(String userId,int page, int size);
}
