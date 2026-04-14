package com.epms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notification_templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTemplate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;          // e.g., "PIP_CREATED"
    private String subject;
    private String bodyTemplate;
    private String channel;       // EMAIL, IN_APP, BOTH

    @ElementCollection
    private Set<String> triggerEvents = new HashSet<>();
}