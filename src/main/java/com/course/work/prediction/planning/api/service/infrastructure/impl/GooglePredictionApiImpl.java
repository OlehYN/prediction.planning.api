package com.course.work.prediction.planning.api.service.infrastructure.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.dto.TrainingInstanceDto;
import com.course.work.prediction.planning.api.service.infrastructure.GooglePredictionApi;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.prediction.Prediction;
import com.google.api.services.prediction.Prediction.Trainedmodels.Analyze;
import com.google.api.services.prediction.Prediction.Trainedmodels.Delete;
import com.google.api.services.prediction.Prediction.Trainedmodels.Get;
import com.google.api.services.prediction.model.Update;
import com.google.api.services.prediction.model.Input;
import com.google.api.services.prediction.model.Input.InputInput;
import com.google.api.services.prediction.model.Insert;
import com.google.api.services.prediction.model.Insert.TrainingInstances;
import com.google.api.services.prediction.model.Output;

@Service
public class GooglePredictionApiImpl implements GooglePredictionApi {

	@Autowired
	private Prediction prediction;

	@Autowired
	private JsonFactory jsonFactory;

	@Override
	public Output predict(String projectName, String modelName, List<Object> exampleInstances) throws IOException {
		Input input = new Input();
		InputInput inputInput = new InputInput();

		inputInput.setCsvInstance(exampleInstances);
		input.setInput(inputInput);
		Output output = prediction.trainedmodels().predict(projectName, modelName, input).execute();
		return output;
	}

	@Override
	public Analyze analyze(String projectName, String modelName) throws IOException {
		return prediction.trainedmodels().analyze(projectName, modelName);
	}

	@Override
	public Delete delete(String projectName, String modelName) throws IOException {
		return prediction.trainedmodels().delete(projectName, modelName);
	}

	@Override
	public Get get(String projectName, String modelName) throws IOException {
		return prediction.trainedmodels().get(projectName, modelName);
	}

	@Override
	public com.google.api.services.prediction.Prediction.Trainedmodels.Insert insert(String projectName,
			String modelName) throws IOException {
		Insert insert = new Insert();
		insert.setFactory(jsonFactory);
		insert.setModelType("regression");
		insert.setId(modelName);

		return prediction.trainedmodels().insert(projectName, insert);
	}

	@Override
	public com.google.api.services.prediction.Prediction.Trainedmodels.List list(String projectName)
			throws IOException {
		return prediction.trainedmodels().list(projectName);
	}

	@Override
	public com.google.api.services.prediction.Prediction.Trainedmodels.Update update(String projectName,
			String modelName, List<Object> exampleInstances, String outputLabel) throws IOException {
		Update update = new Update();
		update.setCsvInstance(exampleInstances);
		update.setOutput(outputLabel);
		update.setFactory(jsonFactory);

		return prediction.trainedmodels().update(projectName, modelName, update);
	}

	@Override
	public com.google.api.services.prediction.Prediction.Trainedmodels.Insert insert(String projectName,
			String modelName, List<TrainingInstanceDto> trainingInstanceDtos) throws IOException {
		Insert insert = new Insert();
		insert.setFactory(jsonFactory);
		insert.setModelType("regression");
		insert.setId(modelName);

		List<TrainingInstances> trainingInstances = new ArrayList<>();

		for (TrainingInstanceDto trainingInstanceDto : trainingInstanceDtos) {
			TrainingInstances trainingInstance = new TrainingInstances();
			trainingInstance.setOutput(trainingInstanceDto.getOutputLabel());
			trainingInstance.setCsvInstance(trainingInstanceDto.getFeatures());

			trainingInstances.add(trainingInstance);
		}

		insert.setTrainingInstances(trainingInstances);
		return prediction.trainedmodels().insert(projectName, insert);
	}

}
