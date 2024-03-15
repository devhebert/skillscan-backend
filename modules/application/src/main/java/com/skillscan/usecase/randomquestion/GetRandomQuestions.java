package com.skillscan.usecase.randomquestion;

import com.skillscan.dto.randomquestion.RandomQuestionDTO;

import java.util.List;
import java.util.UUID;

public interface GetRandomQuestions {
    record InputPort(UUID userId, UUID technologyId) {}

    sealed interface OutputPort permits OutputPort.NoResults, OutputPort.NotAuthorized, OutputPort.Ok, OutputPort.Error {
        public final record Ok(List<RandomQuestionDTO> list) implements OutputPort {}
        public final record NoResults() implements OutputPort {}
        public final record NotAuthorized() implements OutputPort {}
        public final record Error(String message) implements OutputPort {}
    }

    OutputPort execute(InputPort inputPort);
}
