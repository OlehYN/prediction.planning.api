package com.course.work.prediction.planning.api.service.infrastructure;

import java.io.IOException;
import java.util.List;

import com.course.work.prediction.planning.api.dto.TrainingInstanceDto;
import com.google.api.services.prediction.Prediction.Trainedmodels.Analyze;
import com.google.api.services.prediction.Prediction.Trainedmodels.Get;
import com.google.api.services.prediction.model.Output;

public interface GooglePredictionApi {
	Output predict(String projectName, String modelName, List<Object> exampleInstances) throws IOException;
	
	Analyze analyze(String projectName, String modelName) throws IOException;
	
	Integer delete(String projectName, String modelName) throws IOException;
	
	Get get(String projectName, String modelName) throws IOException;
	
	Integer insert(String projectName, String modelName) throws IOException;
	
	Integer insert(String projectName, String modelName, List<TrainingInstanceDto> trainingInstances) throws IOException;
	
	com.google.api.services.prediction.Prediction.Trainedmodels.List list(String projectName) throws IOException;
	
	Integer update(String projectName, String modelName, List<Object> exampleInstances, String outputLabel) throws IOException;
}
