package com.gen.marketrss.interfaces.dto.request.auth;

import com.gen.marketrss.domain.entity.UsersEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    private String id;

    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String certificationNumber;

    public UsersEntity toEntity(String encodedPassword) {
        return UsersEntity.builder()
                .userId(id)
                .password(encodedPassword)
                .email(email)
                .type("app")
                .role("ROLE_USER")
                .build();
    }

}
