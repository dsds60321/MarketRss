package com.gen.marketrss.interfaces.service;

import com.gen.marketrss.interfaces.dto.payload.UserPayload;
import com.gen.marketrss.interfaces.dto.request.edit.StockRequestDto;
import com.gen.marketrss.interfaces.dto.response.edit.EditResponseDto;
import org.springframework.http.ResponseEntity;

public interface EditService {
    ResponseEntity<? super EditResponseDto> userDetailData(String userId);
    ResponseEntity<? super EditResponseDto> putUserDetailData(UserPayload user);
    ResponseEntity<? super EditResponseDto> registStock(String userId, StockRequestDto stock);

    ResponseEntity<? super EditResponseDto> kakaoFeed(String userId);
}
