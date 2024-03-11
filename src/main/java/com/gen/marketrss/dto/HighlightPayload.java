package com.gen.marketrss.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@Getter
public class HighlightPayload implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String highlight;
    private double sentiment;
    private String highlighted_in;

}
