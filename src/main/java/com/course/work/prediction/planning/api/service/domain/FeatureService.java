package com.course.work.prediction.planning.api.service.domain;

import java.util.List;

import com.course.work.prediction.planning.api.dto.CreateFeatureDto;
import com.course.work.prediction.planning.api.entity.Feature;

public interface FeatureService {
	List<Feature> getAll();

	Feature create(Feature obj);

	Feature read(Long key);

	void update(Feature obj);

	boolean delete(Long key);

	Long createFeature(CreateFeatureDto createFeatureDto);
	
	void rename(Long featureId, String name);
	
	void addFeatureValues(Long featureId, List<String> featureValues);
}
