package com.course.work.prediction.planning.api.service.domain.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.dao.ModelDao;
import com.course.work.prediction.planning.api.entity.Model;
import com.course.work.prediction.planning.api.entity.User;
import com.course.work.prediction.planning.api.service.domain.ModelService;
import com.course.work.prediction.planning.api.service.domain.UserService;
import com.course.work.prediction.planning.api.service.infrastructure.GooglePredictionApi;

@Service
@Transactional
public class ModelServiceImpl implements ModelService {

	@Autowired
	private ModelDao modelDao;
	
	@Autowired
	private String projectName;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GooglePredictionApi googlePredictionApi;

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

	@Override
	public Model create(String name, Long userId) throws IOException {
		User user = userService.read(userId);
		
		Model model = new Model();
		model.setAvailableOrder(0);
		model.setCreationDate(new Date());
		model.setExternalId(user.getLogin() + "#" + name);
		model.setName(name);
		model.setUser(user);
		
		modelDao.create(model);
		
		googlePredictionApi.insert(projectName, name);
	
		return model;
	}

}
