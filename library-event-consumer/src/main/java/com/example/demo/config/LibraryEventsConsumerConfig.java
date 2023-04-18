package com.example.demo.config;

import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableKafka
public class LibraryEventsConsumerConfig {
	
	/*
	 * the below method retry to process every 1 sec(1000L) and attempt no. is 2
	 */
	public DefaultErrorHandler errorHandle() {
		var fixedBackOff = new FixedBackOff(1000L,2);
		return new DefaultErrorHandler(fixedBackOff);
	}


	/*
	 * Way to scalling up the kafka-poll generally we have one listener but, we can
	 * scalling up this mathod with help of override this default method, here we
	 * set concurrency as a three that means now it has 3 theads of polls.
	 */
	@Bean
	ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
			ConsumerFactory<Object, Object> kafkaConsumerFactory) {
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		configurer.configure(factory, kafkaConsumerFactory);
		factory.setConcurrency(3);
		factory.setCommonErrorHandler(errorHandle());
		return factory;
	}

}
