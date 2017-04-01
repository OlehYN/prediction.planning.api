package com.course.work.prediction.planning.api.service.domain.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.dao.ExampleDao;
import com.course.work.prediction.planning.api.entity.Example;
import com.course.work.prediction.planning.api.service.domain.ExampleService;

@Service
@Transactional
public class ExampleServiceImpl implements ExampleService {

	@Autowired
	private ExampleDao exampleDao;

	@Override
	public List<Example> getAll() {
		return exampleDao.getAll();
	}

	@Override
	public Example create(Example obj) {
		return exampleDao.create(obj);
	}

	@Override
	public Example read(Long key) {
		return exampleDao.read(key);
	}

	@Override
	public void update(Example obj) {
		exampleDao.update(obj);
	}

	@Override
	public boolean delete(Long key) {
		return exampleDao.delete(key);
	}

}
