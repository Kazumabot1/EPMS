package com.epms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "pip_progress_updates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PipProgressUpdate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String notes;
    private Integer percentageComplete;
    private Date updateDate = new Date();

    @ManyToOne
    @JoinColumn(name = "pip_id")
    private Pip pip;

    @ManyToOne
    @JoinColumn(name = "updated_by_id")
    private User updatedBy;
}