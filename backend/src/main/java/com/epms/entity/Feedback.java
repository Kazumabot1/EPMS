package com.epms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "feedbacks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer targetEmployeeId;
    private Integer sourceUserId;
    private String sourceType; // MANAGER, PEER, SUBORDINATE, SELF
    private Integer cycleId;
    private Integer rating; // 1-5
    private String comments;
    private Boolean isAnonymous = false;
    @Temporal(TemporalType.TIMESTAMP)
    private Date submittedAt;
    private Boolean isLocked = false;
}