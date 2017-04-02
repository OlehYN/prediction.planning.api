package com.course.work.prediction.planning.api.service.domain.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.work.prediction.planning.api.dao.FeatureDao;
import com.course.work.prediction.planning.api.dto.CreateFeatureDto;
import com.course.work.prediction.planning.api.entity.Feature;
import com.course.work.prediction.planning.api.entity.FeatureListValue;
import com.course.work.prediction.planning.api.entity.Model;
import com.course.work.prediction.planning.api.service.domain.FeatureService;
import com.course.work.prediction.planning.api.service.domain.ModelService;

@Service
@Transactional
public class FeatureServiceImpl implements FeatureService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private FeatureDao featureDao;

	@Override
	public List<Feature> getAll() {
		return featureDao.getAll();
	}

	@Override
	public Feature create(Feature obj) {
		return featureDao.create(obj);
	}

	@Override
	public Feature read(Long key) {
		return featureDao.read(key);
	}

	@Override
	public void update(Feature obj) {
		featureDao.update(obj);
	}

	@Override
	public boolean delete(Long key) {
		return featureDao.delete(key);
	}

	@Override
	public Long createFeature(CreateFeatureDto createFeatureDto) {

		Model model = modelService.read(createFeatureDto.getModelId());

		Feature feature = new Feature();
		feature.setFeatureModel(model);
		feature.setOrder(model.getAvailableOrder());

		model.setAvailableOrder(model.getAvailableOrder() + 1);

		feature.setName(createFeatureDto.getName());
		feature.setCategory(createFeatureDto.isCategory());

		if (createFeatureDto.isCategory()) {
			if (createFeatureDto.getValues() == null || createFeatureDto.getValues().size() == 0)
				return -1L;

			List<FeatureListValue> featureListValues = new ArrayList<>();

			for (String value : createFeatureDto.getValues()) {
				FeatureListValue featureListValue = new FeatureListValue();
				featureListValue.setFeatureListValueFeature(feature);
				featureListValue.setValue(value);

				featureListValues.add(featureListValue);
			}

			feature.setFeatureListValues(featureListValues);
		}
		// TODO Auto-generated method stub

		modelService.update(model);
		featureDao.create(feature);

		return feature.getFeatureId();
	}

}
