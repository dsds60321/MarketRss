package com.gen.marketrss.interfaces.service.implement;

import com.gen.marketrss.domain.entity.CertificationEntity;
import com.gen.marketrss.infrastructure.common.provider.EmailProvider;
import com.gen.marketrss.infrastructure.repository.CertificationRepository;
import com.gen.marketrss.infrastructure.repository.UsersRepository;
import com.gen.marketrss.interfaces.dto.request.auth.EmailCertificationRequestDto;
import com.gen.marketrss.interfaces.dto.request.auth.IdCheckRequestDto;
import com.gen.marketrss.interfaces.dto.response.ResponseDto;
import com.gen.marketrss.interfaces.dto.response.auth.EmailCertificationResponseDto;
import com.gen.marketrss.interfaces.dto.response.auth.IdCheckResponseDto;
import com.gen.marketrss.interfaces.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UsersRepository usersRepository;
    private final CertificationRepository certificationRepository;
    private final EmailProvider emailProvider;

    @Override
    public ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto) {
        try {

            String userId = dto.getId();
            boolean isExistId = usersRepository.existsById(userId);

            if (isExistId) {
                return IdCheckResponseDto.duplicateId();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return IdCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto) {
        try {
            String userId = dto.getId();
            String email = dto.getEmail();

            boolean isExistId = usersRepository.existsByUserId(userId);
            if (isExistId) {
                return EmailCertificationResponseDto.duplicatedId();
            }

            String certificationNumber = getCertificationNumber();
            boolean isSucceed = emailProvider.sendCertificationMail(email, certificationNumber);

            if (!isSucceed) {
                return EmailCertificationResponseDto.mailSendFail();
            }

            CertificationEntity certificationEntity = new CertificationEntity(userId, email, certificationNumber);
            certificationRepository.save(certificationEntity);


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return EmailCertificationResponseDto.success();
    }

    private String getCertificationNumber() {
        String certificationNumber = "";

        for (int count = 0; count < 4; count ++) certificationNumber += (int) (Math.random() * 10);

        return certificationNumber;
    }
}
