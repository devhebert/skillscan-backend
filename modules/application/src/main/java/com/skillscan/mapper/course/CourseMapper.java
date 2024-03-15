package com.skillscan.mapper.course;

import com.skillscan.dto.course.CourseDTO;
import com.skillscan.model.course.Course;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    CourseDTO courseToCourseDTO(Course course);

    Course courseDTOToCourse(CourseDTO courseDTO);
}
