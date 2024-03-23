package com.gen.marketrss.domain.entity;

import com.gen.marketrss.interfaces.dto.payload.UserPayload;
import com.gen.marketrss.interfaces.dto.request.auth.SignInRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity(name = "users")
@Table
public class UsersEntity {

    @Id
    private String userId;
    private String password;
    private String email;
    private String type;
    private String role;
    private String send_kakao;
    private String send_email;

    public UserPayload toPayload() {
        return UserPayload.builder()
                .userId(userId)
                .email(email)
                .type(type)
                .role(role)
                .build();
    }

}
