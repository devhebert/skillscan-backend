package com.skillscan.model.tecnology;

import com.skillscan.model.enums.Category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Document(collection = "tecnology")
public class Technology {
    @Id
    private UUID id;
    private Category category;
    private String name;
    private LocalDateTime createAt;

    public Technology() {}

    public Technology(Category category, String name) {
        this.id = UUID.randomUUID();
        this.category = category;
        this.name = name;
        this.createAt = LocalDateTime.now();
    }
}
