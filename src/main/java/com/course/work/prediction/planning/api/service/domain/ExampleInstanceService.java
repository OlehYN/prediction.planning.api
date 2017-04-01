package com.course.work.prediction.planning.api.service.domain;

import java.util.List;

import com.course.work.prediction.planning.api.entity.ExampleInstance;

public interface ExampleInstanceService {
	List<ExampleInstance> getAll();

	ExampleInstance create(ExampleInstance obj);

	ExampleInstance read(Long key);

	void update(ExampleInstance obj);

	boolean delete(Long key);

}
