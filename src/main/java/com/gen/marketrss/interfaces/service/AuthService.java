package com.gen.marketrss.interfaces.service;

import com.gen.marketrss.interfaces.dto.request.auth.CheckCertificationRequestDto;
import com.gen.marketrss.interfaces.dto.request.auth.EmailCertificationRequestDto;
import com.gen.marketrss.interfaces.dto.request.auth.IdCheckRequestDto;
import com.gen.marketrss.interfaces.dto.request.auth.SignUpRequestDto;
import com.gen.marketrss.interfaces.dto.response.auth.CheckCertificationResponseDto;
import com.gen.marketrss.interfaces.dto.response.auth.EmailCertificationResponseDto;
import com.gen.marketrss.interfaces.dto.response.auth.IdCheckResponseDto;
import com.gen.marketrss.interfaces.dto.response.auth.SignUpResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto);
    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);

    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);
}
