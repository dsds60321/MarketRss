package com.gen.marketrss.infrastructure.util;


import com.gen.marketrss.infrastructure.common.config.WebClientConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

    private final WebClientConfig webClient;

    public <T> T WebGet(String uri, MultiValueMap<String,String> params , Map<String,String> headers , Class<T> returnClass) {
        WebClient.RequestHeadersSpec<?> request = webClient.build()
                .get()
                .uri(uriBuilder -> uriBuilder.path(uri).queryParams(params).build())
                .headers(httpHeaders -> httpHeaders.setAll(headers))
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8);

        return request.exchangeToMono(response -> response.bodyToMono(returnClass)).block();
    }
}
