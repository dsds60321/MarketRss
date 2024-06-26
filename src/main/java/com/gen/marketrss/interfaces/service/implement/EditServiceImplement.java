package com.gen.marketrss.interfaces.service.implement;

import com.gen.marketrss.domain.entity.StockEntity;
import com.gen.marketrss.domain.entity.UsersEntity;
import com.gen.marketrss.infrastructure.api.KaKaoMessageService;
import com.gen.marketrss.infrastructure.common.util.RedisUtil;
import com.gen.marketrss.infrastructure.repository.StockRepository;
import com.gen.marketrss.infrastructure.repository.UsersRepository;
import com.gen.marketrss.interfaces.dto.payload.UserPayload;
import com.gen.marketrss.interfaces.dto.request.edit.StockRequestDto;
import com.gen.marketrss.interfaces.dto.response.ResponseDto;
import com.gen.marketrss.interfaces.dto.response.edit.EditResponseDto;
import com.gen.marketrss.interfaces.dto.response.edit.StockResponseDto;
import com.gen.marketrss.interfaces.service.EditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EditServiceImplement implements EditService{

    private final UsersRepository usersRepository;
    private final StockRepository stockRepository;
    private final KaKaoMessageService kaKaoMessageService;
    private final RedisUtil redisUtil;

    @Override
    public ResponseEntity<? super EditResponseDto> userDetailData(String userId) {
        UserPayload userPayload = null;
        StockResponseDto stockResponseDto = null;
        try {
            userPayload = redisUtil.get(userId, UserPayload.class);
            StockEntity stockEntity = stockRepository.findByUserId(userId);

            if (stockEntity != null) {
                stockResponseDto = stockEntity.toDto();
            }

            if (userPayload == null) {
                userPayload = usersRepository.findByUserId(userId).toPayload();
                redisUtil.set(userId, userPayload);
            }

        } catch (Exception e) {
            e.printStackTrace();
            EditResponseDto.databaseError();
        }

        return EditResponseDto.success(userPayload, stockResponseDto);
    }

    @Transactional
    @Override
    public ResponseEntity<? super EditResponseDto> putUserDetailData(UserPayload userPayload) {

        try {
            UsersEntity userEntity = usersRepository.findByUserId(userPayload.getUserId());
            if (userEntity != null) {

                if (userPayload.getSend_email().equalsIgnoreCase("n")) {
                    userPayload.updateEmailBySelectNo();
                }

                userEntity.update(userPayload);
                redisUtil.set(userEntity.getUserId(), userEntity.toPayload());
            } else {
                return ResponseDto.validationFail();
            }

        } catch (Exception e) {
            e.printStackTrace();
            EditResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<? super EditResponseDto> registStock(String userId, StockRequestDto requestBody) {
        try {

            UsersEntity usersEntity = usersRepository.findByUserId(userId);

            if (usersEntity == null) {
                return EditResponseDto.validationFail();
            }

            stockRepository.save(requestBody.toEntity(userId));

        } catch (Exception e) {
            e.printStackTrace();
            return EditResponseDto.databaseError();
        }

        return EditResponseDto.success();
    }

    @Override
    public ResponseEntity<? super EditResponseDto> kakaoFeed(String userId) {

        if (!kaKaoMessageService.sendFeed(userId)) {
            return EditResponseDto.databaseError();
        }

        return EditResponseDto.success();
    }

    public static String getRemoveDuplicateStock(String originStock, List<String> newStocks) {
        if (originStock.contains(",")) {
            String[] splitOriginStocks = originStock.split(",");
            List<String> originStocks = new ArrayList<>(Arrays.asList(splitOriginStocks));
            newStocks.addAll(originStocks);
        } else {
            newStocks.add(originStock);
        }
        Set<String> setStocks = new HashSet<>(newStocks);
        return StringUtils.collectionToDelimitedString(setStocks, ",");
    }
}
