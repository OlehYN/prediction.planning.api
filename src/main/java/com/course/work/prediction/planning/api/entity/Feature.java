package com.course.work.prediction.planning.api.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
	private Long featureId;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "is_category", nullable = false)
	private boolean isCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model_id", nullable = false)
	private Model featureModel;

	@Column(name = "feature_order", nullable = false)
	private Integer order;

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "featureListValueFeature", cascade = CascadeType.ALL)
	private List<FeatureListValue> featureListValues;

	public Long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Long id) {
		this.featureId = id;
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

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((featureId == null) ? 0 : featureId.hashCode());
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
		if (featureId == null) {
			if (other.featureId != null)
				return false;
		} else if (!featureId.equals(other.featureId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Feature [featureId=" + featureId + ", name=" + name + ", isCategory=" + isCategory + ", order=" + order
				+ ", creationDate=" + creationDate + "]";
	}

}
