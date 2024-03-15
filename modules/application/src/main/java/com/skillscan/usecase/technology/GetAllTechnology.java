package com.skillscan.usecase.technology;

import com.skillscan.dto.technology.TechnologyDTO;

import java.util.List;

public interface GetAllTechnology {
    sealed interface OutputPort permits OutputPort.NoResults, OutputPort.NotAuthorized, OutputPort.Ok {
        public final record Ok(List<TechnologyDTO> list) implements OutputPort {}
        public final record NoResults() implements OutputPort {}
        public final record NotAuthorized() implements OutputPort {}
    }

    OutputPort execute();
}
