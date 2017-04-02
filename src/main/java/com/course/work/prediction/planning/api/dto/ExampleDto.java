package com.course.work.prediction.planning.api.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.course.work.prediction.planning.api.entity.Example;

public class ExampleDto {
	private String outputLabel;
	private Date creationDate;

	private List<ExampleInstanceDto> exampleInstances;

	public ExampleDto(Example example) {
		this.outputLabel = example.getOutputLabel().toString();
		this.creationDate = example.getCreationDate();

		exampleInstances = example.getExampleInstances().stream().map(ExampleInstanceDto::new)
				.collect(Collectors.toList());
	}

	public String getOutputLabel() {
		return outputLabel;
	}

	public void setOutputLabel(String outputLabel) {
		this.outputLabel = outputLabel;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<ExampleInstanceDto> getExampleInstances() {
		return exampleInstances;
	}

	public void setExampleInstances(List<ExampleInstanceDto> exampleInstances) {
		this.exampleInstances = exampleInstances;
	}

	@Override
	public String toString() {
		return "ExampleDto [outputLabel=" + outputLabel + ", creationDate=" + creationDate + ", exampleInstances="
				+ exampleInstances + "]";
	}

}
