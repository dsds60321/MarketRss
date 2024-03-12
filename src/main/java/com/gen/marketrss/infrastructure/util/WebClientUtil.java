package com.gen.marketrss.infrastructure.util;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

    public <T> T sendGetRequest(String uri, MultiValueMap<String,String> params , Map<String,String> headers , Class<T> returnClass) {
        WebClient.RequestHeadersSpec<?> request = WebClient.builder().baseUrl(uri)
                .build().get()
                .uri(uriBuilder -> uriBuilder.queryParams(params).build())
                .headers(httpHeaders -> httpHeaders.setAll(headers))
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8);

        return request.exchangeToMono(response -> response.bodyToMono(returnClass)).block();
    }

    public <T> T sendFormPostWithParams(String uri, MultiValueMap<String,String> params, Class<T> returnClass) {
        return WebClient.builder().baseUrl(uri)
                .build().post()
                .uri(uriBuilder -> uriBuilder.queryParams(params).build())
                .contentType(APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(UTF_8)
                .exchangeToMono(response -> response.bodyToMono(returnClass)).block();
    }

    public <T> T sendFormPostWithBearer(String uri, String bearer, Object body, Class<T> returnClass) {
        return WebClient.builder().baseUrl(uri)
                .build().post()
                .contentType(APPLICATION_FORM_URLENCODED)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(bearer))
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .acceptCharset(UTF_8)
                .exchangeToMono(response -> response.bodyToMono(returnClass)).block();
    }


    public <T> T sendFormPostRequest(String uri, String bearer, Object body, Class<T> returnClass) {
        return WebClient.builder().baseUrl(uri)
                .build().post()
                .contentType(APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .acceptCharset(UTF_8)
                .exchangeToMono(response -> response.bodyToMono(returnClass)).block();
    }
}