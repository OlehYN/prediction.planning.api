package com.course.work.prediction.planning.api.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.course.work.prediction.planning.api.config.TokenInfo;
import com.course.work.prediction.planning.api.dto.AddExampleDto;
import com.course.work.prediction.planning.api.dto.CreateFeatureDto;
import com.course.work.prediction.planning.api.dto.ErrorDto;
import com.course.work.prediction.planning.api.dto.ExampleDto;
import com.course.work.prediction.planning.api.dto.FeatureDto;
import com.course.work.prediction.planning.api.dto.ModelInfoDto;
import com.course.work.prediction.planning.api.dto.PredictExampleInstanceDto;
import com.course.work.prediction.planning.api.dto.SuccessWrapper;
import com.course.work.prediction.planning.api.entity.Model;
import com.course.work.prediction.planning.api.service.application.AddExampleValidator;
import com.course.work.prediction.planning.api.service.application.AddFeatureListValueValidator;
import com.course.work.prediction.planning.api.service.application.CreateFeatureValidator;
import com.course.work.prediction.planning.api.service.application.CreateModelValidator;
import com.course.work.prediction.planning.api.service.application.PredictModelService;
import com.course.work.prediction.planning.api.service.application.PredictValidator;
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

	@Autowired
	private PredictModelService predictModelService;

	@Autowired
	private PredictValidator predictValidator;
	
	public static final Log log = LogFactory.getLog(FreeUserController.class);

	@RequestMapping("/list")
	@ResponseBody
	@Transactional
	public SuccessWrapper<List<ModelInfoDto>> getModels(String token) {
		Long userId = tokens.get(token).getUserId();
		log.info("/list: userId= " + userId);
		return new SuccessWrapper<List<ModelInfoDto>>(
				userService.read(userId).getModels().stream().map(ModelInfoDto::new).collect(Collectors.toList()));
	}

	@RequestMapping("/features")
	@ResponseBody
	@Transactional
	public SuccessWrapper<List<FeatureDto>> getFeatures(String token, Long modelId) {
		Long currentUserId = tokens.get(token).getUserId();
		log.info("/features: userId= " + currentUserId + " modelId= " + modelId);

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
	public SuccessWrapper<Long> createFeature(@RequestBody CreateFeatureDto createFeatureDto, String token) {
		log.info("/createFeature: userId= " + tokens.get(token).getUserId() + " createFeatureDto= " + createFeatureDto);
		if (!createFeatureValidator.isValid(createFeatureDto, token))
			throw new IllegalAccessError("Illegal access!");
		return new SuccessWrapper<Long>(featureService.createFeature(createFeatureDto));
	}

	@RequestMapping("/createModel")
	@ResponseBody
	@Transactional
	public SuccessWrapper<ModelInfoDto> createModel(String name, String token) throws IOException {
		log.info("/createModel: userId= " + tokens.get(token).getUserId() + " name= " + name);
		if (!createModelValidator.isValid(name, token))
			throw new IllegalArgumentException("Such name already exists");
		return new SuccessWrapper<ModelInfoDto>(
				new ModelInfoDto(modelService.create(name, tokens.get(token).getUserId())));
	}

	@RequestMapping("/renameFeature")
	@ResponseBody
	@Transactional
	public SuccessWrapper<Boolean> renameFeature(Long featureId, String newName, String token) {
		log.info("/renameFeature: userId= " + tokens.get(token).getUserId() + " newName= " + newName + " featureId= " + featureId);
		if (!renameFeatureValidator.isValid(token, featureId, newName))
			throw new IllegalArgumentException("Invalid data");

		featureService.rename(featureId, newName);
		return new SuccessWrapper<Boolean>(true);
	}

	@RequestMapping("/addFeatureValue")
	@ResponseBody
	@Transactional
	public SuccessWrapper<Boolean> addFeatureValue(Long featureId, @RequestBody List<String> featureValues, String token) {
		log.info("/addFeatureValue: userId= " + tokens.get(token).getUserId() + " featureValues= " + featureValues + " featureId= " + featureId);
		if (!addFeatureListValueValidator.isValid(featureId, token, featureValues))
			throw new IllegalArgumentException("Invalid data");

		featureService.addFeatureValues(featureId, featureValues);
		return new SuccessWrapper<Boolean>(true);
	}

	@RequestMapping("/deleteModel")
	@ResponseBody
	@Transactional
	public SuccessWrapper<Boolean> deleteModel(Long modelId, String token) throws IOException {
		log.info("/deleteModel: userId= " + tokens.get(token).getUserId() + " modelId= " + modelId);
		Model model = modelService.read(modelId);

		if (model.getUser().getUserId() != tokens.get(token).getUserId())
			throw new IllegalAccessError("Illegal access!");

		return new SuccessWrapper<Boolean>(modelService.delete(modelId));
	}

	@RequestMapping("/examples")
	@ResponseBody
	@Transactional
	public SuccessWrapper<List<ExampleDto>> getExamples(Long modelId, String token) {
		log.info("/examples: userId= " + tokens.get(token).getUserId() + " modelId= " + modelId);
		Model model = modelService.read(modelId);

		if (model.getUser().getUserId() != tokens.get(token).getUserId())
			throw new IllegalAccessError("Illegal access!");

		return new SuccessWrapper<List<ExampleDto>>(
				exampleService.unusedExamples(modelId).stream().map(ExampleDto::new).collect(Collectors.toList()));
	}

	@RequestMapping("/addExample")
	@ResponseBody
	@Transactional
	public SuccessWrapper<Boolean> addExample(@RequestBody AddExampleDto exampleDto, String token) {
		log.info("/addExample: userId= " + tokens.get(token).getUserId() + " exampleDto= " + exampleDto);
		if (!addExampleValidator.isValid(exampleDto, token))
			throw new IllegalArgumentException("Invalid data");
		exampleService.create(exampleDto);
		return new SuccessWrapper<Boolean>(true);
	}

	@RequestMapping("/removeExample")
	@ResponseBody
	@Transactional
	public SuccessWrapper<Boolean> removeExample(Long id, String token) {
		log.info("/removeExample: userId= " + tokens.get(token).getUserId() + " id= " + id);
		if (exampleService.read(id).getExampleModel().getUser().getUserId() != tokens.get(token).getUserId())
			throw new IllegalAccessError("Illegal access!");
		return new SuccessWrapper<Boolean>(exampleService.delete(id));
	}

	@RequestMapping("/updateModel")
	@ResponseBody
	public SuccessWrapper<Boolean> updateModel(Long modelId, String token) throws IOException {
		log.info("/updateModel: userId= " + tokens.get(token).getUserId() + " modelId= " + modelId);
		if (modelService.read(modelId).getUser().getUserId() != tokens.get(token).getUserId())
			throw new IllegalAccessError("Illegal access!");
		return new SuccessWrapper<Boolean>(updateModelService.updateModel(modelId));
	}

	@RequestMapping("/predict")
	@ResponseBody
	public SuccessWrapper<Integer> predict(Long modelId, @RequestBody List<PredictExampleInstanceDto> instances, String token)
			throws IOException {
		log.info("/predict: userId= " + tokens.get(token).getUserId() + " modelId= " + modelId + " instances= " + instances);
		if (modelService.read(modelId).getUser().getUserId() != tokens.get(token).getUserId()
				|| !predictValidator.isValid(instances, modelId, token))

			throw new IllegalAccessError("Illegal access!");
		return new SuccessWrapper<Integer>(predictModelService.predict(instances, modelId));
	}
	
	@ExceptionHandler(IllegalAccessError.class)
	@ResponseBody
	public ErrorDto handleIllegalAccessError(IllegalAccessError error) {
		log.error(error.toString());
		return new ErrorDto("Illegal access!", HttpStatus.UNAUTHORIZED.value());

	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody
	public ErrorDto handleIllegalArgumentError(IllegalArgumentException exception) {
		log.error(exception.toString());
		return new ErrorDto("Please, refresh the page and check your data!", HttpStatus.CONFLICT.value());

	}
	
	@ExceptionHandler(IOException.class)
	@ResponseBody
	public ErrorDto handleIOException(IOException exception) {
		log.error(exception.toString());
		return new ErrorDto("Prediction server is currently unavailable! ", HttpStatus.INTERNAL_SERVER_ERROR.value());

	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ErrorDto handleGeneralInfo(Exception exception) {
		log.error(exception.toString());
		return new ErrorDto("Server currently unavailable! ", HttpStatus.INTERNAL_SERVER_ERROR.value());

	}
}
