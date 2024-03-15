package com.skillscan.usecase.technology;

import com.skillscan.exception.technology.ErrorMessages;
import com.skillscan.mapper.technology.TechnologyMapper;
import com.skillscan.repository.TechnologyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class GetTechnologyByIdImpl implements GetTechnologyById {
    private final TechnologyRepository technologyRepository;
    private final Logger logger = LoggerFactory.getLogger(GetTechnologyByIdImpl.class);

    public GetTechnologyByIdImpl(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @Transactional(readOnly = true)
    public OutputPort execute(InputPort input) {
        try {
            if (input.id() == null) return new OutputPort.Error(ErrorMessages.INVALID_INPUT);

            Optional<OutputPort> result = this.technologyRepository.findById(input.id())
                    .map(technology -> new OutputPort.Ok(TechnologyMapper.INSTANCE.technologyToTechnologyDTO(technology)));

            return result.orElse(new OutputPort.Error(ErrorMessages.TECHNOLOGY_NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error get technology by id", e);
            return new OutputPort.Error(ErrorMessages.INTERNAL_ERROR);
        }
    }
}
