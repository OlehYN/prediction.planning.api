package com.course.work.prediction.planning.api.service.application;

import java.util.List;

import com.course.work.prediction.planning.api.dto.PredictExampleInstanceDto;

public interface PredictValidator {
	boolean isValid(List<PredictExampleInstanceDto> instances, Long modelId, String token);
}
