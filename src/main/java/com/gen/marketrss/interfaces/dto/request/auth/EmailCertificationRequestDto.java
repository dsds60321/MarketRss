package com.gen.marketrss.interfaces.dto.request.auth;

import com.gen.marketrss.domain.entity.CertificationEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailCertificationRequestDto {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z\\d]{6,20}$")
    private String id;

    @Email
    @NotBlank
    private String email;

    public CertificationEntity toEntity(String certificationNumber) {
        return CertificationEntity.builder()
                .userId(id)
                .email(email)
                .certificationNumber(certificationNumber)
                .build();
    }
}
