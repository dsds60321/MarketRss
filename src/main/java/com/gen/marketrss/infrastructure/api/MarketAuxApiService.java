package com.gen.marketrss.infrastructure.api;

import com.gen.marketrss.domain.entity.StockEntity;
import com.gen.marketrss.domain.news.News;
import com.gen.marketrss.infrastructure.repository.StockRepository;
import com.gen.marketrss.infrastructure.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.gen.marketrss.common.constant.Key.getNewsKey;

@Service
@Slf4j
@RequiredArgsConstructor
public class MarketAuxApiService {

    private final WebClientUtil webClientUtil;
    private final RedisTemplate<String, News> redisTemplate;
    private final StockRepository stockRepository;

    @Value("${market-aux.uri.base-uri}")
    private String uri;

    @Value("${market-aux.uri.all}")
    private String all;

    @Value("${market-aux.token}")
    private String token;

    @Value("${market-aux.domains}")
    private String domains;

    @Value("${market-aux.payload-ttl}")
    private long payloadTtl;

    public void cacheNewsLettersByUser() {

        try {
            List<StockEntity> stockEntities = stockRepository.findAll();

            stockEntities.forEach(stockEntity -> {
                String key = getNewsKey(stockEntity.getUserId());
                News news = getCurrentNewsPayloadsFromCache(key).orElseGet(() -> {
                    News newsPayload = fetchNewsPayloadsFromApi(stockEntity.getStock());
                    cacheNewsPayloads(key, newsPayload);
                    return newsPayload;
                });
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Optional<News> getCurrentNewsPayloadsFromCache(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    public News fetchNewsPayloadsFromApi(String stock) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("symbols", stock);
        params.add("industries", "Technology");
        params.add("filter_entities", "true");
        params.add("domains", domains);
        params.add("api_token", token);
        return webClientUtil.sendGetRequest(uri.concat(all)
                , params
                , new HashMap<>()
                , News.class);
    }

    // 7일간 보관
    private void cacheNewsPayloads(String key , News newsPayloads) {
        redisTemplate.opsForValue().set(key, newsPayloads, payloadTtl , TimeUnit.DAYS);
    }
}
