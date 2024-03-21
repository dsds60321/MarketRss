package com.gen.marketrss.interfaces.service.implement;

import com.gen.marketrss.domain.entity.CertificationEntity;
import com.gen.marketrss.domain.entity.UsersEntity;
import com.gen.marketrss.infrastructure.common.provider.EmailProvider;
import com.gen.marketrss.infrastructure.common.provider.JwtProvider;
import com.gen.marketrss.infrastructure.repository.CertificationRepository;
import com.gen.marketrss.infrastructure.repository.UsersRepository;
import com.gen.marketrss.interfaces.dto.request.auth.*;
import com.gen.marketrss.interfaces.dto.response.auth.*;
import com.gen.marketrss.interfaces.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    @Value("${jwt.refresh-time}")
    private long refreshTimeout;

    private final UsersRepository usersRepository;
    private final CertificationRepository certificationRepository;
    private final EmailProvider emailProvider;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, SignInRequestDto> signInRedisTemplate;

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
        String accessToken;
        String refreshToken;

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

            accessToken = jwtProvider.generateAccessToken(userId);
            refreshToken = jwtProvider.generateRefreshToken(userId);
            stringRedisTemplate.opsForValue().set(userId + "_refreshToken_" + LocalDate.now(), refreshToken, refreshTimeout , TimeUnit.DAYS);

            // user 정보 redis 저장
            signInRedisTemplate.opsForValue().set(userId, dto, Duration.ofDays(refreshTimeout));

        } catch (Exception e ) {
            e.printStackTrace();
            return SignInResponseDto.databaseError();
        }

        return SignInResponseDto.success(accessToken, refreshToken);
    }

    @Override
    public ResponseEntity<? super TokenResponseDto> refreshToken(String userId, TokenRequestDto dto) {
        String accessToken = "";

        try {
            String refreshToken = dto.getRefreshToken();

            Boolean isExpired = jwtProvider.isTokenExpired(refreshToken);

            if (isExpired) {
                return TokenResponseDto.expiredToken();
            }

            accessToken = jwtProvider.generateAccessToken(userId);

            if (!StringUtils.hasText(accessToken)) {
                return TokenResponseDto.databaseError();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return TokenResponseDto.databaseError();
        }
        return TokenResponseDto.success(accessToken, dto.getRefreshToken());
    }

    private String getCertificationNumber() {
        StringBuilder certificationNumber = new StringBuilder();

        for (int count = 0; count < 4; count ++) certificationNumber.append((int) (Math.random() * 10));

        return certificationNumber.toString();
    }
}