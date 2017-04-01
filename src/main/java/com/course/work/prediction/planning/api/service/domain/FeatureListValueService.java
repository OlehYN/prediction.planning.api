package com.course.work.prediction.planning.api.service.domain;

import java.util.List;

import com.course.work.prediction.planning.api.entity.FeatureListValue;

public interface FeatureListValueService {
	List<FeatureListValue> getAll();

	FeatureListValue create(FeatureListValue obj);

	FeatureListValue read(Long key);

	void update(FeatureListValue obj);

	boolean delete(Long key);

}
