package com.gen.marketrss.infrastructure.repository;

import com.gen.marketrss.domain.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, String> {

    StockEntity findByUserId(String userId);

}
