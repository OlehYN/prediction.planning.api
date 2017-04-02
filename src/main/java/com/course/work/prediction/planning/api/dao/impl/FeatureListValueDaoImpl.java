package com.course.work.prediction.planning.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.course.work.prediction.planning.api.dao.FeatureListValueDao;
import com.course.work.prediction.planning.api.entity.FeatureListValue;

@Repository
public class FeatureListValueDaoImpl extends GenericDaoImpl<FeatureListValue, Long> implements FeatureListValueDao{

	@Override
	protected Class<FeatureListValue> entityClass() {
		return FeatureListValue.class;
	}

	@Override
	protected String getEntityName() {
		return "FeatureListValue";
	}

}
