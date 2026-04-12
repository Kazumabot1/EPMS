package com.epms.dto;

import lombok.Data;

@Data
public class KpiDTO {
    private String title;
    private String kpiCategory;
    private Double target;
    private String unit;
    private Integer weight;
}