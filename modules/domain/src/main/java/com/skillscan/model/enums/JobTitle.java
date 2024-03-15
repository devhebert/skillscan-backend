package com.skillscan.model.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum JobTitle {
    FRONT_END_DEVELOPER("Front-End Developer"),
    BACK_END_DEVELOPER("Back-End Developer"),
    FULL_STACK_DEVELOPER("Full Stack Developer"),
    DATABASE_DEVELOPER("Database Developer"),
    QA_DEVELOPER("QA Developer"),
    UX_UI_DESIGNER("UX/UI Designer"),
    INFRASTRUCTURE_DEPLOY_ENGINEER("Infrastructure and Deployment Engineer"),
    PROJECT_MANAGER("Project Manager"),
    PRODUCT_OWNER("Product Owner"),
    SCRUM_MASTER("Scrum Master"),
    TECH_LEAD("Tech Lead"),
    HR_SPECIALIST("HR Specialist"),
    WRITER("Writer"),
    SUPPORT("Support"),
    MARKETING_SPECIALIST("Marketing Specialist");

    private final String description;

    JobTitle(String description) {
        this.description = description;
    }

    public static JobTitle fromString(String jobTitle) {
        for (JobTitle jt : JobTitle.values()) {
            if (jt.name().equalsIgnoreCase(jobTitle)) {
                return jt;
            }
        }
        throw new IllegalArgumentException("No JobTitle with text " + jobTitle + " found");
    }
}