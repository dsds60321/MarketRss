package com.gen.marketrss.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gen.marketrss.domain.entity.UsersEntity;
import com.gen.marketrss.domain.kakao.message.Message;
import com.gen.marketrss.domain.news.News;
import com.gen.marketrss.infrastructure.common.util.RedisUtil;
import com.gen.marketrss.infrastructure.repository.UsersRepository;
import com.gen.marketrss.infrastructure.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gen.marketrss.common.constant.Key.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class KaKaoMessageService {

    @Value("${kakao.api.message.default-me}")
    private String meApi;

    @Value("${kakao.api.message.custom-me}")
    private String meCustomApi;

    @Value("${kakao.token.clientId}")
    private String clientId;


    @Value("${kakao.token.clientSecret}")
    private String clientSecret;

    @Value("${host}")
    private String host;

    private final WebClientUtil webClientUtil;

    private final RedisUtil redisUtil;

    private final StringRedisTemplate stringRedisTemplate;
    private final UsersRepository usersRepository;



    public boolean sendFeed(String userId) {
        UsersEntity userEntity = usersRepository.findByUserId(userId);

        if (userEntity == null ) {
            return false;
        }

        News news = redisUtil.get(NEWS_KEY + userEntity.getUserId() + "_" + LocalDate.now(), News.class);

        if (news == null) {
            return false;
        }

        List<Message.OfList.Content> contents = new ArrayList<>();
        List<News.NewsPayload> newsPayloadList = news.getNewsPayloads();
        String link = "";
        newsPayloadList
                .forEach(data -> {
                    Message.Link contentLink = Message.Link.builder()
                            .webUrl(data.getUrl())
                            .mobileWebUrl(data.getUrl())
                            .androidExecutionParams("")
                            .iosExecutionParams("")
                            .build();

                    Message.OfList.Content content = Message.OfList.Content
                            .builder()
                            .title(data.getTitle())
                            .description(data.getDescription())
                            .imageUrl(data.getImage_url())
                            .imageWidth("640")
                            .imageHeight("640")
                            .link(contentLink)
                            .build();

                    contents.add(content);
                });

        Message.Link messageLink = Message.Link.builder()
                .webUrl(host)
                .mobileWebUrl(host)
                .androidExecutionParams("")
                .iosExecutionParams("")
                .build();

        Message.Link buttonLink = Message.Link.builder()
                .webUrl(host)
                .build();

        Message.Button button = Message.Button.builder()
                .title("웹으로 이동")
                .link(buttonLink).build();

        Message.OfList.OfListBuilder messageList = Message.OfList
                .builder()
                .objectType("list")
                .headerTitle("금일 뉴스 피드")
                .headerLink(messageLink)
                .buttons(List.of(button));


        Message.OfList list = messageList.contents(contents).build();
        Map<String, Message.OfList> template = Map.of(KAKAO_MSG_OBJ_KEY, list);

        try {
            log.info("news List : {} " , new ObjectMapper().writeValueAsString(template));
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap result = webClientUtil.sendFormPostWithBearer(meApi, userEntity.getKakao_token(), template, HashMap.class);


        try {
            log.info("result : {} " , new ObjectMapper().writeValueAsString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.get("result_code").equals("0");
    }
}
