package com.gen.marketrss.infrastructure.util;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
@Slf4j
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
        MultiValueMap<String, String> formData = convert(new ObjectMapper(), body);

        return WebClient.builder().baseUrl(uri)
                .build().post()
                .contentType(APPLICATION_FORM_URLENCODED)
                .headers(httpHeaders -> httpHeaders.setBearerAuth("iMmpviksJ3AYHLFhU-1LijgubhDsVfqVR4UKPXWaAAABjjPoFVNONYg--5I0Sw"))
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(formData))
                .acceptCharset(UTF_8)
                .exchangeToMono(response -> response.bodyToMono(returnClass)).block();
    }


    public <T> T sendFormPostRequest(String uri, Object body, Class<T> returnClass) {
        return WebClient.builder().baseUrl(uri)
                .build().post()
                .contentType(APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .acceptCharset(UTF_8)
                .exchangeToMono(response -> response.bodyToMono(returnClass)).block();
    }

    public static MultiValueMap<String, String> convert(ObjectMapper objectMapper, Object dto) {
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            Map<String, Object> map = objectMapper.convertValue(dto, new TypeReference<Map<String, Object>>() {});

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof List) {
                    List<?> list = (List<?>) entry.getValue();
                    for (Object obj : list) {
                        params.add(entry.getKey(), obj.toString());
                    }
                } else if (entry.getValue() instanceof Map) {
                    // 복잡한 객체를 JSON 문자열로 변환하여 처리
                    String json = objectMapper.writeValueAsString(entry.getValue());
                    params.add(entry.getKey(), json);
                } else {
                    params.add(entry.getKey(), entry.getValue().toString());
                }
            }

            return params;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Url Parameter 변환 중 오류가 발생했습니다.");
        }
    }
}