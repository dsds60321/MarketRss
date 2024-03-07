package com.gen.marketrss.domain.news;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", insertable = false, updatable = false)
    private News news;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "content_id")
    private List<Highlight> highlights = new ArrayList<>();

    private String symbol;
    private String name;
    private String exchange;
    private String exchange_long;
    private String country;
    private String type;
    private String industry;
    private double match_score;
    private double sentiment_score;
}
