package com.epms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KpiPerformanceDTO {
    private Integer kpiId;
    private String kpiTitle;
    private String kpiCategory;
    private Double target;
    private String unit;
    private Integer weight;
    private Double averageActual;
    private Double averageScore;
    private Double averageWeightedScore;
    private Integer assignedPositions;
    private Double bestScore;
    private Double worstScore;
    private List<KpiPositionDTO> positionDetails;
}