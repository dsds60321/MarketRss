package com.gen.marketrss.interfaces.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.util.Base64;

@Getter
public class CheckCertificationRequestDto {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z\\d]{6,20}$")
    private String id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String certificationNumber;

}
