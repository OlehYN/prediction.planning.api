package com.course.work.prediction.planning.api.service.application;

import com.course.work.prediction.planning.api.dto.CreateFeatureDto;

public interface CreateFeatureValidator {
	boolean isValid(CreateFeatureDto createFeatureDto, String token);
}
