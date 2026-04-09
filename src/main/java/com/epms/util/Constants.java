package com.epms.util;

public class Constants {
    // Roles
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_HR = "ROLE_HR";
    public static final String ROLE_MANAGER = "ROLE_MANAGER";
    public static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
    public static final String ROLE_EXECUTIVE = "ROLE_EXECUTIVE";

    // Appraisal status
    public static final String APPRAISAL_DRAFT = "DRAFT";
    public static final String APPRAISAL_SUBMITTED = "SUBMITTED";
    public static final String APPRAISAL_REVIEWED = "REVIEWED";
    public static final String APPRAISAL_LOCKED = "LOCKED";

    // Cycle status
    public static final String CYCLE_DRAFT = "DRAFT";
    public static final String CYCLE_ACTIVE = "ACTIVE";
    public static final String CYCLE_COMPLETED = "COMPLETED";
    public static final String CYCLE_LOCKED = "LOCKED";

    // PIP status
    public static final String PIP_DRAFT = "DRAFT";
    public static final String PIP_ACTIVE = "ACTIVE";
    public static final String PIP_COMPLETED = "COMPLETED";
    public static final String PIP_FAILED = "FAILED";
    public static final String PIP_CANCELLED = "CANCELLED";

    // Meeting status
    public static final String MEETING_SCHEDULED = "SCHEDULED";
    public static final String MEETING_COMPLETED = "COMPLETED";
    public static final String MEETING_CANCELLED = "CANCELLED";

    // Feedback source
    public static final String FEEDBACK_MANAGER = "MANAGER";
    public static final String FEEDBACK_PEER = "PEER";
    public static final String FEEDBACK_SUBORDINATE = "SUBORDINATE";
    public static final String FEEDBACK_SELF = "SELF";

    // Notification type
    public static final String NOTIF_APPRAISAL = "APPRAISAL";
    public static final String NOTIF_FEEDBACK = "FEEDBACK";
    public static final String NOTIF_PIP = "PIP";
    public static final String NOTIF_MEETING = "MEETING";

    // Performance categories
    public static final String CAT_OUTSTANDING = "Outstanding";
    public static final String CAT_GOOD = "Good";
    public static final String CAT_MEET_REQUIREMENT = "Meet Requirement";
    public static final String CAT_NEED_IMPROVEMENT = "Need Improvement";
    public static final String CAT_UNSATISFACTORY = "Unsatisfactory";
}