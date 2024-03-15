package com.skillscan.usecase.technology;

import com.skillscan.repository.TechnologyRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class DeleteTechnologyImpl implements DeleteTechnology {
    private final TechnologyRepository technologyRepository;

    public DeleteTechnologyImpl(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @Transactional
    @CacheEvict(value = "technology", allEntries = true)
    public OutputPort execute(UUID id) {
        try {
            if (this.technologyRepository.findById(id).isEmpty()) return new OutputPort.NotFound();

            this.technologyRepository.deleteById(id);
            return new OutputPort.Ok();
        } catch (Exception e) {
            return new OutputPort.Error(e.getMessage());
        }
    }
}
