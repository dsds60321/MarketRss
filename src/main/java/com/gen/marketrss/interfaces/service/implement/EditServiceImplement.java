package com.gen.marketrss.interfaces.service.implement;

import com.gen.marketrss.domain.entity.UsersEntity;
import com.gen.marketrss.infrastructure.repository.UsersRepository;
import com.gen.marketrss.interfaces.dto.payload.UserPayload;
import com.gen.marketrss.interfaces.dto.response.edit.EditResponseDto;
import com.gen.marketrss.interfaces.service.EditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditServiceImplement implements EditService{

    private final UsersRepository usersRepository;
    private final RedisTemplate<String, UserPayload> userPayloadRedisTemplate;

    @Override
    public ResponseEntity<? super EditResponseDto> editData(String userId) {
        UserPayload userPayload = null;
        try {
            userPayload = userPayloadRedisTemplate.opsForValue().get(userId);

            if (userPayload == null) {
                userPayload = usersRepository.findByUserId(userId).toPayload();
                userPayloadRedisTemplate.opsForValue().set(userId, userPayload);
            }

        } catch (Exception e ) {
            e.printStackTrace();
            EditResponseDto.databaseError();
        }

        return EditResponseDto.success(userPayload);
    }
}
