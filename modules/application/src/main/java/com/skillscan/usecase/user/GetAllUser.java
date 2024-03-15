package com.skillscan.usecase.user;

import com.skillscan.dto.user.UserDTO;

import java.util.List;

public interface GetAllUser {
    sealed interface OutputPort permits OutputPort.NoResults, OutputPort.NotAuthorized, OutputPort.Ok {
        public final record Ok(List<UserDTO> list) implements OutputPort {}
        public final record NoResults() implements OutputPort {}
        public final record NotAuthorized() implements OutputPort {}
    }

    OutputPort execute();
}
