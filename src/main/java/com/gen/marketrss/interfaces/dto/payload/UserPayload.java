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
}
