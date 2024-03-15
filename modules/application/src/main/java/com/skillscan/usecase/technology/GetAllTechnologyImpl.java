package com.skillscan.usecase.technology;

import com.skillscan.mapper.technology.TechnologyMapper;
import com.skillscan.model.tecnology.Technology;
import com.skillscan.repository.TechnologyRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllTechnologyImpl implements GetAllTechnology {
    private final TechnologyRepository technologyRepository;

    public GetAllTechnologyImpl(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @Transactional(readOnly = true)
    public OutputPort execute() {
        try {
            List<Technology> technology = technologyRepository.all();

            if (technology.isEmpty()) return new OutputPort.NoResults();

            return new OutputPort.Ok(technology.stream()
                    .map(TechnologyMapper.INSTANCE::technologyToTechnologyDTO)
                    .collect(Collectors.toList()));

        } catch (Exception e) {
            return new OutputPort.NotAuthorized();
        }
    }
}
