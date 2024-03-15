package com.gen.marketrss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableScheduling
public class MarketRssApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketRssApplication.class, args);
    }

}
