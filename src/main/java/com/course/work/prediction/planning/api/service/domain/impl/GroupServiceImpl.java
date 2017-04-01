package com.course.work.prediction.planning.api.service.domain.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.dao.GroupDao;
import com.course.work.prediction.planning.api.entity.Group;
import com.course.work.prediction.planning.api.service.domain.GroupService;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDao groupDao;

	@Override
	public List<Group> getAll() {
		return groupDao.getAll();
	}

	@Override
	public Group create(Group obj) {
		return groupDao.create(obj);
	}

	@Override
	public Group read(Long key) {
		return groupDao.read(key);
	}

	@Override
	public void update(Group obj) {
		groupDao.update(obj);
	}

	@Override
	public boolean delete(Long key) {
		return groupDao.delete(key);
	}

}
