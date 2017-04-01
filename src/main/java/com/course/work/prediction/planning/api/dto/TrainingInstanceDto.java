package com.course.work.prediction.planning.api.dto;

import java.util.List;

public class TrainingInstanceDto {
	private String outputLabel;
	private List<Object> features;

	public TrainingInstanceDto(String outputLabel, List<Object> features) {
		super();
		this.outputLabel = outputLabel;
		this.features = features;
	}

	public String getOutputLabel() {
		return outputLabel;
	}

	public void setOutputLabel(String outputLabel) {
		this.outputLabel = outputLabel;
	}

	public List<Object> getFeatures() {
		return features;
	}

	public void setFeatures(List<Object> features) {
		this.features = features;
	}

	@Override
	public String toString() {
		return "TrainingInstanceDto [outputLabel=" + outputLabel + ", features=" + features + "]";
	}

}
