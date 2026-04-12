package com.epms.repository;

import com.epms.entity.KpiVersionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KpiVersionHistoryRepository extends JpaRepository<KpiVersionHistory, Integer> {
}