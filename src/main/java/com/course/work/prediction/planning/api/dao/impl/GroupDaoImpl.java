package com.course.work.prediction.planning.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.course.work.prediction.planning.api.dao.GroupDao;
import com.course.work.prediction.planning.api.entity.Group;

@Repository
public class GroupDaoImpl extends GenericDaoImpl<Group, Long> implements GroupDao{

	@Override
	protected Class<Group> entityClass() {
		return Group.class;
	}

	@Override
	protected String getEntityName() {
		return "Group";
	}

}
