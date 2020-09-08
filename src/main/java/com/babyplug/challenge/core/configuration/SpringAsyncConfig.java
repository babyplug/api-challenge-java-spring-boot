package com.babyplug.challenge.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class SpringAsyncConfig {

	@Bean("threadCustom")
	public Executor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setMaxPoolSize(20);
		executor.setCorePoolSize(20);
		executor.setQueueCapacity(100);
		executor.setThreadNamePrefix("Sale-");
		executor.initialize();
		return executor;
	}
}
