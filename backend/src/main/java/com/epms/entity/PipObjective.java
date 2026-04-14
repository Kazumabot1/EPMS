package com.epms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "pip_objectives")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PipObjective {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;
    private String expectedOutcome;
    private Date dueDate;
    private Boolean achieved = false;

    @ManyToOne
    @JoinColumn(name = "pip_id")
    private Pip pip;
}