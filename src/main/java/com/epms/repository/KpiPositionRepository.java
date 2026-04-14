package com.epms.repository;

import com.epms.entity.KpiPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface KpiPositionRepository extends JpaRepository<KpiPosition, Integer> {
    Optional<KpiPosition> findByKpi_IdAndPosition_Id(Integer kpiId, Integer positionId);
    void deleteByKpi_IdAndPosition_Id(Integer kpiId, Integer positionId);

    @Query("SELECT kp FROM KpiPosition kp JOIN FETCH kp.kpi JOIN FETCH kp.position WHERE kp.kpi.id = :kpiId")
    List<KpiPosition> findByKpiIdWithDetails(@Param("kpiId") Integer kpiId);
}