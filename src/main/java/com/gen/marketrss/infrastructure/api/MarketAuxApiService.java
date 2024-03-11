package com.gen.marketrss.infrastructure.api;

import com.gen.marketrss.dto.NewsPayloads;
import com.gen.marketrss.infrastructure.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.gen.marketrss.infrastructure.common.constant.Key.NEWS_KEY;

@Service
@RequiredArgsConstructor
public class MarketAuxApiService {

    private final WebClientUtil webClientUtil;
    private final RedisTemplate<String, NewsPayloads> redisTemplate;

    @Value("${market-aux.uri.base-uri}")
    private String uri;

    @Value("${market-aux.uri.all}")
    private String all;

    @Value("${market-aux.token}")
    private String token;

    @Value("${market-aux.domains}")
    private String domains;

    @Value("${etf.fngu}")
    private String fngu;

    public void getNewsLetters() {
        LocalDate currentDate = LocalDate.now();
        String key = NEWS_KEY + currentDate;

        NewsPayloads payloads = getNewsPayloadsFromCache(key);

        if (payloads == null) {
            NewsPayloads newsPayloads = fetchNewsPayloadsFromApi();
            cacheNewsPayloads(key, newsPayloads);
        }
    }

    private NewsPayloads getNewsPayloadsFromCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    private NewsPayloads fetchNewsPayloadsFromApi() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("symbols", fngu);
        params.add("industries", "Technology");
        params.add("filter_entities", "true");
        params.add("domains", domains);
        params.add("api_token", token);
        return webClientUtil.get(uri.concat(all)
                , params
                , new HashMap<>()
                , NewsPayloads.class);
    }

    private void cacheNewsPayloads(String key , NewsPayloads newsPayloads) {
        redisTemplate.opsForValue().set(key, newsPayloads , 1 , TimeUnit.DAYS);
    }
}
