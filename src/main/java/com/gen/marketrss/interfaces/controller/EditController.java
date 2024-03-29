package com.gen.marketrss.interfaces.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gen.marketrss.interfaces.dto.payload.UserPayload;
import com.gen.marketrss.interfaces.dto.request.edit.StockRequestDto;
import com.gen.marketrss.interfaces.dto.response.edit.EditResponseDto;
import com.gen.marketrss.interfaces.service.EditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/edit")
@RestController
@RequiredArgsConstructor
@Slf4j
public class EditController {

    private final EditService editService;

    @GetMapping
    public ResponseEntity<? super EditResponseDto> edit(@AuthenticationPrincipal UserPayload userPayload) {
        return editService.userDetailData(userPayload.getUserId());
    }

    @PostMapping
    public ResponseEntity<? super EditResponseDto> editByPut(@RequestBody @Valid UserPayload userPayload) {
        return editService.putUserDetailData(userPayload);
    }


    @PostMapping("/stock")
    public ResponseEntity<? super EditResponseDto> registStock (@AuthenticationPrincipal UserPayload userPayload,@RequestBody @Valid StockRequestDto requestBody) {
        return editService.registStock(userPayload.getUserId(), requestBody);
    }

    @GetMapping("/kakao")
    public ResponseEntity<? super EditResponseDto> kakaoFeed(@AuthenticationPrincipal UserPayload userPayload) {
        return editService.kakaoFeed(userPayload.getUserId());
    }
}
