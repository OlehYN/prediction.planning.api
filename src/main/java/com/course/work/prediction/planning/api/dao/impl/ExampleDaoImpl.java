package com.course.work.prediction.planning.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.course.work.prediction.planning.api.dao.ExampleDao;
import com.course.work.prediction.planning.api.entity.Example;

@Repository
public class ExampleDaoImpl extends GenericDaoImpl<Example, Long> implements ExampleDao{

	@Override
	protected Class<Example> entityClass() {
		return Example.class;
	}

	@Override
	protected String getEntityName() {
		return "Example";
	}

}
