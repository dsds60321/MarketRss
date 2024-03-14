package com.gen.marketrss.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "certification")
@Table(name = "certification")
public class CertificationEntity {

    @Id
    private String userId;
    private String email;
    private String certificationNumber;
}
