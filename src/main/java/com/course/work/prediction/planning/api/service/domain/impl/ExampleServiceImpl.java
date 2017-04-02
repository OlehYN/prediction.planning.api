package com.course.work.prediction.planning.api.service.domain.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.dao.ExampleDao;
import com.course.work.prediction.planning.api.dto.AddExampleDto;
import com.course.work.prediction.planning.api.dto.AddExampleInstanceDto;
import com.course.work.prediction.planning.api.entity.Example;
import com.course.work.prediction.planning.api.entity.ExampleInstance;
import com.course.work.prediction.planning.api.entity.Feature;
import com.course.work.prediction.planning.api.service.domain.ExampleService;
import com.course.work.prediction.planning.api.service.domain.FeatureListValueService;
import com.course.work.prediction.planning.api.service.domain.FeatureService;
import com.course.work.prediction.planning.api.service.domain.ModelService;

@Service
@Transactional
public class ExampleServiceImpl implements ExampleService {

	@Autowired
	private ExampleDao exampleDao;

	@Autowired
	private ModelService modelService;

	@Autowired
	private FeatureService featureService;

	@Autowired
	private FeatureListValueService featureListValueService;

	@Override
	public List<Example> getAll() {
		return exampleDao.getAll();
	}

	@Override
	public Example create(Example obj) {
		return exampleDao.create(obj);
	}

	@Override
	public Example read(Long key) {
		return exampleDao.read(key);
	}

	@Override
	public void update(Example obj) {
		exampleDao.update(obj);
	}

	@Override
	public boolean delete(Long key) {
		if (!exampleDao.read(key).isUsedInPredictionApi())
			return exampleDao.delete(key);
		return false;
	}

	@Override
	public Example create(AddExampleDto addExampleDto) {
		Example example = new Example();

		example.setExampleModel(modelService.read(addExampleDto.getModelId()));
		example.setCreationDate(new Date());
		example.setOutputLabel(addExampleDto.getOutputLabel());
		example.setUsedInPredictionApi(false);

		List<ExampleInstance> exampleInstances = new ArrayList<>();

		for (AddExampleInstanceDto addExampleInstanceDto : addExampleDto.getExamples()) {
			ExampleInstance exampleInstance = new ExampleInstance();
			exampleInstance.setExampleInstanceExample(example);

			Feature feature = featureService.read(addExampleInstanceDto.getId());

			if (feature.isCategory())
				exampleInstance.setExampleInstanceFeatureListValue(
						featureListValueService.read(addExampleInstanceDto.getListValueId()));
			else
				exampleInstance.setValue(addExampleInstanceDto.getValue());

			exampleInstance.setExampleInstanceFeature(feature);

			exampleInstances.add(exampleInstance);
		}

		example.setExampleInstances(exampleInstances);

		exampleDao.create(example);
		return example;
	}

	@Override
	public List<Example> unusedExamples(Long modelId) {
		return exampleDao.unusedExamples(modelId);
	}

}
