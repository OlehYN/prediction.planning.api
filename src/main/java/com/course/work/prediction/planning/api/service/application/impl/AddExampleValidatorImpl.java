package com.course.work.prediction.planning.api.service.application.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.config.TokenInfo;
import com.course.work.prediction.planning.api.dto.AddExampleDto;
import com.course.work.prediction.planning.api.dto.AddExampleInstanceDto;
import com.course.work.prediction.planning.api.entity.Feature;
import com.course.work.prediction.planning.api.entity.FeatureListValue;
import com.course.work.prediction.planning.api.entity.Model;
import com.course.work.prediction.planning.api.entity.User;
import com.course.work.prediction.planning.api.service.application.AddExampleValidator;
import com.course.work.prediction.planning.api.service.domain.ModelService;
import com.course.work.prediction.planning.api.service.domain.UserService;

@Service
public class AddExampleValidatorImpl implements AddExampleValidator {

	@Autowired
	private ModelService modelService;

	@Autowired
	private Map<String, TokenInfo> tokens;

	@Autowired
	private UserService userService;

	@Override
	public boolean isValid(AddExampleDto addExampleDto, String token) {
		User user = userService.read(tokens.get(token).getUserId());
		Model model = modelService.read(addExampleDto.getModelId());

		if (user.getUserId() != model.getUser().getUserId())
			throw new IllegalAccessError();
		
		if(addExampleDto.getOutputLabel() == null || addExampleDto.getOutputLabel() <= 0)
			return false;

		List<Feature> features = model.getFeatures();

		int count = 0;

		if (addExampleDto.getExamples() == null || addExampleDto.getExamples().size() == 0)
			return false;

		outer: for (Feature feature : features)
			for (AddExampleInstanceDto exampleInstance : addExampleDto.getExamples())
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

		if (count != addExampleDto.getExamples().size())
			return false;

		return true;
	}

}
