package com.skillscan.model.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Category {
    FRONT_END("Front-End Technologies"),
    BACK_END("Back-End Technologies"),
    DATABASE("Database Technologies"),
    QA("Quality Assurance"),
    UX_UI("User Experience and User Interface"),
    VERSION_CONTROL("Version Control"),
    INFRASTRUCTURE_DEPLOY("Infrastructure and Deployment"),
    COMMUNICATION_COLLABORATION("Communication and Collaboration");

    private final String description;

    Category(String description) {
        this.description = description;
    }
}
