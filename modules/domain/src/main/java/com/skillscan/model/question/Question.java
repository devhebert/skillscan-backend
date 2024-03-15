package com.skillscan.model.question;

import com.skillscan.model.enums.Difficulty;
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
@Document(collection = "question")
public class Question {
    @Id
    private UUID id;
    private String questionText;
    private List<Answer> answers;
    private Difficulty difficulty;
    private UUID technologyId;
    private LocalDateTime createAt;

    public Question() {
    }

    public Question(String questionText, List<Answer> answers, Difficulty difficulty, UUID technologyId) {
        this.id = UUID.randomUUID();
        this.questionText = questionText;
        this.answers = answers;
        this.difficulty = difficulty;
        this.technologyId = technologyId;
        this.createAt = LocalDateTime.now();
    }
}
