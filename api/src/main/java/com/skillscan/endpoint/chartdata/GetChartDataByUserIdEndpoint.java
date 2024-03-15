package com.skillscan.endpoint.chartdata;

import com.skillscan.endpoint.BaseChartDataEndpoint;
import com.skillscan.usecase.chartdata.GetChartDataByUserId;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GetChartDataByUserIdEndpoint extends BaseChartDataEndpoint {
    private final GetChartDataByUserId useCase;

    public GetChartDataByUserIdEndpoint(GetChartDataByUserId useCase) {
        this.useCase = useCase;
    }

    @Operation(summary = "Get chart data")
    @GetMapping("/{id}")
    public ResponseEntity<?> execute(@PathVariable("id") UUID id) {
        GetChartDataByUserId.OutputPort result = this.useCase.execute(new GetChartDataByUserId.InputPort(id));

        return switch (result) {
            case GetChartDataByUserId.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case GetChartDataByUserId.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
