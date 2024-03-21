package com.gen.marketrss.interfaces.controller;

import com.gen.marketrss.interfaces.dto.payload.UserPayload;
import com.gen.marketrss.interfaces.dto.response.edit.EditResponseDto;
import com.gen.marketrss.interfaces.service.EditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/edit")
@RestController
@RequiredArgsConstructor
public class EditController {

    private final EditService editService;

    @GetMapping
    public ResponseEntity<? super EditResponseDto> edit(@AuthenticationPrincipal UserPayload userPayload) {
        return editService.editData(userPayload.getUserId());
    }
}
