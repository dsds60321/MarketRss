package com.gen.marketrss.interfaces.dto.response.edit;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StockResponseDto {

    private List<String> stocks;
}
