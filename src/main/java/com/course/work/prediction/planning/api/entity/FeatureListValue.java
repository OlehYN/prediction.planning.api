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
@Table(name = "feature_list_value")
public class FeatureListValue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feature_list_value_id")
	private Long featureListValueId;

	@Column(name = "name", nullable = false, length = 100)
	private String value;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feature_id", nullable = false)
	private Feature featureListValueFeature;

	public Long getFeatureListValueId() {
		return featureListValueId;
	}

	public void setFeatureListValueId(Long featureListValueId) {
		this.featureListValueId = featureListValueId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Feature getFeatureListValueFeature() {
		return featureListValueFeature;
	}

	public void setFeatureListValueFeature(Feature featureListValueFeature) {
		this.featureListValueFeature = featureListValueFeature;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((featureListValueId == null) ? 0 : featureListValueId.hashCode());
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
		FeatureListValue other = (FeatureListValue) obj;
		if (featureListValueId == null) {
			if (other.featureListValueId != null)
				return false;
		} else if (!featureListValueId.equals(other.featureListValueId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FeatureListValue [id=" + featureListValueId + ", value=" + value + "]";
	}

}
