package com.gen.marketrss.domain.entity;

import com.gen.marketrss.interfaces.dto.response.edit.StockResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "stock")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class StockEntity {

    @Id
    private String userId;
    private String stock;

    public StockResponseDto toDto() {
        List<String> stocks = stock.contains(",") ? List.of(stock.split(",")) : List.of(stock);
        return StockResponseDto.builder()
                .stocks(stocks)
                .build();
    }
}
