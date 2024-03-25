package com.gen.marketrss.infrastructure.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gen.marketrss.domain.entity.CustomOAuth2User;
import com.gen.marketrss.domain.entity.UsersEntity;
import com.gen.marketrss.infrastructure.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService {

    private final UsersRepository usersRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();
        UsersEntity usersEntity = null;
        String userId = null;

        if (oauthClientName.equals("kakao")) {
            userId = "kakao_" + oAuth2User.getAttributes().get("id");
            usersEntity = UsersEntity.builder()
                    .userId(userId)
                    .email("")
                    .type("kakao")
                    .kakao_token(request.getAccessToken().getTokenValue())
                    .role("ROLE_USER")
                    .send_email("N")
                    .send_kakao("Y")
                    .build();

        }

        if (usersEntity != null) {
            usersRepository.save(usersEntity);
        }

        return new CustomOAuth2User(userId);
    }
}
