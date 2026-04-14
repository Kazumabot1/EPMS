package com.epms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "meeting_action_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingActionItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;
    private Date dueDate;
    private Boolean completed = false;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private OneOnOneMeeting meeting;

    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;
}