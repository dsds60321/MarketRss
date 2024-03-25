package com.gen.marketrss.domain.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class News implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("data")
    private List<NewsPayload> newsPayloads;

    @Getter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class NewsPayload implements Serializable{

        @Serial
        private static final long serialVersionUID = 1L;

        private  String uuid;
        private  String title;
        private  String description;
        private  String keywords;
        private  String snippet;
        private  String url;
        private  String image_url;
        @Setter
        private  String custom_image_url;
        private  String language;
        private  String published_at;
        private  String source;
        private  String relevance_score;
        @JsonProperty("entities")
        private List<ContentPayload> entities;

    }

    @NoArgsConstructor
    @Getter
    public static class ContentPayload implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        @JsonProperty("highlights")
        private List<HighlightPayload> highlightPayloads = new ArrayList<>();
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


    @NoArgsConstructor
    @Getter
    public static class HighlightPayload implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private String highlight;
        private double sentiment;
        private String highlighted_in;

    }
}
