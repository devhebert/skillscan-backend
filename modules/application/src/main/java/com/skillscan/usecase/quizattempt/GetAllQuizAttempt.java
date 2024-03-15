package com.skillscan.usecase.quizattempt;

import com.skillscan.dto.quizAttempt.QuizAttemptDTO;

import java.util.List;

public interface GetAllQuizAttempt {
    sealed interface OutputPort permits OutputPort.NoResults, OutputPort.NotAuthorized, OutputPort.Ok {
        public final record Ok(List<QuizAttemptDTO> list) implements OutputPort {}
        public final record NoResults() implements OutputPort {}
        public final record NotAuthorized() implements OutputPort {}
    }

    OutputPort execute();
}
