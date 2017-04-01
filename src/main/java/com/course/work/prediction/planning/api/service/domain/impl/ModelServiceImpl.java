package com.course.work.prediction.planning.api.service.domain.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.dao.ModelDao;
import com.course.work.prediction.planning.api.entity.Model;
import com.course.work.prediction.planning.api.service.domain.ModelService;

@Service
@Transactional
public class ModelServiceImpl implements ModelService {

	@Autowired
	private ModelDao modelDao;

	@Override
	public List<Model> getAll() {
		return modelDao.getAll();
	}

	@Override
	public Model create(Model obj) {
		return modelDao.create(obj);
	}

	@Override
	public Model read(Long key) {
		return modelDao.read(key);
	}

	@Override
	public void update(Model obj) {
		modelDao.update(obj);
	}

	@Override
	public boolean delete(Long key) {
		return modelDao.delete(key);
	}

}
