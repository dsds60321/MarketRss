package com.gen.marketrss.interfaces.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IdCheckRequestDto {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z\\d]{6,20}$")
    private String id;
}
