package com.course.work.prediction.planning.api.service.application.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.entity.Example;
import com.course.work.prediction.planning.api.entity.ExampleInstance;
import com.course.work.prediction.planning.api.entity.Feature;
import com.course.work.prediction.planning.api.entity.Model;
import com.course.work.prediction.planning.api.service.application.UpdateModelService;
import com.course.work.prediction.planning.api.service.domain.ExampleService;
import com.course.work.prediction.planning.api.service.domain.ModelService;
import com.course.work.prediction.planning.api.service.infrastructure.GooglePredictionApi;

@Service
public class UpdateModelServiceImpl implements UpdateModelService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private ExampleService exampleService;

	@Autowired
	private GooglePredictionApi googlePredictionApi;

	@Autowired
	private String projectName;

	@Override
	public boolean updateModel(Long modelId) throws IOException {
		Model model = modelService.read(modelId);

		List<Feature> features = model.getFeatures();
		features.sort((f1, f2) -> f1.getOrder().compareTo(f2.getOrder()));

		List<Example> examples = exampleService.unusedExamples(modelId);

		for (Example example : examples) {
			List<Object> exampleFeatures = new ArrayList<>();

			outer: for (Feature feature : features) {
				for (ExampleInstance exampleInstance : example.getExampleInstances())
					if (exampleInstance.getExampleInstanceFeature().getFeatureId() == feature.getFeatureId()) {
						if (feature.isCategory())
							exampleFeatures.add(exampleInstance.getExampleInstanceFeatureListValue().getValue());
						else
							exampleFeatures.add(exampleInstance.getValue());
						continue outer;
					}

				if (feature.isCategory())
					exampleFeatures.add("");
				else
					exampleFeatures.add(0D);
			}
			partialUpdate(model, exampleFeatures, example);

		}

		return true;
	}

	@Transactional
	@Override
	public void partialUpdate(Model model, List<Object> exampleFeatures, Example example) throws IOException {
		Integer statusCode = googlePredictionApi.update(projectName, model.getExternalId(), exampleFeatures,
				example.getOutputLabel().toString());

		if (statusCode != 200)
			throw new InternalError();

		example.setUsedInPredictionApi(true);
		exampleService.update(example);
	}

}
