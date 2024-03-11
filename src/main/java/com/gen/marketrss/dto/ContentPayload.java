package com.gen.marketrss.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class ContentPayload implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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
