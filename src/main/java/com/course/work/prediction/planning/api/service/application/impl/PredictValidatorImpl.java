package com.course.work.prediction.planning.api.service.application.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.config.TokenInfo;
import com.course.work.prediction.planning.api.dto.PredictExampleInstanceDto;
import com.course.work.prediction.planning.api.entity.Feature;
import com.course.work.prediction.planning.api.entity.FeatureListValue;
import com.course.work.prediction.planning.api.entity.Model;
import com.course.work.prediction.planning.api.entity.User;
import com.course.work.prediction.planning.api.service.application.PredictValidator;
import com.course.work.prediction.planning.api.service.domain.ModelService;
import com.course.work.prediction.planning.api.service.domain.UserService;

@Service
public class PredictValidatorImpl implements PredictValidator{
	
	@Autowired
	private ModelService modelService;

	@Autowired
	private Map<String, TokenInfo> tokens;

	@Autowired
	private UserService userService;

	@Override
	public boolean isValid(List<PredictExampleInstanceDto> instances, Long modelId, String token) {
		User user = userService.read(tokens.get(token).getUserId());
		Model model = modelService.read(modelId);

		if (user.getUserId() != model.getUser().getUserId())
			return false;

		List<Feature> features = model.getFeatures();

		int count = 0;

		if (instances == null || instances.size() == 0)
			return false;

		outer: for (Feature feature : features)
			for (PredictExampleInstanceDto exampleInstance : instances)
				if (exampleInstance.getId() == feature.getFeatureId()) {
					++count;

					if (feature.isCategory()) {
						for (FeatureListValue featureListValue : feature.getFeatureListValues())
							if (featureListValue.getFeatureListValueId() == exampleInstance.getListValueId())
								continue outer;

						return false;
					}
					continue outer;
				}

		if (count != instances.size())
			return false;

		return true;
	}

}
