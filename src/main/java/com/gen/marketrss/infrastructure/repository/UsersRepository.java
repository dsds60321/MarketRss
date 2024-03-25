package com.gen.marketrss.infrastructure.repository;

import com.gen.marketrss.domain.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, String> {

    boolean existsByUserId(String userId);
    UsersEntity findByUserId(String userId);

    List<UsersEntity> findByType(String type);

}
