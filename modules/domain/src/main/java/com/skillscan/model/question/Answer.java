package com.skillscan.model.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public  class Answer {
    private String answerText;
    private Boolean isCorrect;

    public Answer() {
    }

    public Answer(String answerText, boolean isCorrect) {
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }
}


