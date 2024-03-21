package com.gen.marketrss.interfaces.service;

import com.gen.marketrss.domain.news.News;
import org.springframework.http.ResponseEntity;

public interface NewsService {
    ResponseEntity<? super News.NewsPayload> news();
}
