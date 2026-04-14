package com.epms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notification_templates")
@Entity
@Table(name = "notification_templates") // fix table name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String channelType;
    private String subjectTemplate;
    private String bodyTemplate;

    @OneToMany(mappedBy = "notificationTemplate")
    private java.util.List<Notification> notifications;
}