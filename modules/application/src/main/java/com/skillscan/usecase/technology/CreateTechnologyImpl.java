package com.skillscan.usecase.technology;

import com.skillscan.exception.technology.ErrorMessages;
import com.skillscan.model.tecnology.Technology;
import com.skillscan.repository.TechnologyRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

public class CreateTechnologyImpl implements CreateTechnology {
    private final TechnologyRepository technologyRepository;
    private final Logger logger = LoggerFactory.getLogger(CreateTechnologyImpl.class);

    public CreateTechnologyImpl(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @Transactional
    @CacheEvict(value = "technology", allEntries = true)
    public OutputPort execute(InputPort inputPort) {
        try {
            if (inputPort == null) {
                return new OutputPort.Error(ErrorMessages.INVALID_INPUT);
            }

            if(this.technologyRepository.findByName(inputPort.name()).isPresent()) {
                return new OutputPort.Error(ErrorMessages.TECHNOLOGY_ALREADY_EXISTS);
            }

            OutputPort validationOutput = validateTechnology(inputPort);
            if (validationOutput != null) return validationOutput;

            Technology saved = this.technologyRepository.save(new Technology(inputPort.category(), inputPort.name()));

            return new OutputPort.Ok(saved.getId());
        } catch (Exception e) {
            logger.error("Error creating technology", e);
            return new OutputPort.Error(ErrorMessages.INTERNAL_ERROR);
        }
    }

    private OutputPort validateTechnology(InputPort technology) {
        if (technology.name() == null || technology.name().trim().isEmpty()) {
            return new OutputPort.Error("O nome não pode ser nulo ou vazio.");
        }

        if (technology.category() == null) {
            return new OutputPort.Error("A categoria não pode ser nula");
        }

        return null;
    }
}

