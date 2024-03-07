package com.gen.marketrss.domain.news;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;
    private String title;
    private String description;
    private String keywords;
    private String snippet;
    private String url;
    private String image_url;
    private String language;
    private String published_at;
    private String source;
    private String relevance_score;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "id")
//    private List<Content> contents = new ArrayList<>();
}
