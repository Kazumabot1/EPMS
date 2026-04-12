package com.epms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private String title;
    private String message;
    private String type; // APPRAISAL, FEEDBACK, PIP, MEETING
    private Boolean isRead = false;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
}