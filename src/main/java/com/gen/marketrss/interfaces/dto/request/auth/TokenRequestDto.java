package com.gen.marketrss.interfaces.dto.request.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class TokenRequestDto {

    private String accessToken;

    @NotEmpty
    private String refreshToken;

    public void generateAccessToken(String newAccessToken) {
        this.accessToken = newAccessToken;
    }
}
