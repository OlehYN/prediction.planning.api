package com.course.work.prediction.planning.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "example_instance")
public class ExampleInstance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "example_instance_id")
	private Long exampleInstanceId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feature_id", nullable = false)
	private Feature exampleInstanceFeature;

	@Column(name = "value", nullable = true)
	private Double value;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feature_list_value_id", nullable = true)
	private FeatureListValue exampleInstanceFeatureListValue;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "example_id", nullable = false)
	private Example exampleInstanceExample;

	public Long getExampleInstanceId() {
		return exampleInstanceId;
	}

	public void setExampleInstanceId(Long exampleInstanceId) {
		this.exampleInstanceId = exampleInstanceId;
	}

	public Feature getExampleInstanceFeature() {
		return exampleInstanceFeature;
	}

	public void setExampleInstanceFeature(Feature exampleInstanceFeature) {
		this.exampleInstanceFeature = exampleInstanceFeature;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public FeatureListValue getExampleInstanceFeatureListValue() {
		return exampleInstanceFeatureListValue;
	}

	public void setExampleInstanceFeatureListValue(FeatureListValue exampleInstanceFeatureListValue) {
		this.exampleInstanceFeatureListValue = exampleInstanceFeatureListValue;
	}

	public Example getExampleInstanceExample() {
		return exampleInstanceExample;
	}

	public void setExampleInstanceExample(Example exampleInstanceExample) {
		this.exampleInstanceExample = exampleInstanceExample;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exampleInstanceId == null) ? 0 : exampleInstanceId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExampleInstance other = (ExampleInstance) obj;
		if (exampleInstanceId == null) {
			if (other.exampleInstanceId != null)
				return false;
		} else if (!exampleInstanceId.equals(other.exampleInstanceId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ExampleInstance [id=" + exampleInstanceId + ", exampleInstanceFeature=" + exampleInstanceFeature + ", value=" + value
				+ ", exampleInstanceFeatureListValue=" + exampleInstanceFeatureListValue + "]";
	}

}
