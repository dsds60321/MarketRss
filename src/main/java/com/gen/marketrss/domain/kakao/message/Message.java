package com.gen.marketrss.domain.kakao.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class OfList {
        private final String objectType = "list";
        private String headerTitle;
        private Link headerLink;
        @JsonProperty("contents")
        private List<Contents> contents;

        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @Builder
        @AllArgsConstructor
        public static class Contents {
            private String title;
            private String description;
            private String imageUrl;
            private String imageWidth;
            private String imageHeight;
            private Link link;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @AllArgsConstructor
    public static class Link {
        private String webUrl;
        private String mobileWebUrl;
        private String androidExecutionParams;
        private String iosExecutionParams;
    }
}
