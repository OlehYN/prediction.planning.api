package com.course.work.prediction.planning.api.service.application.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.config.TokenInfo;
import com.course.work.prediction.planning.api.entity.Feature;
import com.course.work.prediction.planning.api.entity.User;
import com.course.work.prediction.planning.api.service.application.RenameFeatureValidator;
import com.course.work.prediction.planning.api.service.domain.FeatureService;
import com.course.work.prediction.planning.api.service.domain.UserService;

@Service
public class RenameFeatureValidatorImpl implements RenameFeatureValidator {

	@Autowired
	private Map<String, TokenInfo> tokens;

	@Autowired
	private UserService userService;

	@Autowired
	private FeatureService featureService;

	@Override
	public boolean isValid(String token, Long featureId, String name) {
		User user = userService.read(tokens.get(token).getUserId());
		Feature feature = featureService.read(featureId);

		if (feature.getFeatureModel().getUser().getUserId() != user.getUserId())
			return false;

		if (name == null || name.length() == 0 || name.length() > 100)
			return false;

		return true;
	}

}
