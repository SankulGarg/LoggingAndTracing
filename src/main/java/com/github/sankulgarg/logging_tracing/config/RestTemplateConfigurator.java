package com.github.sankulgarg.logging_tracing.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import com.github.sankulgarg.logging_tracing.handlers.HeaderRequestInterceptor;
import com.github.sankulgarg.logging_tracing.manager.LoggersManager;

@Configuration
@Configurable
public class RestTemplateConfigurator {
	Logger			LOGGER	= LoggersManager.getLogger(RestTemplateConfigurator.class);

	@Autowired
	RestTemplate	restTemplate;

	@PostConstruct
	private void postConstruct() {
		try {
			List<ClientHttpRequestInterceptor> list = new ArrayList<>();
			list.add(new HeaderRequestInterceptor());
			restTemplate.setInterceptors(list);

		}
		catch (Exception e) {
			LOGGER.error("error adding ClientHttpRequestInterceptor to restTemplate", e);
		}
	}
}
