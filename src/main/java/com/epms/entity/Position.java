package com.epms.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String positiontitle;
    private Integer positionlevel;
    private String description;
    private boolean status;
    private Date createdAt;
    private String createdBy;


}
