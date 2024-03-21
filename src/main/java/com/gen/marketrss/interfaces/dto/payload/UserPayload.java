package com.gen.marketrss.interfaces.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class UserPayload {

    private String userId;
    private String password;
    private String email;
    private String type;
    private String role;
}
