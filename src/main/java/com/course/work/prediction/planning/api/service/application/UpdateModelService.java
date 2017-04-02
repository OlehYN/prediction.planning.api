package com.course.work.prediction.planning.api.service.application;

import java.io.IOException;
import java.util.List;

import com.course.work.prediction.planning.api.entity.Example;
import com.course.work.prediction.planning.api.entity.Model;

public interface UpdateModelService {
	boolean updateModel(Long modelId) throws IOException;

	void partialUpdate(Model model, List<Object> exampleFeatures, Example example) throws IOException;
}
