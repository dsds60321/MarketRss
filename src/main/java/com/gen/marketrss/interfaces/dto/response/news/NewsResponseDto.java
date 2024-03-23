package com.gen.marketrss.interfaces.dto.response.news;

import com.gen.marketrss.domain.news.News;
import com.gen.marketrss.interfaces.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class NewsResponseDto extends ResponseDto {

    private final List<News.NewsPayload> news;
    private final long total;

    public NewsResponseDto(List<News.NewsPayload> news, long total) {
        super();
        this.news = news;
        this.total = total;
    }

    public static ResponseEntity<? super NewsResponseDto> success(List<News.NewsPayload> news, long total) {
        NewsResponseDto responseBody = new NewsResponseDto(news, total);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
