package com.gen.marketrss.interfaces.dto.response.auth;

import com.gen.marketrss.common.constant.ResponseCode;
import com.gen.marketrss.common.constant.ResponseMessage;
import com.gen.marketrss.interfaces.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class TokenResponseDto extends ResponseDto {

    private final String accessToken;
    private final String refreshToken;

    private TokenResponseDto(String accessToken, String refreshToken) {
        super();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static ResponseEntity<TokenResponseDto> success (String accessToken, String refreshToken) {
        TokenResponseDto responseBody = new TokenResponseDto(accessToken, refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<TokenResponseDto> expiredToken() {
        TokenResponseDto responseBody = new TokenResponseDto(ResponseCode.UN_AUTHORIZED, ResponseMessage.UN_AUTHORIZED);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
    }
}
