package com.gen.marketrss.interfaces.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignInRequestDto {

    @NotBlank
    private String id;

    @NotBlank
    private String password;
}
