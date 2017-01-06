package com.AppContextConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com")
public class AppConfig extends WebMvcConfigurerAdapter  {
		private static final Logger logger = 
				LoggerFactory.getLogger(AppConfig.class);
		@Bean
		public ViewResolver viewResolver() {
			logger.debug("STARTING OF THE METHOD VIEW RESOLVER");

			InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
			viewResolver.setViewClass(JstlView.class);
			viewResolver.setPrefix("/WEB-INF/views/");
			viewResolver.setSuffix(".html");
			logger.debug("ENDING OF THE METHOD VIEW RESOLVER");

			return viewResolver;
		}

}
