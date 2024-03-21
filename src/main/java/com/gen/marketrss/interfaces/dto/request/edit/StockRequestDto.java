package com.gen.marketrss.interfaces.dto.request.edit;

import com.gen.marketrss.domain.entity.StockEntity;
import com.gen.marketrss.domain.entity.UsersEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.List;

@Getter
public class StockRequestDto {

    @NotEmpty
    private List<String> stocks;

    public StockEntity toEntity(String userId) {
        return StockEntity.builder()
                .userId(userId)
                .stock(StringUtils.collectionToDelimitedString(stocks, ","))
                .build();
    }
}
