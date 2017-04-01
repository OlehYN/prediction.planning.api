package com.course.work.prediction.planning.api.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.course.work.prediction.planning.api.service.infrastructure.GooglePredictionApi;

@Controller
public class HomeController {

	@Autowired
	private GooglePredictionApi googlePredictionApi;

	@RequestMapping(value = "/")
	@ResponseBody
	public String test() throws IOException {
		List<Object> features = new ArrayList<>();
		features.add("hi");
		System.out.println(googlePredictionApi.predict("double-archive-160219", "language-identifier", features));
		return "Test";
	}
}
