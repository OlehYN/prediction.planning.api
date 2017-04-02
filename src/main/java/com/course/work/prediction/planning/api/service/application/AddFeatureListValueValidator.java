package com.course.work.prediction.planning.api.service.application;

import java.util.List;

public interface AddFeatureListValueValidator {
	boolean isValid(Long featureId, String token, List<String> values);
}
