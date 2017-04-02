package com.course.work.prediction.planning.api.service.application.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.dto.PredictExampleInstanceDto;
import com.course.work.prediction.planning.api.entity.Feature;
import com.course.work.prediction.planning.api.entity.Model;
import com.course.work.prediction.planning.api.service.application.PredictModelService;
import com.course.work.prediction.planning.api.service.domain.FeatureListValueService;
import com.course.work.prediction.planning.api.service.domain.ModelService;
import com.course.work.prediction.planning.api.service.infrastructure.GooglePredictionApi;
import com.google.api.services.prediction.model.Output;

@Service
public class PredictModelServiceImpl implements PredictModelService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private FeatureListValueService featureListValueService;

	@Autowired
	private GooglePredictionApi googlePredictionApi;

	@Autowired
	private String projectName;

	@Override
	public Integer predict(List<PredictExampleInstanceDto> instances, Long modelId) throws IOException {
		Model model = modelService.read(modelId);
		List<Object> exampleFeatures = new ArrayList<>();

		outer: for (Feature feature : model.getFeatures()) {
			for (PredictExampleInstanceDto predictExampleInstance : instances)
				if (predictExampleInstance.getId() == feature.getFeatureId()) {
					if (feature.isCategory())
						exampleFeatures
								.add(featureListValueService.read(predictExampleInstance.getListValueId()).getValue());
					else
						exampleFeatures.add(predictExampleInstance.getValue());
					continue outer;
				}

			if (feature.isCategory())
				exampleFeatures.add("");
			else
				exampleFeatures.add(0D);
		}
		Output output = googlePredictionApi.predict(projectName, model.getName(), exampleFeatures);
		return Double.valueOf(output.getOutputLabel()).intValue();
	}

}
