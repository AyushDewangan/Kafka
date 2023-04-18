package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.LibraryEvent;
import com.example.demo.domain.LibraryEventType;
import com.example.demo.producer.LibraryEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/v1")
public class LibraryEventsRest {
	
	@Autowired
	LibraryEventProducer libraryEventProducer;

	@PostMapping("/libraryevent")
	public ResponseEntity<LibraryEvent> createLibraryEvents(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException {
		System.out.println("enter in createLibraryEvents Rest");
		libraryEvent.setLibraryEventType(LibraryEventType.NEW);
		libraryEventProducer.sendLibraryEvent(libraryEvent);
//		libraryEventProducer.sendLibraryEvent_Approach2(libraryEvent);		//approach2
		System.out.println("exit in createLibraryEvents Rest");
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
	}
	
	@PutMapping("/libraryevent")
	public ResponseEntity<?> UpdateLibraryEvents(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException {
		System.out.println("enter in UpdateLibraryEvents Rest");
		
		if(libraryEvent.getLibraryEventId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please pass the LibraryEventId.");
		}
		
		libraryEvent.setLibraryEventType(LibraryEventType.NEW);
		libraryEventProducer.sendLibraryEvent(libraryEvent);
//		libraryEventProducer.sendLibraryEvent_Approach2(libraryEvent);		//approach2
		System.out.println("exit in createLibraryEvents Rest");
		return ResponseEntity.status(HttpStatus.OK).body(libraryEvent);
	}
}
