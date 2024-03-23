package com.gen.marketrss.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity(name = "feed_histories")
@Table
public class FeedHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String sendKakao;
    private String sendEmail;
    private int sendCount;

    @Column(columnDefinition = "DATE")
    private LocalDate regDate;

    @Setter
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private StockEntity stockEntity;
}
