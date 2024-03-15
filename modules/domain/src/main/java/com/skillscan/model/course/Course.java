package com.skillscan.model.course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Document(collection = "course")
public class Course {
    @Id
    private UUID id;
    private UUID technologyId;
    private String name;
    private String content;
    private List<String> keywords;
    private LocalDateTime createAt;

    public Course() {}

    public Course(String name, String content, List<String> keywords) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.content = content;
        this.keywords = keywords;
    }

    public Course(UUID technologyId, String name, String content, List<String> keywords) {
        this.id = UUID.randomUUID();
        this.technologyId = technologyId;
        this.name = name;
        this.content = content;
        this.createAt = LocalDateTime.now();
        this.keywords = keywords;
    }
}
