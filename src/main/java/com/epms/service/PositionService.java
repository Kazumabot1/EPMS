package com.epms.service;

import com.epms.dto.PositionDTO;
import com.epms.entity.*;
import com.epms.exception.*;
import com.epms.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PositionService {

    private final PositionRepository positionRepository;
    private final KpiPositionRepository kpiPositionRepository;
    private final KpiRepository kpiRepository;

    public Position createPosition(PositionDTO dto, String createdBy) {
        if (positionRepository.findByPositionTitle(dto.getPositionTitle()).isPresent()) {
            throw new DuplicateResourceException("Position title already exists");
        }
        Position pos = new Position();
        mapDtoToEntity(dto, pos);
        pos.setCreatedBy(createdBy);
        pos.setCreatedAt(new Date());
        pos.setStatus(true);
        return positionRepository.save(pos);
    }

    public Position updatePosition(Integer id, PositionDTO dto) {
        Position pos = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found"));
        mapDtoToEntity(dto, pos);
        return positionRepository.save(pos);
    }

    @Transactional(readOnly = true)
    public Position getPositionById(Integer id) {
        return positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found"));
    }

    @Transactional(readOnly = true)
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Position> getActivePositions() {
        return positionRepository.findByStatus(true);
    }

    public void deletePosition(Integer id) {
        Position pos = getPositionById(id);
        if (!pos.getKpiPositions().isEmpty()) {
            throw new InvalidOperationException("Cannot delete position with assigned KPIs");
        }
        positionRepository.delete(pos);
    }

    public void assignKpiToPosition(Integer positionId, Integer kpiId, Double score,
                                    Double targetValue, String assignedBy) {
        Position pos = getPositionById(positionId);
        Kpi kpi = kpiRepository.findById(kpiId)
                .orElseThrow(() -> new ResourceNotFoundException("KPI not found"));
        if (kpiPositionRepository.findByKpi_IdAndPosition_Id(kpiId, positionId).isPresent()) {
            throw new DuplicateResourceException("KPI already assigned");
        }
        KpiPosition kp = new KpiPosition();
        kp.setPosition(pos);
        kp.setKpi(kpi);
        kp.setScore(score);
        kp.setTargetValue(targetValue);
        kp.setAssignedDate(new Date());
        kp.setAssignedBy(assignedBy);
        kp.setStatus("ACTIVE");
        kpiPositionRepository.save(kp);
    }

    public Map<String, Object> getPositionPerformanceSummary(Integer positionId) {
        List<KpiPosition> assignments = kpiPositionRepository.findByPositionIdWithDetails(positionId);
        double totalWeighted = assignments.stream()
                .filter(kp -> kp.getWeightedScore() != null)
                .mapToDouble(KpiPosition::getWeightedScore).sum();
        long achieved = assignments.stream().filter(KpiPosition::isTargetAchieved).count();
        long totalTargets = assignments.stream()
                .filter(kp -> kp.getTargetValue() != null && kp.getActualValue() != null).count();
        Map<String, Object> summary = new HashMap<>();
        summary.put("positionId", positionId);
        summary.put("positionTitle", assignments.isEmpty() ? "" : assignments.get(0).getPosition().getPositionTitle());
        summary.put("totalKpis", assignments.size());
        summary.put("totalWeightedScore", totalWeighted);
        summary.put("averageScore", kpiPositionRepository.calculateAverageScoreForPosition(positionId));
        summary.put("achievedTargets", achieved);
        summary.put("targetAchievementRate", totalTargets > 0 ? (double) achieved / totalTargets * 100 : 0.0);
        List<Map<String, Object>> details = new ArrayList<>();
        for (KpiPosition kp : assignments) {
            Map<String, Object> m = new HashMap<>();
            m.put("kpiId", kp.getKpi().getId());
            m.put("kpiTitle", kp.getKpi().getTitle());
            m.put("score", kp.getScore());
            m.put("weight", kp.getKpi().getWeight());
            m.put("weightedScore", kp.getWeightedScore());
            m.put("actualValue", kp.getActualValue());
            m.put("targetValue", kp.getTargetValue());
            m.put("achievement", kp.getAchievementPercentage());
            m.put("rating", kp.getPerformanceRating());
            details.add(m);
        }
        summary.put("kpiDetails", details);
        return summary;
    }

    private void mapDtoToEntity(PositionDTO dto, Position entity) {
        if (dto.getPositionTitle() != null) entity.setPositionTitle(dto.getPositionTitle());
        if (dto.getPositionLevel() != null) entity.setPositionLevel(dto.getPositionLevel());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
    }
}