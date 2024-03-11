package com.gen.marketrss.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class NewsPayloads implements Serializable {

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
        private  String language;
        private  String published_at;
        private  String source;
        private  String relevance_score;

        @JsonProperty("entities")
        private List<ContentPayload> entities;

        public NewsPayload(String uuid, String title, String description, String keywords, String snippet, String url, String image_url, String language, String published_at, String source, String relevance_score, List<ContentPayload> entities) {
            this.uuid = uuid;
            this.title = title;
            this.description = description;
            this.keywords = keywords;
            this.snippet = snippet;
            this.url = url;
            this.image_url = image_url;
            this.language = language;
            this.published_at = published_at;
            this.source = source;
            this.relevance_score = relevance_score;
            this.entities = entities;
        }
    }
}
