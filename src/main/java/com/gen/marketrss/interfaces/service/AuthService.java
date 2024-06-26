package com.gen.marketrss.interfaces.service;

import com.gen.marketrss.interfaces.dto.payload.UserPayload;
import com.gen.marketrss.interfaces.dto.request.auth.*;
import com.gen.marketrss.interfaces.dto.response.auth.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto);
    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);

    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);
    ResponseEntity<? super SignInResponseDto> signIn (SignInRequestDto dto);
    ResponseEntity<? super TokenResponseDto> refreshToken (UserPayload user, TokenRequestDto dto);

    ResponseEntity<? super SignInResponseDto> logout(UserPayload userId);
}
