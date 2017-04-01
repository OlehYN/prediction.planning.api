package com.course.work.prediction.planning.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.course.work.prediction.planning.api.dao.ModelDao;
import com.course.work.prediction.planning.api.entity.Model;

@Repository
public class ModelDaoImpl extends GenericDaoImpl<Model, Long> implements ModelDao{

	@Override
	protected Class<Model> entityClass() {
		return Model.class;
	}

	@Override
	protected String getEntityName() {
		return "model";
	}

}
