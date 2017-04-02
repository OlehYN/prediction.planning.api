package com.course.work.prediction.planning.api.config;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.prediction.Prediction;
import com.google.api.services.prediction.PredictionScopes;

@Configuration
@EnableWebMvc
@ComponentScan("com.course.work.prediction.planning.api.*")
@PropertySource("classpath:jdbc.properties")
@PropertySource("classpath:prediction.properties")
@Import(HibernateConfig.class)
public class Config extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment environment;

	@Autowired
	private ServletContext servletContext;
	
	@Bean
	public Map<String, TokenInfo> tokens(){
		return new HashMap<>();
	}

	@Bean
	public String projectName() {
		return (environment.getRequiredProperty("project.id"));
	}

	@Bean
	public HttpTransport httpTransport() throws GeneralSecurityException, IOException {
		return GoogleNetHttpTransport.newTrustedTransport();
	}

	@Bean
	public JsonFactory jsonFactory() {
		return JacksonFactory.getDefaultInstance();
	}

	@Bean
	@Autowired
	public GoogleCredential googleCredential(HttpTransport httpTransport, JsonFactory jsonFactory)
			throws IllegalStateException, GeneralSecurityException, IOException {
		return new GoogleCredential.Builder().setTransport(httpTransport).setJsonFactory(jsonFactory)
				.setServiceAccountId(environment.getRequiredProperty("service.account.email"))
				.setServiceAccountPrivateKeyFromP12File(new File(servletContext.getRealPath("") + "\\WEB-INF\\classes\\"
						+ environment.getRequiredProperty("service.account.keyfile")))
				.setServiceAccountScopes(Arrays.asList(PredictionScopes.PREDICTION)).build();
	}

	@Bean
	public Prediction prediction(HttpTransport httpTransport, JsonFactory jsonFactory, GoogleCredential credential) {
		return new Prediction.Builder(httpTransport, jsonFactory, credential)
				.setApplicationName(environment.getRequiredProperty("application.name")).build();
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(converter());
	}

	@Bean
	public MappingJackson2HttpMessageConverter converter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setPrettyPrint(true);
		return converter;
	}

	@Bean
	public BCryptPasswordEncoder encrypt() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthentificationTokenHandlerInterceptor inteceptor() {
		return new AuthentificationTokenHandlerInterceptor();
	}

	@Bean
	public BeanNameUrlHandlerMapping mapping() {
		return new BeanNameUrlHandlerMapping();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(inteceptor()).addPathPatterns("/*");
	}

	@Bean
	public DataSource getDataSource() throws ClassNotFoundException {

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.databaseurl"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));

		return dataSource;
	}
}
