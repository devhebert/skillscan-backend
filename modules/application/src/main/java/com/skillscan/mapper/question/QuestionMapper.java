package com.skillscan.mapper.question;

import com.skillscan.dto.question.QuestionDTO;
import com.skillscan.model.question.Question;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    QuestionDTO questionToQuestionDTO(Question question);

    Question questionDTOToQuestion(QuestionDTO questionDTO);
}
