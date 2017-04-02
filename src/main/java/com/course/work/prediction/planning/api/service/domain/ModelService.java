package com.course.work.prediction.planning.api.service.domain;

import java.io.IOException;
import java.util.List;

import com.course.work.prediction.planning.api.entity.Model;

public interface ModelService {
	List<Model> getAll();

	Model create(Model obj);

	Model read(Long key);

	void update(Model obj);

	boolean delete(Long key);
	
	Model create(String name, Long userId) throws IOException;

}
