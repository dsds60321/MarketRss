package com.gen.marketrss.interfaces.service.implement;

import com.gen.marketrss.domain.entity.CertificationEntity;
import com.gen.marketrss.domain.entity.UsersEntity;
import com.gen.marketrss.infrastructure.common.provider.EmailProvider;
import com.gen.marketrss.infrastructure.common.provider.JwtProvider;
import com.gen.marketrss.infrastructure.repository.CertificationRepository;
import com.gen.marketrss.infrastructure.repository.UsersRepository;
import com.gen.marketrss.interfaces.dto.request.auth.*;
import com.gen.marketrss.interfaces.dto.response.ResponseDto;
import com.gen.marketrss.interfaces.dto.response.auth.*;
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
    private final JwtProvider jwtProvider;

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

            if (certificationEntity.getVerifyCount() == 5) {
                return CheckCertificationResponseDto.exceedAttemptLimitAndFail();
            }

            boolean isMatched = certificationEntity.getEmail().equals(email) && certificationEntity.getCertificationNumber().equals(certificationNumber);

            if (!isMatched) {
                certificationEntity.updateByVerifyCount();
                certificationRepository.save(certificationEntity);
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

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
        String token = null;

        try {
            String userId = dto.getId();
            UsersEntity usersEntity = usersRepository.findByUserId(userId);
            if (usersEntity == null) {
                return SignInResponseDto.signInFail();
            }

            String password = dto.getPassword();
            String encodedPassword = usersEntity.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);

            if (!isMatched) {
                return SignInResponseDto.signInFail();
            }

            token = jwtProvider.create(userId);

        } catch (Exception e ) {
            e.printStackTrace();
            return SignInResponseDto.databaseError();
        }

        return SignInResponseDto.success(token);
    }

    private String getCertificationNumber() {
        String certificationNumber = "";

        for (int count = 0; count < 4; count ++) certificationNumber += (int) (Math.random() * 10);

        return certificationNumber;
    }
}
