package com.gen.marketrss.interfaces.service;

import com.gen.marketrss.interfaces.dto.response.ResponseDto;
import com.gen.marketrss.interfaces.dto.response.news.NewsResponseDto;
import org.springframework.http.ResponseEntity;

public interface NewsService {
    ResponseEntity<? super NewsResponseDto> news(String userId);
}
