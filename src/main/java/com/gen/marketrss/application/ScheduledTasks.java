package com.gen.marketrss.application;

import com.gen.marketrss.infrastructure.api.MarketAuxApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final MarketAuxApiService marketAuxApiService;

    // 매일 오후 11시 실행
    @Scheduled(cron = "0 0 23 * * *", zone = "Asia/Seoul")
    public void fetchNewsPayloads() {
        marketAuxApiService.getNewsLetters();
    }
}
