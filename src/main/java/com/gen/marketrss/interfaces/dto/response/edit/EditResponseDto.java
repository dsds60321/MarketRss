package com.gen.marketrss.interfaces.dto.response.edit;

import com.gen.marketrss.interfaces.dto.payload.UserPayload;
import com.gen.marketrss.interfaces.dto.response.ResponseDto;
import com.gen.marketrss.interfaces.dto.response.auth.TokenResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class EditResponseDto extends ResponseDto {

    private final UserPayload payload;

    public EditResponseDto(UserPayload payload) {
        super();
        this.payload = payload;
    }


    public static ResponseEntity<EditResponseDto> success (UserPayload userPayload) {
        EditResponseDto responseBody = new EditResponseDto(userPayload);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
