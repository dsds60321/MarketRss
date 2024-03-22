package com.gen.marketrss.interfaces.dto.response.news;

import com.gen.marketrss.domain.news.News;
import com.gen.marketrss.interfaces.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class NewsResponseDto extends ResponseDto {

    private final News news;

    public NewsResponseDto(News news) {
        super();
        this.news = news;
    }

    public static ResponseEntity<? super NewsResponseDto> success(News news) {
        NewsResponseDto responseBody = new NewsResponseDto(news);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
