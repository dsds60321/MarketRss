package com.gen.marketrss.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity(name = "certification")
@Table
public class CertificationEntity {

    @Id
    private String userId;
    private String email;
    private String certificationNumber;
    private int verifyCount;

    public void updateByVerifyCount() {
        ++verifyCount;
    }
}
