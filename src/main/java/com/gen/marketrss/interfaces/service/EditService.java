package com.gen.marketrss.interfaces.service;

import com.gen.marketrss.interfaces.dto.request.edit.StockRequestDto;
import com.gen.marketrss.interfaces.dto.response.edit.EditResponseDto;
import org.springframework.http.ResponseEntity;

public interface EditService {
    ResponseEntity<? super EditResponseDto> editData(String userId);
    ResponseEntity<? super EditResponseDto> registStock(String userId, StockRequestDto stock);
}