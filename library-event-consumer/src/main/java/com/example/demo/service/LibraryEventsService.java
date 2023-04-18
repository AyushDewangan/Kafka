package com.example.demo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface LibraryEventsService {

	public void processLibraryEvent(ConsumerRecord<Integer, String> consumerRecord) throws JsonMappingException, JsonProcessingException;
}
