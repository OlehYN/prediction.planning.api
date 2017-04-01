package com.course.work.prediction.planning.api.service.domain;

import java.util.List;

import com.course.work.prediction.planning.api.entity.Group;

public interface GroupService {
	List<Group> getAll();

	Group create(Group obj);

	Group read(Long key);

	void update(Group obj);

	boolean delete(Long key);

}
