package com.course.work.prediction.planning.api.dao;

import java.util.List;

import com.course.work.prediction.planning.api.entity.Example;

public interface ExampleDao extends GenericDao<Example, Long> {
	
	List<Example> unusedExamples(Long modelId);
}
