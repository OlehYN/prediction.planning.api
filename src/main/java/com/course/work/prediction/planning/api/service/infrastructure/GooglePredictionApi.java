package com.course.work.prediction.planning.api.service.infrastructure;

import java.io.IOException;
import java.util.List;

import com.course.work.prediction.planning.api.dto.TrainingInstanceDto;
import com.google.api.services.prediction.Prediction.Trainedmodels.Analyze;
import com.google.api.services.prediction.Prediction.Trainedmodels.Delete;
import com.google.api.services.prediction.Prediction.Trainedmodels.Get;
import com.google.api.services.prediction.Prediction.Trainedmodels.Insert;
import com.google.api.services.prediction.Prediction.Trainedmodels.Update;
import com.google.api.services.prediction.model.Output;

public interface GooglePredictionApi {
	Output predict(String projectName, String modelName, List<Object> exampleInstances) throws IOException;
	
	Analyze analyze(String projectName, String modelName) throws IOException;
	
	Delete delete(String projectName, String modelName) throws IOException;
	
	Get get(String projectName, String modelName) throws IOException;
	
	Insert insert(String projectName, String modelName) throws IOException;
	
	Insert insert(String projectName, String modelName, List<TrainingInstanceDto> trainingInstances) throws IOException;
	
	com.google.api.services.prediction.Prediction.Trainedmodels.List list(String projectName) throws IOException;
	
	Update update(String projectName, String modelName, List<Object> exampleInstances, String outputLabel) throws IOException;
}
