package com.skillscan.usecase.chartdata;

import com.skillscan.dto.chartdata.ChartDataDTO;
import com.skillscan.exception.chartdata.ErrorMessages;
import com.skillscan.mapper.chartdata.ChartDataMapper;
import com.skillscan.model.quizattempt.QuizAttempt;
import com.skillscan.model.tecnology.Technology;
import com.skillscan.repository.QuizAttemptRepository;
import com.skillscan.repository.TechnologyRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class GetChartDataByUserIdImpl implements GetChartDataByUserId {
    private final QuizAttemptRepository quizAttemptRepository;
    private final TechnologyRepository technologyRepository;
    private final Logger logger = LoggerFactory.getLogger(GetChartDataByUserIdImpl.class);

    public GetChartDataByUserIdImpl(QuizAttemptRepository quizAttemptRepository, TechnologyRepository technologyRepository) {
        this.quizAttemptRepository = quizAttemptRepository;
        this.technologyRepository = technologyRepository;
    }

    @Transactional(readOnly = true)
    public GetChartDataByUserId.OutputPort execute(GetChartDataByUserId.InputPort input) {
        try {
            if (input.userId() == null) return new GetChartDataByUserId.OutputPort.Error(ErrorMessages.INVALID_INPUT);

            List<QuizAttempt> quizAttempts = this.quizAttemptRepository.findByUserId(input.userId());

            if (quizAttempts.isEmpty()) return new GetChartDataByUserId.OutputPort.Error(ErrorMessages.CHART_NOT_FOUND);

            List<ChartDataDTO> chartDataDTOs = quizAttempts.stream().map(quizAttempt -> {
                String technologyName = this.technologyRepository.findById(quizAttempt.getTechnologyId())
                        .map(Technology::getName)
                        .orElseThrow(() -> new RuntimeException("Technology not found"));
                return ChartDataMapper.INSTANCE.quizAttemptToChartDataDTO(quizAttempt, technologyName);
            }).collect(Collectors.toList());

            return new GetChartDataByUserId.OutputPort.Ok(chartDataDTOs);
        } catch (Exception e) {
            logger.error("Error get chart data by id", e);
            return new GetChartDataByUserId.OutputPort.Error(ErrorMessages.INTERNAL_ERROR);
        }
    }
}
