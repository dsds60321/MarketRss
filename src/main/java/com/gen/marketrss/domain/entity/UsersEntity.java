package com.gen.marketrss.domain.entity;

import com.gen.marketrss.interfaces.dto.payload.UserPayload;
import com.gen.marketrss.interfaces.dto.request.auth.SignInRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.context.annotation.Description;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity(name = "users")
@Table
@DynamicInsert
public class UsersEntity {

    @Id
    private String userId;
    private String password;
    private String email;
    private String type;
    private String role;
    private String kakao_token;
    @Column(nullable = false)
    @ColumnDefault("Y")
    private String send_email;
    @Column(nullable = false)
    @ColumnDefault("N")
    private String send_kakao;

    public UserPayload toPayload() {
        return UserPayload.builder()
                .userId(userId)
                .email(email)
                .type(type)
                .role(role)
                .send_email(send_email)
                .send_kakao(send_kakao)
                .build();
    }

    public void update(UserPayload user) {
        this.email = user.getEmail();
        this.send_email = user.getSend_email();
        this.send_kakao = user.getSend_kakao();
    }
}
