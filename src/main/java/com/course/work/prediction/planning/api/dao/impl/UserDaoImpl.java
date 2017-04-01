package com.course.work.prediction.planning.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.course.work.prediction.planning.api.dao.UserDao;
import com.course.work.prediction.planning.api.entity.User;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao{

	@Override
	protected Class<User> entityClass() {
		return User.class;
	}

	@Override
	protected String getEntityName() {
		return "user";
	}

}
