package com.course.work.prediction.planning.api.dao;

import com.course.work.prediction.planning.api.entity.User;

public interface UserDao extends GenericDao<User, Long> {
	User findByName(String name);
}
