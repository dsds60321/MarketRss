package com.gen.marketrss.interfaces.dto.request.auth;

import com.gen.marketrss.domain.entity.CertificationEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailCertificationRequestDto {

    @NotBlank
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
