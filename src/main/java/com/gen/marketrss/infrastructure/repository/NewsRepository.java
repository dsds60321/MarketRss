package com.gen.marketrss.infrastructure.repository;

import com.gen.marketrss.domain.news.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {

}
