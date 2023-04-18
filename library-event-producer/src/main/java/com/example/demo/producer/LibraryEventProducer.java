package com.example.demo.producer;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.example.demo.domain.LibraryEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
//@Slf4j
public class LibraryEventProducer {

	@Autowired
	KafkaTemplate<Integer, String> kafkaTemplate;

	@Autowired
	ObjectMapper objectMapper;
	
	String topic = "library-events";	//Initialize for approach 2

	/*Test Something -- approach : 1 -- Asynchronous thread Working fine.*/
	public void sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {
		System.out.println("method sendLibraryEvent enterd.");
		Integer key = libraryEvent.getLibraryEventId();
		String value = objectMapper.writeValueAsString(libraryEvent);
//		kafkaTemplate.sendDefault(key, value);
		CompletableFuture<SendResult<Integer, String>> completableFuture = CompletableFuture.supplyAsync(() -> {
		    try {
		        return kafkaTemplate.sendDefault(key, value).get();
		    } catch (InterruptedException | ExecutionException e) {
		        throw new RuntimeException(e);
		    }
		});

		completableFuture.whenComplete((result, ex) -> {
		    if (ex != null) {
		        handleFailure(key, value, ex);
		    } else {
		        handleSuccess(key, value, result);
		    }
		});

	

		System.out.println("method sendLibraryEvent completed.");
	}
	
	/*Test Something -- approach : 2 -- Synchronous thread Working fine.*/
	public void sendLibraryEvent_Approach2(LibraryEvent libraryEvent) throws JsonProcessingException {
		System.out.println("method sendLibraryEvent enterd.");
		Integer key = libraryEvent.getLibraryEventId();
		String value = objectMapper.writeValueAsString(libraryEvent);
		ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, topic);
		CompletableFuture<SendResult<Integer, String>> completableFuture = CompletableFuture.supplyAsync(() -> {
		    try {
//		        return kafkaTemplate.sendDefault(key, value).get();				//topic set in properties
//		        return kafkaTemplate.send("library-events",key, value).get();	//topic given here
		    	return kafkaTemplate.send(producerRecord).get();
		    } catch (InterruptedException | ExecutionException e) {
		        throw new RuntimeException(e);
		    }
		});
		completableFuture.whenComplete((result, ex) -> {
		    if (ex != null) {
		        handleFailure(key, value, ex);
		    } else {
		        handleSuccess(key, value, result);
		    }
		});
		System.out.println("method sendLibraryEvent completed.");
	}

	private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {
		// RecoredHeader used for information for consumer.
		List<Header> recordHeader = List.of(new RecordHeader("event-source", "scanner".getBytes())); 
		return new ProducerRecord<>(topic, null, key, value, recordHeader);
	}

	private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
		System.out.println("Message Sent SuccessFully for the key : "+key+ ", and the value is : "+value +" , partition is : " +result.getRecordMetadata().partition());
	}

	private void handleFailure(Integer key, String value, Throwable ex) {
		 System.out.println("Error Sending the Message and the exception is : "+ ex.getMessage());
	        try {
	            throw ex;
	        } catch (Throwable throwable) {
	            System.out.println("Error in OnFailure: "+ throwable.getMessage());
	        }
	}
}
