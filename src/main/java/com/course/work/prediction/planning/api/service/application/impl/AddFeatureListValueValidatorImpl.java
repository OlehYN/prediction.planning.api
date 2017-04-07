package com.course.work.prediction.planning.api.service.application.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.config.TokenInfo;
import com.course.work.prediction.planning.api.entity.Feature;
import com.course.work.prediction.planning.api.entity.FeatureListValue;
import com.course.work.prediction.planning.api.entity.User;
import com.course.work.prediction.planning.api.service.application.AddFeatureListValueValidator;
import com.course.work.prediction.planning.api.service.domain.FeatureService;
import com.course.work.prediction.planning.api.service.domain.UserService;

@Service
public class AddFeatureListValueValidatorImpl implements AddFeatureListValueValidator {

	@Autowired
	private Map<String, TokenInfo> tokens;

	@Autowired
	private UserService userService;

	@Autowired
	private FeatureService featureService;

	@Override
	public boolean isValid(Long featureId, String token, List<String> values) {

		User user = userService.read(tokens.get(token).getUserId());
		Feature feature = featureService.read(featureId);

		if (feature.getFeatureModel().getUser().getUserId() != user.getUserId())
			throw new IllegalAccessError();

		if (values == null || values.size() == 0)
			return false;

		for (String name : values)
			if (name == null || name.length() == 0 || name.length() > 100)
				return false;

		List<FeatureListValue> listValues = feature.getFeatureListValues();

		for (FeatureListValue featureListValue : listValues)
			for (String value : values)
				if (featureListValue.getValue().equals(value))
					return false;

		return true;
	}

}
