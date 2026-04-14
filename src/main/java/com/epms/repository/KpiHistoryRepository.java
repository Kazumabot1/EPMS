package com.epms.repository;

import com.epms.entity.KpiHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KpiHistoryRepository extends JpaRepository<KpiHistory, Integer> {
}