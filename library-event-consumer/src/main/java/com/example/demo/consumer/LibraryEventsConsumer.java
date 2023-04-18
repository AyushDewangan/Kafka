package com.example.demo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.service.LibraryEventsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Component
public class LibraryEventsConsumer {

	@Autowired
	private LibraryEventsService libraryEventsService;
	
    @KafkaListener(topics = {"library-events"})
    public void onMessage(ConsumerRecord<Integer,String> consumerRecord) throws JsonMappingException, JsonProcessingException{

        System.out.println("ConsumerRecord : {} "+ consumerRecord );
        libraryEventsService.processLibraryEvent(consumerRecord);
    }
}
