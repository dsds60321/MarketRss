package com.gen.marketrss.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class HighlightPayload {

    private Long id;
    private String highlight;
    private double sentiment;
    private String highlighted_in;

}
