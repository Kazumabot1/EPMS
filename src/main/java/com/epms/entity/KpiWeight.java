package com.epms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kpi_weight")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KpiWeight {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer kpiWeight;


}
