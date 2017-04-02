package com.course.work.prediction.planning.api.service.application.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.config.TokenInfo;
import com.course.work.prediction.planning.api.dto.CreateFeatureDto;
import com.course.work.prediction.planning.api.entity.Model;
import com.course.work.prediction.planning.api.entity.User;
import com.course.work.prediction.planning.api.service.application.CreateFeatureValidator;
import com.course.work.prediction.planning.api.service.domain.UserService;

@Service
public class CreateFeatureValidatorImpl implements CreateFeatureValidator {

	@Autowired
	private Map<String, TokenInfo> tokens;

	@Autowired
	private UserService userService;

	@Override
	public boolean isValid(CreateFeatureDto createFeatureDto, String token) {
		User user = userService.read(tokens.get(token).getUserId());

		if (createFeatureDto.getName() == null || createFeatureDto.getName().length() == 0
				|| createFeatureDto.getName().length() > 100)
			return false;

		if (createFeatureDto.isCategory()
				&& (createFeatureDto.getValues() == null || createFeatureDto.getValues().size() == 0))
			return false;

		if (createFeatureDto.isCategory())
			for (String name : createFeatureDto.getValues())
				if (name == null || name.length() == 0 || name.length() > 100)
					return false;

		for (Model model : user.getModels())
			if (model.getModelId() == createFeatureDto.getModelId())
				return true;

		return false;
	}
}
