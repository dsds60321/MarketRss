package com.gen.marketrss.interfaces.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPayload implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String userId;
    private String password;
    private String email;
    private String type;
    private String role;
    private String send_kakao;
    private String send_email;

    private void setEmail(String email) {
        this.email = email;
    }

    // No버튼일 경우 이메일 지우기
    public void updateEmailBySelectNo() {
        if (this.send_email.equalsIgnoreCase("n")) {
            setEmail("");
        }
    }

}
