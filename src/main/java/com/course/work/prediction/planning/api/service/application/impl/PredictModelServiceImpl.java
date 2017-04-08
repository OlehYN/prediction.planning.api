package com.course.work.prediction.planning.api.service.application.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.controller.FreeUserController;
import com.course.work.prediction.planning.api.dto.AddExampleInstanceDto;
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
	
	public static final Log log = LogFactory.getLog(FreeUserController.class);

	@Autowired
	private String projectName;

	@Override
	public Integer predict(List<AddExampleInstanceDto> instances, Long modelId) throws IOException {
		Model model = modelService.read(modelId);
		List<Object> exampleFeatures = new ArrayList<>();

		outer: for (Feature feature : model.getFeatures()) {
			for (AddExampleInstanceDto predictExampleInstance : instances)
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
		Output output = googlePredictionApi.predict(projectName, model.getUser().getLogin() + "#" + model.getName(),
				exampleFeatures);
		
		log.info(output);
		
		return Double.valueOf(output.getOutputValue()).intValue();
	}

}
