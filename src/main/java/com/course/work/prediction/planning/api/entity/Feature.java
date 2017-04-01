package com.course.work.prediction.planning.api.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "feature")
public class Feature {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feature_id")
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "is_category", nullable = false)
	private boolean isCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model_id", nullable = false)
	private Model featureModel;

	@Column(name = "available_order", nullable = false)
	private Integer availableOrder;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "featureListValueFeature")
	private List<FeatureListValue> featureListValues;

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

	public List<FeatureListValue> getFeatureListValues() {
		return featureListValues;
	}

	public void setFeatureListValues(List<FeatureListValue> featureListValues) {
		this.featureListValues = featureListValues;
	}

	public Model getFeatureModel() {
		return featureModel;
	}

	public void setFeatureModel(Model featureModel) {
		this.featureModel = featureModel;
	}

	public Integer getAvailableOrder() {
		return availableOrder;
	}

	public void setAvailableOrder(Integer availableOrder) {
		this.availableOrder = availableOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Feature other = (Feature) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Feature [id=" + id + ", name=" + name + ", isCategory=" + isCategory + ", availableOrder="
				+ availableOrder + "]";
	}

}
