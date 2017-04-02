package com.course.work.prediction.planning.api.service.application.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.config.TokenInfo;
import com.course.work.prediction.planning.api.entity.Model;
import com.course.work.prediction.planning.api.service.application.CreateModelValidator;
import com.course.work.prediction.planning.api.service.domain.UserService;

@Service
public class CreateModelValidatorImpl implements CreateModelValidator {

	@Autowired
	private UserService userService;

	@Autowired
	private Map<String, TokenInfo> tokens;

	@Override
	public boolean isValid(String name, String token) {

		if (name == null || name.length() == 0 || name.length() > 100)
			return false;

		for (Model model : userService.read(tokens.get(token).getUserId()).getModels())
			if (model.getName().equals(name))
				return false;

		return true;
	}

}
