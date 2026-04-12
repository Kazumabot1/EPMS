package com.epms.dto;

import lombok.Data;
import java.util.Date;

@Data
public class UserDTO {
    private String email;
    private String password;
    private String fullName;
    private String employeeCode;
    private String position;
    private Integer managerId;
    private Integer departmentId;
    private Boolean active;
    private Date joinDate;
}