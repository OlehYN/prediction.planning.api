package com.course.work.prediction.planning.api.dto;

import com.course.work.prediction.planning.api.entity.FeatureListValue;

public class FeatureListValueDto {
	private Long id;
	private String value;

	public FeatureListValueDto(FeatureListValue featureListValue) {
		super();
		this.id = featureListValue.getFeatureListValueId();
		this.value = featureListValue.getValue();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "FeatureListValueDto [id=" + id + ", value=" + value + "]";
	}

}
