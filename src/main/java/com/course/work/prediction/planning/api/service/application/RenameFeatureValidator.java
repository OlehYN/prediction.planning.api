package com.course.work.prediction.planning.api.service.application;

public interface RenameFeatureValidator {
	boolean isValid(String token, Long featureId, String name);
}
