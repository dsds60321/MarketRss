package com.gen.marketrss.interfaces.service.implement;

import com.gen.marketrss.domain.news.News;
import com.gen.marketrss.infrastructure.common.util.RedisUtil;
import com.gen.marketrss.interfaces.dto.response.news.NewsResponseDto;
import com.gen.marketrss.interfaces.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.gen.marketrss.infrastructure.common.util.RedisUtil.getNewsKey;


@Service
@RequiredArgsConstructor
@Slf4j
public class NewsServiceImplement implements NewsService {

    private final RedisTemplate<String, News> newsRedisTemplate;
    private final RedisUtil redisUtil;

    @Override
    public ResponseEntity<? super NewsResponseDto> newsPaging(String userId, int page, int size) {
        Set<String> keys = redisUtil.findByKeysRedisPattern(newsRedisTemplate, getNewsKey(userId));
        List<News.NewsPayload> newsList = new ArrayList<>();

        for (String key : keys) {
            News news = redisUtil.get(key, News.class);
            if (news != null) {
                newsList.addAll(news.getNewsPayloads());
            }
        }

        if (newsList.isEmpty()) {
            return NewsResponseDto.validationFail();
        }

        return NewsResponseDto.success(getNewsPaging(newsList, page, size), newsList.size());
    }

    private List<News.NewsPayload> getNewsPaging(List<News.NewsPayload> news, int page, int size) {
        page--;
        int total = news.size();
        int start = page * size;
        int end = Math.min((start + size), total);

        if ( start > total) {
            return List.of();
        }

        return news.subList(start, end);
    }
}
