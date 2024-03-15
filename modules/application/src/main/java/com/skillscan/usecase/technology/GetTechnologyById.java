package com.skillscan.usecase.technology;

import com.skillscan.dto.technology.TechnologyDTO;

import java.util.UUID;

public interface GetTechnologyById {
    public record InputPort(UUID id) {}

    public sealed interface OutputPort {
        public final record Ok(TechnologyDTO technology) implements OutputPort {}
        public final record Error(String message) implements OutputPort {}
    }

    public OutputPort execute(InputPort input);
}
