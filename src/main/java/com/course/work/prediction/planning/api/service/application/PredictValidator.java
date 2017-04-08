package com.course.work.prediction.planning.api.service.application;

import java.util.List;

import com.course.work.prediction.planning.api.dto.AddExampleInstanceDto;

public interface PredictValidator {
	boolean isValid(List<AddExampleInstanceDto> instances, Long modelId, String token);
}
