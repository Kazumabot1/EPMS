package com.epms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionDTO {
    private String positionTitle;
    private Integer positionLevel;
    private String description;
    private Boolean status;
}