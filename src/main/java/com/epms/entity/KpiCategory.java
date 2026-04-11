package com.epms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kpi_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KpiCategory {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String kpiCategory;


}
