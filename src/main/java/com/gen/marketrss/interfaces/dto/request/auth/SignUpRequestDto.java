package com.gen.marketrss.interfaces.dto.request.auth;

import com.gen.marketrss.domain.entity.UsersEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z\\d]{6,20}$")
    private String id;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*&])[A-Za-z\\d!@#$%^&*&]{8,15}$")
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
