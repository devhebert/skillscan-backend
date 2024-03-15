package com.skillscan.mapper.technology;

import com.skillscan.dto.technology.TechnologyDTO;
import com.skillscan.model.tecnology.Technology;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TechnologyMapper {
    TechnologyMapper INSTANCE = Mappers.getMapper(TechnologyMapper.class);

    TechnologyDTO technologyToTechnologyDTO(Technology technology);

    Technology technologyDTOToTechnology(TechnologyDTO technologyDTO);
}
