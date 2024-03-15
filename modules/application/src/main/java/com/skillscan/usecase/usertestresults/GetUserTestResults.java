package com.skillscan.usecase.usertestresults;

import com.skillscan.dto.usertestresults.UserTestResultsDTO;

import java.util.List;

public interface GetUserTestResults {
    sealed interface OutputPort permits OutputPort.NoResults, OutputPort.NotAuthorized, OutputPort.Ok {
        public final record Ok(List<UserTestResultsDTO> list) implements OutputPort {}
        public final record NoResults() implements OutputPort {}
        public final record NotAuthorized() implements OutputPort {}
    }

    OutputPort execute();
}
