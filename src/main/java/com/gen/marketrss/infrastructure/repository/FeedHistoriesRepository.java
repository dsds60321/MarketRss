package com.gen.marketrss.infrastructure.repository;

import com.gen.marketrss.domain.entity.FeedHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedHistoriesRepository extends JpaRepository<FeedHistoryEntity, Long> {
}
