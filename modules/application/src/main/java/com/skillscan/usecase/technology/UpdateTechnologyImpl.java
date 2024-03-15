package com.skillscan.usecase.technology;

import com.skillscan.exception.technology.ErrorMessages;
import com.skillscan.model.tecnology.Technology;
import com.skillscan.repository.TechnologyRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

public class UpdateTechnologyImpl implements UpdateTechnology{
    private final TechnologyRepository technologyRepository;
    private final Logger logger = LoggerFactory.getLogger(UpdateTechnologyImpl.class);

    public UpdateTechnologyImpl(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @Transactional
    @CacheEvict(value = "technology", allEntries = true)
    public OutputPort execute(InputPort inputPort) {
        try {
            if (inputPort == null) return new OutputPort.Error(ErrorMessages.INVALID_INPUT);

            return this.technologyRepository.findById(inputPort.id())
                    .map(technology -> {
                        OutputPort validationOutput = this.validateInput(inputPort);
                        if (validationOutput != null) return validationOutput;

                        this.updateFields(technology, inputPort);

                        Technology updatedTechnology = this.technologyRepository.save(technology);

                        return new OutputPort.Ok(updatedTechnology.getId());
                    })
                    .orElse(new OutputPort.NotFound(ErrorMessages.TECHNOLOGY_NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error updating technology", e);
            return new OutputPort.Error(ErrorMessages.INTERNAL_ERROR);
        }
    }

    private OutputPort validateInput(InputPort inputPort) {
        if (inputPort.name() != null && inputPort.name().length() < 1) {
            return new OutputPort.Error("O nome deve ter pelo menos 1 caractere.");
        }

        if ( inputPort.category() == null) {
            return new OutputPort.Error("A categoria não pode ser nula.");
        }

        return null;
    }

    private void updateFields(Technology technology, InputPort inputPort) {
        if (inputPort.name() != null) {
            technology.setName(inputPort.name());
        }

        if (inputPort.category() != null) {
            technology.setCategory(inputPort.category());
        }
    }
}
