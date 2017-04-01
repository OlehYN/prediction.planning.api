package com.course.work.prediction.planning.api.service.domain.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.dao.FeatureDao;
import com.course.work.prediction.planning.api.entity.Feature;
import com.course.work.prediction.planning.api.service.domain.FeatureService;

@Service
@Transactional
public class FeatureServiceImpl implements FeatureService {

	@Autowired
	private FeatureDao featureDao;

	@Override
	public List<Feature> getAll() {
		return featureDao.getAll();
	}

	@Override
	public Feature create(Feature obj) {
		return featureDao.create(obj);
	}

	@Override
	public Feature read(Long key) {
		return featureDao.read(key);
	}

	@Override
	public void update(Feature obj) {
		featureDao.update(obj);
	}

	@Override
	public boolean delete(Long key) {
		return featureDao.delete(key);
	}

}
