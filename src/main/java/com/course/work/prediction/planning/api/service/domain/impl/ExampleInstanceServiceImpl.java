package com.course.work.prediction.planning.api.service.domain.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.dao.ExampleInstanceDao;
import com.course.work.prediction.planning.api.entity.ExampleInstance;
import com.course.work.prediction.planning.api.service.domain.ExampleInstanceService;

@Service
@Transactional
public class ExampleInstanceServiceImpl implements ExampleInstanceService {

	@Autowired
	private ExampleInstanceDao exampleInstanceDao;

	@Override
	public List<ExampleInstance> getAll() {
		return exampleInstanceDao.getAll();
	}

	@Override
	public ExampleInstance create(ExampleInstance obj) {
		return exampleInstanceDao.create(obj);
	}

	@Override
	public ExampleInstance read(Long key) {
		return exampleInstanceDao.read(key);
	}

	@Override
	public void update(ExampleInstance obj) {
		exampleInstanceDao.update(obj);
	}

	@Override
	public boolean delete(Long key) {
		return exampleInstanceDao.delete(key);
	}

}
