package com.skillscan.mapper.quizattempt;

import com.skillscan.dto.quizAttempt.QuizAttemptDTO;
import com.skillscan.model.quizattempt.QuizAttempt;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuizAttemptMapper {
    QuizAttemptMapper INSTANCE = Mappers.getMapper(QuizAttemptMapper.class);

    QuizAttemptDTO quizAttemptToQuizAttemptDTO(QuizAttempt quizAttempt);

    QuizAttempt quizAttemptDTOToQuizAttempt(QuizAttemptDTO quizAttemptDTO);
}
