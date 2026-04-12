package com.epms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionPerformanceDTO {
    private Integer positionId;
    private String positionTitle;
    private Integer positionLevel;
    private Double totalWeightedScore;
    private Double averageScore;
    private Integer totalKpis;
    private Integer completedKpis;
    private Double completionRate;
    private List<KpiPositionDTO> kpiDetails;
    private Map<String, Double> categoryScores;
}