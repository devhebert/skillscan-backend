package com.skillscan.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value = "*")
@Tag(name = "ChartData", description = "The ChartData API")
@RestController
@RequestMapping("/api/v1/chart-data")
public abstract class BaseChartDataEndpoint {}
