package com.skillscan.usecase.chartdata;

import com.skillscan.dto.chartdata.ChartDataDTO;

import java.util.List;
import java.util.UUID;

public interface GetChartDataByUserId {
    public record InputPort(UUID userId) {}

    public sealed interface OutputPort {
        public final record Ok(List<ChartDataDTO> chartData) implements OutputPort {}
        public final record Error(String message) implements OutputPort {}
    }

    public OutputPort execute(InputPort input);
}
