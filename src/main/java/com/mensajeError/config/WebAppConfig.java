package com.mensajeError.config;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.mensajeError.dao.TaskInterface;
import com.mensajeError.service.TaskService;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.mensajeError.controller")
public class WebAppConfig implements ApplicationContextAware{

	
	private ApplicationContext aplicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.aplicationContext = applicationContext;
	}
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}
	
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		//Indicar donde están las "VISTAS"
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(this.aplicationContext);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		return templateResolver;
	}
	
	@Bean SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(this.templateResolver());
		templateEngine.setEnableSpringELCompiler(true);
		return templateEngine;
	}
	@Bean ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(this.templateEngine());
		return viewResolver;
	}
	
	@Bean
	public DataSource getDataSource() {
		try {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.setUrl("jdbc:mysql://localhost:3306/errores?serverTimezone=UTC");
			dataSource.setUsername("root");
			dataSource.setPassword("root");
			return dataSource;	
		}catch(Exception ex) {
			System.out.println("Data Source: "+ex);
			return null;
		}
		
	}
	
	
	@Bean
	public TaskInterface getTask(){
		return new TaskService(this.getDataSource());
	}
	
	
	/*
	@Bean
	public CourseInterface getCourse() {
		return new CourseService(this.getDataSource());
	}
	*/
}
