package com.course.work.prediction.planning.api.service.domain;

import java.util.List;

import com.course.work.prediction.planning.api.entity.User;

public interface UserService {
	List<User> getAll();

	User create(User obj);

	User read(Long key);

	void update(User obj);

	boolean delete(Long key);

}
