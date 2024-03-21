package com.gen.marketrss.domain.entity;

import com.gen.marketrss.interfaces.dto.payload.UserPayload;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public UserPayload toPayload() {
        return UserPayload.builder()
                .userId(userId)
                .email(email)
                .type(type)
                .role(role)
                .build();
    }
}
