package com.skillscan.mapper.chartdata;

import com.skillscan.dto.chartdata.ChartDataDTO;
import com.skillscan.model.quizattempt.QuizAttempt;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChartDataMapper {
ChartDataMapper INSTANCE = Mappers.getMapper(ChartDataMapper.class);
     default ChartDataDTO quizAttemptToChartDataDTO(QuizAttempt quizAttempt, String technologyName) {
        return new ChartDataDTO(
                quizAttempt.getUserId(),
                technologyName,
                quizAttempt.getScore(),
                quizAttempt.getTestDate()
        );
    }

}


