package com.course.work.prediction.planning.api.service.domain.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.dao.UserDao;
import com.course.work.prediction.planning.api.entity.User;
import com.course.work.prediction.planning.api.service.domain.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}

	@Override
	public User create(User obj) {
		return userDao.create(obj);
	}

	@Override
	public User read(Long key) {
		return userDao.read(key);
	}

	@Override
	public void update(User obj) {
		userDao.update(obj);
	}

	@Override
	public boolean delete(Long key) {
		return userDao.delete(key);
	}

	@Override
	public User findByName(String name) {
		return userDao.findByName(name);
	}

}
