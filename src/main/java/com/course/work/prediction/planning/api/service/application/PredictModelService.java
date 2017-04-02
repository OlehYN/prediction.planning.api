package com.course.work.prediction.planning.api.service.application;

import java.io.IOException;
import java.util.List;

import com.course.work.prediction.planning.api.dto.PredictExampleInstanceDto;

public interface PredictModelService {
	Integer predict(List<PredictExampleInstanceDto> instances, Long modelId) throws IOException;
}
