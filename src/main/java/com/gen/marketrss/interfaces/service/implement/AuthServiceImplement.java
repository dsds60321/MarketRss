package com.gen.marketrss.interfaces.service.implement;

import com.gen.marketrss.domain.entity.CertificationEntity;
import com.gen.marketrss.domain.entity.UsersEntity;
import com.gen.marketrss.infrastructure.common.provider.EmailProvider;
import com.gen.marketrss.infrastructure.repository.CertificationRepository;
import com.gen.marketrss.infrastructure.repository.UsersRepository;
import com.gen.marketrss.interfaces.dto.request.auth.CheckCertificationRequestDto;
import com.gen.marketrss.interfaces.dto.request.auth.EmailCertificationRequestDto;
import com.gen.marketrss.interfaces.dto.request.auth.IdCheckRequestDto;
import com.gen.marketrss.interfaces.dto.request.auth.SignUpRequestDto;
import com.gen.marketrss.interfaces.dto.response.auth.CheckCertificationResponseDto;
import com.gen.marketrss.interfaces.dto.response.auth.EmailCertificationResponseDto;
import com.gen.marketrss.interfaces.dto.response.auth.IdCheckResponseDto;
import com.gen.marketrss.interfaces.dto.response.auth.SignUpResponseDto;
import com.gen.marketrss.interfaces.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UsersRepository usersRepository;
    private final CertificationRepository certificationRepository;
    private final EmailProvider emailProvider;
    private final PasswordEncoder passwordEncoder;

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
            return IdCheckResponseDto.databaseError();
        }

        return IdCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto certificationRequestDto) {
        try {
            String userId = certificationRequestDto.getId();
            String email = certificationRequestDto.getEmail();

            boolean isExistId = usersRepository.existsByUserId(userId);
            if (isExistId) {
                return EmailCertificationResponseDto.duplicatedId();
            }

            String certificationNumber = getCertificationNumber();
            boolean isSucceed = emailProvider.sendCertificationMail(email, certificationNumber);

            if (!isSucceed) {
                return EmailCertificationResponseDto.mailSendFail();
            }

            CertificationEntity certificationEntity = certificationRequestDto.toEntity(certificationNumber);

            certificationRepository.save(certificationEntity);


        } catch (Exception e) {
            e.printStackTrace();
            return EmailCertificationResponseDto.databaseError();
        }
        return EmailCertificationResponseDto.success();
    }


    @Override
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto) {
        try {
            String userId = dto.getId();
            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();

            CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);
            if (certificationEntity == null) {
                return CheckCertificationResponseDto.certificationFail();
            }

            boolean isMatched = certificationEntity.getEmail().equals(email) && certificationEntity.getCertificationNumber().equals(certificationNumber);

            if (!isMatched) {
                return CheckCertificationResponseDto.certificationFail();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return CheckCertificationResponseDto.databaseError();
        }
        return CheckCertificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
        try {
            String userId = dto.getId();
            boolean isExistId = usersRepository.existsByUserId(userId);

            if(isExistId) {
                return SignUpResponseDto.duplicateId();
            }

            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();
            CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);
            boolean isMatched =
                    certificationEntity.getEmail().equals(email) && certificationEntity.getCertificationNumber().equals(certificationNumber);

            if (!isMatched) {
                return SignUpResponseDto.certificationFail();
            }

            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);

            UsersEntity usersEntity = dto.toEntity(encodedPassword);
            usersRepository.save(usersEntity);

            certificationRepository.deleteByUserId(userId);

        } catch (Exception e) {
            e.printStackTrace();
            return SignUpResponseDto.databaseError();
        }

        return SignUpResponseDto.success();
    }

    private String getCertificationNumber() {
        String certificationNumber = "";

        for (int count = 0; count < 4; count ++) certificationNumber += (int) (Math.random() * 10);

        return certificationNumber;
    }
}
