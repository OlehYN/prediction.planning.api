package com.course.work.prediction.planning.api.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.course.work.prediction.planning.api.dao.UserDao;
import com.course.work.prediction.planning.api.entity.User;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

	@Override
	protected Class<User> entityClass() {
		return User.class;
	}

	@Override
	protected String getEntityName() {
		return "User";
	}

	@Override
	public User findByName(String name) {
		List<User> users = em.createQuery("select u from User u where u.login = :login", User.class)
				.setParameter("login", name).getResultList();
		if (users == null || users.size() == 0)
			return null;
		return users.get(0);
	}

}
