package com.skillscan.model.coursesuggestion;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@Document(collection = "course-suggestion")
public class CourseSuggestion {
    @Id
    private UUID id;
    private String name;
    private String content;

    public CourseSuggestion() {
    }

    public CourseSuggestion(String name, String content) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.content = content;
    }
}
