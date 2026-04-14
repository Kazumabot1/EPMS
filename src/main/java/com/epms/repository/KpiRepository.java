package com.epms.repository;

import com.epms.entity.Kpi;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface KpiRepository extends JpaRepository<Kpi, Integer> {
    boolean existsByTitleAndCreatedByUser_Id(String title, Integer userId);
    List<Kpi> findByKpiCategory(String category);
}