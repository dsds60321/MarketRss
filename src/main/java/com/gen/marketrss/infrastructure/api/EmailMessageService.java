package com.gen.marketrss.infrastructure.api;

import com.gen.marketrss.domain.entity.FeedHistoryEntity;
import com.gen.marketrss.domain.entity.StockEntity;
import com.gen.marketrss.domain.entity.UsersEntity;
import com.gen.marketrss.domain.news.News;
import com.gen.marketrss.infrastructure.common.provider.EmailProvider;
import com.gen.marketrss.infrastructure.common.util.RedisUtil;
import com.gen.marketrss.infrastructure.repository.FeedHistoriesRepository;
import com.gen.marketrss.infrastructure.repository.StockRepository;
import com.gen.marketrss.infrastructure.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.List;

import static com.gen.marketrss.infrastructure.common.util.RedisUtil.getCurrentDateNewsKey;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailMessageService {

    private final EmailProvider emailProvider;
    private final RedisUtil redisUtil;
    private final UsersRepository usersRepository;
    private final StockRepository stockRepository;
    private final FeedHistoriesRepository feedHistoriesRepository;

    @Transactional
    public void sendEmailMessage() {
        try {
            List<UsersEntity> usersEntity = usersRepository.findAll();
            for (UsersEntity user : usersEntity) {
                boolean isSendEmail = user.getSend_email().equals("Y");

                if (!isSendEmail) {
                    continue;
                }

                boolean isSend = false;
                int attempts = 0;
                int maxAttempts = 3;
                int waitTimeout = 3000;

                FeedHistoryEntity feedHistoryEntity = null;
                StockEntity stockEntity = stockRepository.findByUserId(user.getUserId());
                String email = user.getEmail();
                News news = redisUtil.get(getCurrentDateNewsKey(user.getUserId()), News.class);

                while (!isSend && attempts < maxAttempts) {
                    isSend = emailProvider.sendNewsMail(email, news);
                    attempts++;
                    if (!isSend) {
                        log.error("email send fail : {} " , email);
                        // 실패시 3초 후 재시도
                        Thread.sleep(waitTimeout);;
                    }
                }

                if (isSend) {
                    feedHistoryEntity = FeedHistoryEntity.builder()
                            .sendEmail(email)
                            .sendCount(attempts)
                            .sendKakao("N")
                            .sendEmail("Y")
                            .stockEntity(stockEntity)
                            .regDate(LocalDate.now())
                            .build();
                } else {
                    feedHistoryEntity = FeedHistoryEntity.builder()
                            .sendEmail(email)
                            .sendCount(attempts)
                            .sendKakao("N")
                            .sendEmail("N")
                            .stockEntity(stockEntity)
                            .regDate(LocalDate.now())
                            .build();
                }

                stockEntity.addFeedHistory(feedHistoryEntity);
                feedHistoriesRepository.save(feedHistoryEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
