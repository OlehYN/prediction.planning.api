package com.course.work.prediction.planning.api.service.application;

import com.course.work.prediction.planning.api.dto.AddExampleDto;

public interface AddExampleValidator {
	boolean isValid(AddExampleDto addExampleDto, String token);
}
