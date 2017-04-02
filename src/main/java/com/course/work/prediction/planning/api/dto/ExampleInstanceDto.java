package com.course.work.prediction.planning.api.dto;

import com.course.work.prediction.planning.api.entity.ExampleInstance;

public class ExampleInstanceDto {
	private String name;
	private String value;

	public ExampleInstanceDto(ExampleInstance exampleInstance) {
		this.name = exampleInstance.getExampleInstanceFeature().getName();

		if (exampleInstance.getExampleInstanceFeature().isCategory())
			this.value = exampleInstance.getExampleInstanceFeatureListValue().getValue();
		else
			this.value = exampleInstance.getValue().toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ExampleInstanceDto [name=" + name + ", value=" + value + "]";
	}

}
