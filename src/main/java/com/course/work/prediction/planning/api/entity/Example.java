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
@Table(name = "example")
public class Example {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "example_id")
	private Long exampleId;

	@Column(name = "output_label", nullable = false)
	private Integer outputLabel;

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@Column(name = "used_in_prediction_api", nullable = false)
	private boolean usedInPredictionApi;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exampleInstanceExample", cascade = CascadeType.ALL)
	private List<ExampleInstance> exampleInstances;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model_id", nullable = false)
	private Model exampleModel;

	public Long getExampleId() {
		return exampleId;
	}

	public void setExampleId(Long id) {
		this.exampleId = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<ExampleInstance> getExampleInstances() {
		return exampleInstances;
	}

	public void setExampleInstances(List<ExampleInstance> exampleInstances) {
		this.exampleInstances = exampleInstances;
	}

	public Model getExampleModel() {
		return exampleModel;
	}

	public void setExampleModel(Model exampleModel) {
		this.exampleModel = exampleModel;
	}

	public Integer getOutputLabel() {
		return outputLabel;
	}

	public void setOutputLabel(Integer outputLabel) {
		this.outputLabel = outputLabel;
	}

	public boolean isUsedInPredictionApi() {
		return usedInPredictionApi;
	}

	public void setUsedInPredictionApi(boolean usedInPredictionApi) {
		this.usedInPredictionApi = usedInPredictionApi;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exampleId == null) ? 0 : exampleId.hashCode());
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
		Example other = (Example) obj;
		if (exampleId == null) {
			if (other.exampleId != null)
				return false;
		} else if (!exampleId.equals(other.exampleId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Example [id=" + exampleId + ", outputLabel=" + outputLabel + ", creationDate=" + creationDate
				+ ", usedInPredictionApi=" + usedInPredictionApi + ", exampleInstances=" + exampleInstances + "]";
	}

}
