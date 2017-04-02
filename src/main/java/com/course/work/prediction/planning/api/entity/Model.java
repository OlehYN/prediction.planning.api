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
@Table(name = "model")
public class Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "model_id")
	private Long modelId;

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "external_id", nullable = false, length = 100, unique = true)
	private String externalId;

	@Column(name = "available_order", nullable = false)
	private Integer availableOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "featureModel", cascade = CascadeType.ALL)
	private List<Feature> features;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exampleModel", cascade = CascadeType.ALL)
	private List<Example> examples;

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Integer getAvailableOrder() {
		return availableOrder;
	}

	public void setAvailableOrder(Integer availableOrder) {
		this.availableOrder = availableOrder;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}

	public List<Example> getExamples() {
		return examples;
	}

	public void setExamples(List<Example> examples) {
		this.examples = examples;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modelId == null) ? 0 : modelId.hashCode());
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
		Model other = (Model) obj;
		if (modelId == null) {
			if (other.modelId != null)
				return false;
		} else if (!modelId.equals(other.modelId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Model [modelId=" + modelId + ", creationDate=" + creationDate + ", name=" + name + ", externalId="
				+ externalId + ", availableOrder=" + availableOrder + "]";
	}

}
