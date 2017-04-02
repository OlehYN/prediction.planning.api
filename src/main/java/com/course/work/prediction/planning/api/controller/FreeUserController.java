package com.course.work.prediction.planning.api.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.course.work.prediction.planning.api.config.TokenInfo;
import com.course.work.prediction.planning.api.dto.CreateFeatureDto;
import com.course.work.prediction.planning.api.dto.FeatureDto;
import com.course.work.prediction.planning.api.dto.ModelInfoDto;
import com.course.work.prediction.planning.api.entity.Model;
import com.course.work.prediction.planning.api.service.application.CreateFeatureValidator;
import com.course.work.prediction.planning.api.service.application.CreateModelValidator;
import com.course.work.prediction.planning.api.service.domain.FeatureService;
import com.course.work.prediction.planning.api.service.domain.ModelService;
import com.course.work.prediction.planning.api.service.domain.UserService;

@Controller
public class FreeUserController {

	@Autowired
	private Map<String, TokenInfo> tokens;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelService modelService;

	@Autowired
	private FeatureService featureService;

	@Autowired
	private CreateFeatureValidator createFeatureValidator;

	@Autowired
	private CreateModelValidator createModelValidator;

	@RequestMapping("/list")
	@ResponseBody
	public List<ModelInfoDto> getModels(String token) {
		Long userId = tokens.get(token).getUserId();
		return userService.read(userId).getModels().stream().map(ModelInfoDto::new).collect(Collectors.toList());
	}

	@RequestMapping("/features")
	@ResponseBody
	public List<FeatureDto> getFeatures(String token, Long modelId) {
		Long currentUserId = tokens.get(token).getUserId();

		Model currentModel = modelService.read(modelId);
		Long expectedUserId = currentModel.getUser().getUserId();

		if (currentUserId != expectedUserId)
			throw new IllegalAccessError("Illegal access!");

		return currentModel.getFeatures().stream().map(FeatureDto::new).collect(Collectors.toList());
	}

	@RequestMapping("/createFeature")
	@ResponseBody
	public Long createFeature(CreateFeatureDto createFeatureDto, String token) {
		if (!createFeatureValidator.isValid(createFeatureDto, token))
			throw new IllegalAccessError("Illegal access!");
		return featureService.createFeature(createFeatureDto);
	}

	@RequestMapping("/createModel")
	@ResponseBody
	public Long createModel(String name, String token) throws IOException {
		if (!createModelValidator.isValid(name, token))
			throw new IllegalArgumentException("Such name already exists");
		return modelService.create(name, tokens.get(token).getUserId()).getModelId();
	}
}
