package com.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class AppConfig {

	/* =================  Enable Property Placeholders ==================== */

	/**
	 * PropertySourcesPlaceholderConfigurer resolves ${...} placeholders within bean definition
	 * property values.
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer prop = new PropertySourcesPlaceholderConfigurer();
		prop.setIgnoreUnresolvablePlaceholders(true);
		return prop;
	}

}