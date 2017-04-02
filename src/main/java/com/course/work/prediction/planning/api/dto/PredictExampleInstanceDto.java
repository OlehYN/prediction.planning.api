package com.course.work.prediction.planning.api.dto;

public class PredictExampleInstanceDto {
	private Long id;
	private Long listValueId;
	private Double value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getListValueId() {
		return listValueId;
	}

	public void setListValueId(Long listValueId) {
		this.listValueId = listValueId;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "AddExampleInstanceDto [id=" + id + ", listValueId=" + listValueId + ", value=" + value + "]";
	}

}
