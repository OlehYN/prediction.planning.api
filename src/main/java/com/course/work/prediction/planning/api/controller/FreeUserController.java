package com.course.work.prediction.planning.api.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.course.work.prediction.planning.api.config.TokenInfo;
import com.course.work.prediction.planning.api.dto.AddExampleDto;
import com.course.work.prediction.planning.api.dto.CreateFeatureDto;
import com.course.work.prediction.planning.api.dto.ExampleDto;
import com.course.work.prediction.planning.api.dto.FeatureDto;
import com.course.work.prediction.planning.api.dto.ModelInfoDto;
import com.course.work.prediction.planning.api.dto.SuccessWrapper;
import com.course.work.prediction.planning.api.entity.Model;
import com.course.work.prediction.planning.api.service.application.AddExampleValidator;
import com.course.work.prediction.planning.api.service.application.AddFeatureListValueValidator;
import com.course.work.prediction.planning.api.service.application.CreateFeatureValidator;
import com.course.work.prediction.planning.api.service.application.CreateModelValidator;
import com.course.work.prediction.planning.api.service.application.RenameFeatureValidator;
import com.course.work.prediction.planning.api.service.application.UpdateModelService;
import com.course.work.prediction.planning.api.service.domain.ExampleService;
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
	private ExampleService exampleService;

	@Autowired
	private CreateFeatureValidator createFeatureValidator;

	@Autowired
	private CreateModelValidator createModelValidator;

	@Autowired
	private RenameFeatureValidator renameFeatureValidator;

	@Autowired
	private AddFeatureListValueValidator addFeatureListValueValidator;

	@Autowired
	private AddExampleValidator addExampleValidator;
	
	@Autowired
	private UpdateModelService updateModelService;

	@RequestMapping("/list")
	@ResponseBody
	@Transactional
	public SuccessWrapper<List<ModelInfoDto>> getModels(String token) {
		Long userId = tokens.get(token).getUserId();
		return new SuccessWrapper<List<ModelInfoDto>>(
				userService.read(userId).getModels().stream().map(ModelInfoDto::new).collect(Collectors.toList()));
	}

	@RequestMapping("/features")
	@ResponseBody
	@Transactional
	public SuccessWrapper<List<FeatureDto>> getFeatures(String token, Long modelId) {
		Long currentUserId = tokens.get(token).getUserId();

		Model currentModel = modelService.read(modelId);
		Long expectedUserId = currentModel.getUser().getUserId();

		if (currentUserId != expectedUserId)
			throw new IllegalAccessError("Illegal access!");

		return new SuccessWrapper<List<FeatureDto>>(
				currentModel.getFeatures().stream().map(FeatureDto::new).collect(Collectors.toList()));
	}

	@RequestMapping("/createFeature")
	@ResponseBody
	@Transactional
	public SuccessWrapper<Long> createFeature(CreateFeatureDto createFeatureDto, String token) {
		if (!createFeatureValidator.isValid(createFeatureDto, token))
			throw new IllegalAccessError("Illegal access!");
		return new SuccessWrapper<Long>(featureService.createFeature(createFeatureDto));
	}

	@RequestMapping("/createModel")
	@ResponseBody
	@Transactional
	public SuccessWrapper<Long> createModel(String name, String token) throws IOException {
		if (!createModelValidator.isValid(name, token))
			throw new IllegalArgumentException("Such name already exists");
		return new SuccessWrapper<Long>(modelService.create(name, tokens.get(token).getUserId()).getModelId());
	}

	@RequestMapping("/renameFeature")
	@ResponseBody
	@Transactional
	public SuccessWrapper<Boolean> renameFeature(Long featureId, String newName, String token) {
		if (renameFeatureValidator.isValid(token, featureId, newName))
			throw new IllegalArgumentException("Invalid data");

		featureService.rename(featureId, newName);
		return new SuccessWrapper<Boolean>(true);
	}

	@RequestMapping("/addFeatureValue")
	@ResponseBody
	@Transactional
	public SuccessWrapper<Boolean> addFeatureValue(Long featureId, List<String> featureValues, String token) {
		if (addFeatureListValueValidator.isValid(featureId, token, featureValues))
			throw new IllegalArgumentException("Invalid data");

		featureService.addFeatureValues(featureId, featureValues);
		return new SuccessWrapper<Boolean>(true);
	}

	@RequestMapping("/deleteModel")
	@ResponseBody
	@Transactional
	public SuccessWrapper<Boolean> deleteModel(Long modelId, String token) throws IOException {
		Model model = modelService.read(modelId);

		if (model.getUser().getUserId() != tokens.get(token).getUserId())
			throw new IllegalAccessError("Illegal access!");

		return new SuccessWrapper<Boolean>(modelService.delete(modelId));
	}

	@RequestMapping("/examples")
	@ResponseBody
	@Transactional
	public SuccessWrapper<List<ExampleDto>> getExamples(Long modelId, String token) {
		Model model = modelService.read(modelId);

		if (model.getUser().getUserId() != tokens.get(token).getUserId())
			throw new IllegalAccessError("Illegal access!");

		return new SuccessWrapper<List<ExampleDto>>(
				model.getExamples().stream().map(ExampleDto::new).collect(Collectors.toList()));
	}

	@RequestMapping("/addExample")
	@ResponseBody
	@Transactional
	public SuccessWrapper<Long> addExample(AddExampleDto exampleDto, String token) {
		if (addExampleValidator.isValid(exampleDto, token))
			throw new IllegalArgumentException("Invalid data");
		return new SuccessWrapper<Long>(exampleService.create(exampleDto).getExampleId());
	}

	@RequestMapping("/removeExample")
	@ResponseBody
	@Transactional
	public SuccessWrapper<Boolean> removeExample(Long id, String token) {
		if (exampleService.read(id).getExampleModel().getUser().getUserId() != tokens.get(token).getUserId())
			throw new IllegalAccessError("Illegal access!");
		return new SuccessWrapper<Boolean>(exampleService.delete(id));
	}
	
	@RequestMapping("/updateModel")
	@ResponseBody
	public SuccessWrapper<Boolean> updateModel(Long modelId, String token) throws IOException{
		if(modelService.read(modelId).getUser().getUserId() != tokens.get(token).getUserId())
			throw new IllegalAccessError("Illegal access!");
		return new SuccessWrapper<Boolean>(updateModelService.updateModel(modelId));
	}
}
