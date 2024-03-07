package com.gen.marketrss.infrastructure.api;

import com.gen.marketrss.infrastructure.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class MarketAuxApiService {

    private final WebClientUtil webClientUtil;

    @Value("${market-aux.uri.base-uri}")
    private String uri;

    @Value("${market-aux.uri.all}")
    private String all;

    @Value("${market-aux.token}")
    private String token;

    @Value("${etf.fngu}")
    private String fngu;

    public void getNewsLetters() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("symbols", "AAPL,AMZN,BABA,BIDU,META,GOOGL,MSFT,NFLX,NVDA,TSLA");
        params.add("symbols", fngu);
        params.add("industries", "Technology");
        params.add("filter_entities", "true");
        params.add("domains", "marketwatch.com,cnn.com,bloomberg.com,gurufocus.com,nasdaq.com,etftrends.com");
        params.add("api_token", token);
        String s = webClientUtil.WebGet(uri.concat(all)
                , params
                , null
                , String.class);
    }
}
