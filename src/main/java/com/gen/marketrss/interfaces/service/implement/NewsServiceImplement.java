package com.gen.marketrss.interfaces.service.implement;

import com.gen.marketrss.domain.news.News;
import com.gen.marketrss.interfaces.dto.response.news.NewsResponseDto;
import com.gen.marketrss.interfaces.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gen.marketrss.common.constant.Key.getNewsKey;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsServiceImplement implements NewsService {

    private final RedisTemplate<String, News> newsRedisTemplate;

    @Override
    public ResponseEntity<? super NewsResponseDto> newsPaging(String userId, int page, int size) {
        News news = newsRedisTemplate.opsForValue().get(getNewsKey(userId));

        if (news == null) {
            return NewsResponseDto.validationFail();
        }

        return NewsResponseDto.success(getNewsPaging(news, page, size), news.getNewsPayloads().size());
    }

    private List<News.NewsPayload> getNewsPaging(News news, int page, int size) {
        page--;
        int total = news.getNewsPayloads().size();
        int start = page * size;
        int end = Math.min((start + size), total);

        if ( start > total) {
            return List.of();
        }

        return news.getNewsPayloads().subList(start, end);
    }
}
