package com.gen.marketrss.infrastructure.api;

import com.gen.marketrss.domain.kakao.message.Message;
import com.gen.marketrss.domain.news.News;
import com.gen.marketrss.infrastructure.common.util.RedisUtil;
import com.gen.marketrss.infrastructure.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static com.gen.marketrss.common.constant.Key.*;

@Service
@RequiredArgsConstructor
public class KaKaoMessageService {

    @Value("${kakao.api.auth}")
    private String authApi;

    @Value("${kakao.api.token}")
    private String oauthTokenApi;

    @Value("${kakao.api.message.default-me}")
    private String meApi;

    @Value("${kakao.api.message.custom-me}")
    private String meCustomApi;

    @Value("${kakao.token.rest}")
    private String restKey;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private final WebClientUtil webClientUtil;

    private final RedisUtil redisUtil;

    private final StringRedisTemplate stringRedisTemplate;

    // kakao token 얻기 위한 메소드
    public void getAuthCode(){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", restKey);
        params.add("redirect_uri", redirectUri);
        params.add("response_type", "code");
        webClientUtil.sendFormPostWithParams(authApi, params, String.class);
    }

    public void getOauthToken(String code) {
        Map<String, String> body = Map.of("grant_type", "authorization_code",
                "client_id", restKey,
                "redirect_uri", redirectUri,
                "code", code);
        String s = webClientUtil.sendFormPostRequest(oauthTokenApi, body, String.class);
        System.out.println(s);
    }

    public void sendCustomMessage() {
        String accToken = stringRedisTemplate.opsForValue().get(KAKAO_ACC_TOKEN);
        webClientUtil.sendFormPostWithBearer(meApi, accToken, getMessageByList(), String.class);
    }

    public Map<String, Message.OfList> getMessageByList() {
        News news = redisUtil.get(NEWS_KEY + "2024-03-12", News.class);
        List<Message.OfList.Content> contents = new ArrayList<>();
        List<News.NewsPayload> newsPayloadList = news.getNewsPayloads();

        newsPayloadList
                .forEach(data -> {
                    Message.OfList.Content content = Message.OfList.Content
                            .builder()
                            .title(data.getTitle())
                            .description(data.getDescription())
                            .imageUrl(data.getImage_url())
                            .imageWidth("640")
                            .imageHeight("640")
                            .build();

                    Message.Link contentLink = Message.Link.builder()
                            .webUrl(data.getUrl())
                            .mobileWebUrl(data.getUrl())
                            .androidExecutionParams("")
                            .iosExecutionParams("")
                            .build();

                    content = content.toBuilder()
                                    .link(contentLink)
                                            .build();

                    contents.add(content);
                });

        Message.Link messageLink = Message.Link.builder()
                .webUrl("")
                .mobileWebUrl("")
                .androidExecutionParams("")
                .iosExecutionParams("")
                .build();

        Message.OfList.OfListBuilder messageList = Message.OfList
                .builder()
                .objectType("list")
                .headerTitle("Fngu News Summary")
                .headerLink(messageLink);


        Message.OfList list = messageList.contents(contents).build();


        return Map.of(KAKAO_MSG_OBJ_KEY, list);
    }
}
