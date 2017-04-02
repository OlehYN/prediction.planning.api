package com.course.work.prediction.planning.api.service.domain;

import java.util.List;

import com.course.work.prediction.planning.api.dto.AddExampleDto;
import com.course.work.prediction.planning.api.entity.Example;

public interface ExampleService {
	List<Example> getAll();

	Example create(Example obj);

	Example read(Long key);

	void update(Example obj);

	boolean delete(Long key);

	Example create(AddExampleDto addExampleDto);
	
	List<Example> unusedExamples(Long modelId);
}
