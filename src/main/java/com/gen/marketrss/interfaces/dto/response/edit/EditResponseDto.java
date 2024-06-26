package com.gen.marketrss.interfaces.dto.response.edit;

import com.gen.marketrss.interfaces.dto.payload.UserPayload;
import com.gen.marketrss.interfaces.dto.response.ResponseDto;
import com.gen.marketrss.interfaces.dto.response.auth.TokenResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class EditResponseDto extends ResponseDto {

    private final UserPayload userPayload;
    private final StockResponseDto stockPayload;

    public EditResponseDto(UserPayload userPayload, StockResponseDto stockResponseDto) {
        super();
        this.userPayload = userPayload;
        this.stockPayload = stockResponseDto;
    }


    public static ResponseEntity<EditResponseDto> success (UserPayload userPayload, StockResponseDto stockPayload) {
        EditResponseDto responseBody = new EditResponseDto(userPayload, stockPayload);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
