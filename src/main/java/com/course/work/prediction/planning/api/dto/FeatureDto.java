package com.course.work.prediction.planning.api.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.course.work.prediction.planning.api.entity.Feature;

public class FeatureDto {
	private Long id;
	private String name;
	private boolean isCategory;

	public FeatureDto(Feature feature) {
		id = feature.getFeatureId();
		name = feature.getName();
		isCategory = feature.isCategory();

		if (isCategory)
			values = feature.getFeatureListValues().stream().map(FeatureListValueDto::new).collect(Collectors.toList());
	}

	private List<FeatureListValueDto> values;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCategory() {
		return isCategory;
	}

	public void setCategory(boolean isCategory) {
		this.isCategory = isCategory;
	}

	public List<FeatureListValueDto> getValues() {
		return values;
	}

	public void setValues(List<FeatureListValueDto> values) {
		this.values = values;
	}

}
