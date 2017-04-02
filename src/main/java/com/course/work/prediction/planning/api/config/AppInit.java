package com.course.work.prediction.planning.api.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { Config.class };
	}

	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { Config.class };
	}

	protected String[] getServletMappings() {
		return new String[] { "/"};
	}
}
