package com.course.work.prediction.planning.api.service.application;

import java.io.IOException;
import java.util.List;

import com.course.work.prediction.planning.api.dto.AddExampleInstanceDto;

public interface PredictModelService {
	Integer predict(List<AddExampleInstanceDto> instances, Long modelId) throws IOException;
}
