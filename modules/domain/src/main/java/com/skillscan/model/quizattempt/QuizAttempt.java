package com.skillscan.model.quizattempt;

import com.skillscan.model.coursesuggestion.CourseSuggestion;
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
@Document(collection = "quiz-attempt")
public class QuizAttempt {
    @Id
    private UUID id;
    private UUID userId;
    private UUID technologyId;
    private List<QuestionResponse> questionResponses;
    private Integer score;
    private List<CourseSuggestion> courseSuggestions;;
    private LocalDateTime testDate;

    public QuizAttempt() {
    }

    public QuizAttempt(UUID userId, UUID technologyId, List<QuestionResponse> questionResponses, Integer score, List<CourseSuggestion> courseSuggestions) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.technologyId = technologyId;
        this.questionResponses = questionResponses;
        this.score = score;
        this.courseSuggestions  = courseSuggestions;
        this.testDate = LocalDateTime.now();
    }
}
