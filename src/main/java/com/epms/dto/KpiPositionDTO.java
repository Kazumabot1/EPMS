package com.epms.dto;

import lombok.Data;

@Data
public class KpiPositionDTO {
    private Integer kpiId;
    private Integer positionId;
    private Double score;
    private Double targetValue;
    private Double actualValue;
    private String status;
}