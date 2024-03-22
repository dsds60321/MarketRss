package com.gen.marketrss.interfaces.service.implement;

import com.gen.marketrss.domain.news.News;
import com.gen.marketrss.interfaces.dto.response.news.NewsResponseDto;
import com.gen.marketrss.interfaces.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.gen.marketrss.common.constant.Key.getNewsKey;

@Service
@RequiredArgsConstructor
public class NewsServiceImplement implements NewsService {

    private final RedisTemplate<String, News> newsRedisTemplate;

    @Override
    public ResponseEntity<? super NewsResponseDto> news(String userId) {
        News news = newsRedisTemplate.opsForValue().get(getNewsKey(userId));
        return NewsResponseDto.success(news);
    }
}
