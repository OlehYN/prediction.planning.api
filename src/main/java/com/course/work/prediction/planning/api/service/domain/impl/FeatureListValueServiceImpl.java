package com.course.work.prediction.planning.api.service.domain.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.dao.FeatureListValueDao;
import com.course.work.prediction.planning.api.entity.FeatureListValue;
import com.course.work.prediction.planning.api.service.domain.FeatureListValueService;

@Service
@Transactional
public class FeatureListValueServiceImpl implements FeatureListValueService {

	@Autowired
	private FeatureListValueDao featureListValueDao;

	@Override
	public List<FeatureListValue> getAll() {
		return featureListValueDao.getAll();
	}

	@Override
	public FeatureListValue create(FeatureListValue obj) {
		return featureListValueDao.create(obj);
	}

	@Override
	public FeatureListValue read(Long key) {
		return featureListValueDao.read(key);
	}

	@Override
	public void update(FeatureListValue obj) {
		featureListValueDao.update(obj);
	}

	@Override
	public boolean delete(Long key) {
		return featureListValueDao.delete(key);
	}

}
