package com.course.work.prediction.planning.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.course.work.prediction.planning.api.dao.FeatureDao;
import com.course.work.prediction.planning.api.entity.Feature;

@Repository
public class FeatureDaoImpl extends GenericDaoImpl<Feature, Long> implements FeatureDao{

	@Override
	protected Class<Feature> entityClass() {
		return Feature.class;
	}

	@Override
	protected String getEntityName() {
		return "Feature";
	}

}
