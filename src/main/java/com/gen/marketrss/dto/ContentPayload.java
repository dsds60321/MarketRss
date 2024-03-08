package com.gen.marketrss.dto;

import java.util.ArrayList;
import java.util.List;

public class ContentPayload {

    private Long id;

    private NewsPayloads news;

    private List<HighlightPayload> highlights = new ArrayList<>();

    private String symbol;
    private String name;
    private String exchange;
    private String exchange_long;
    private String country;
    private String type;
    private String industry;
    private double match_score;
    private double sentiment_score;
}
