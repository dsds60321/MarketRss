package com.gen.marketrss.domain.kakao.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@NoArgsConstructor
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class List {
        private static final String object_type = "list";
        private String header_title;
        private HeaderLink headerLink;
    }
}
