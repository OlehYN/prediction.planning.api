package com.course.work.prediction.planning.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.course.work.prediction.planning.api.dao.ExampleInstanceDao;
import com.course.work.prediction.planning.api.entity.ExampleInstance;

@Repository
public class ExampleInstanceDaoImpl extends GenericDaoImpl<ExampleInstance, Long> implements ExampleInstanceDao{

	@Override
	protected Class<ExampleInstance> entityClass() {
		return ExampleInstance.class;
	}

	@Override
	protected String getEntityName() {
		return "example_instance";
	}

}
