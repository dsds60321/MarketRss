package com.gen.marketrss.application;

import com.gen.marketrss.infrastructure.api.EmailMessageService;
import com.gen.marketrss.infrastructure.api.KaKaoMessageService;
import com.gen.marketrss.infrastructure.api.MarketAuxApiService;
import com.gen.marketrss.infrastructure.service.InitialService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final MarketAuxApiService marketAuxApiService;
    private final EmailMessageService emailMessageService;
    private final InitialService initialService;

    @Value("${market-aux.payload-ttl}")
    private int newsPayloadTtl;

    // 매일 오후 10시 실행
    @Scheduled(cron = "0 0 22 * * *", zone = "Asia/Seoul")
    public void fetchNewsPayloads() {
        marketAuxApiService.cacheNewsLettersByUser();
    }

    // 매일 오후 10시 30분 실행
    @Scheduled(cron = "0 30 22 * * *", zone = "Asia/Seoul")
    public void fetchNewsEmail() {
        emailMessageService.sendEmailMessage();
    }

    // 00시 삭제
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void removeImages() {
        initialService.removeImage(newsPayloadTtl);
    }

}
