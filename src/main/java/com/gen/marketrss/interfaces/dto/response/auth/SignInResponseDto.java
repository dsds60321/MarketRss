package com.gen.marketrss.interfaces.dto.response.auth;

import com.gen.marketrss.common.constant.ResponseCode;
import com.gen.marketrss.common.constant.ResponseMessage;
import com.gen.marketrss.interfaces.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignInResponseDto extends ResponseDto {

    private final String accessToken;
    private final String refreshToken;

    private SignInResponseDto(String accessToken, String refreshToken) {
        super();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static ResponseEntity<SignInResponseDto> success (String accessToken, String refreshToken) {
        SignInResponseDto responseBody = new SignInResponseDto(accessToken, refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> signInFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }

}
