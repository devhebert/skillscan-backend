package com.skillscan.usecase.question;

import com.skillscan.dto.question.QuestionDTO;

import java.util.List;

public interface GetAllQuestion {
    sealed interface OutputPort permits OutputPort.NoResults, OutputPort.NotAuthorized, OutputPort.Ok {

        public final record Ok(List<QuestionDTO> list) implements OutputPort {}
        public final record NoResults() implements OutputPort {}
        public final record NotAuthorized() implements OutputPort {}
    }

    OutputPort execute();
}
