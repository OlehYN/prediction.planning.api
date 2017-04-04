package com.course.work.prediction.planning.api.dto;

import java.util.Date;

import com.course.work.prediction.planning.api.entity.Model;

public class ModelInfoDto {
	private Long id;
	private String name;
	private Integer examplesQuantity;
	private Date creationDate;

	public ModelInfoDto(Model model) {
		id = model.getModelId();
		name = model.getName();

		if (model.getExamples() != null)
			examplesQuantity = model.getExamples().size();
		else
			examplesQuantity = 0;
		creationDate = model.getCreationDate();
	}

	public ModelInfoDto(Long id, String name, Integer examplesQuantity) {
		super();
		this.id = id;
		this.name = name;
		this.examplesQuantity = examplesQuantity;
	}

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

	public Integer getExamplesQuantity() {
		return examplesQuantity;
	}

	public void setExamplesQuantity(Integer examplesQuantity) {
		this.examplesQuantity = examplesQuantity;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "ModelInfoDto [id=" + id + ", name=" + name + ", examplesQuantity=" + examplesQuantity
				+ ", creationDate=" + creationDate + "]";
	}

}
