package com.gen.marketrss.interfaces.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.Base64;

@Getter
public class CheckCertificationRequestDto {

    @NotBlank
    private String id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String certificationNumber;

}
