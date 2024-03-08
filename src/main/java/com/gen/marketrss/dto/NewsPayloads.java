package com.gen.marketrss.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
public class NewsPayloads {

    @Getter
    @ToString
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class NewsPayload {

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
        private List<ContentPayload> contentPayloads;

        public NewsPayload(String uuid, String title, String description, String keywords, String snippet, String url, String image_url, String language, String published_at, String source, String relevance_score, List<ContentPayload> contentPayloads) {
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
            this.contentPayloads = contentPayloads;
        }
    }

}
