package com.example.trading_app.repository;

import com.example.trading_app.model.MarketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface MarketRepository extends JpaRepository<MarketItem, Integer> {
    // Additional custom queries can be added if needed.

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM MarketItem m WHERE m.number = :id")
    Optional<MarketItem> findByIdForUpdate(@Param("id") Integer id);
}
