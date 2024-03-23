package com.gen.marketrss.application;

import com.gen.marketrss.infrastructure.api.EmailMessageService;
import com.gen.marketrss.infrastructure.api.MarketAuxApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final MarketAuxApiService marketAuxApiService;
    private final EmailMessageService emailMessageService;

    // 매일 오후 10시 실행
    @Scheduled(cron = "0 0 22 * * *", zone = "Asia/Seoul")
    public void fetchNewsPayloads() {
        marketAuxApiService.cacheNewsLettersByUser();
    }

    // 매일 오후 10시 실행
    @Scheduled(cron = "0 58 2 * * *", zone = "Asia/Seoul")
    public void fetchNewsEmail() {
        emailMessageService.sendEmailMessage();
    }
}
