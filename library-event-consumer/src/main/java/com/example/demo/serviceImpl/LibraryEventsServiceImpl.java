package com.example.demo.serviceImpl;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hibernate.metamodel.relational.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.LibraryEvent;
import com.example.demo.repository.LibraryEventsRepository;
import com.example.demo.service.LibraryEventsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LibraryEventsServiceImpl implements LibraryEventsService {

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	private LibraryEventsRepository libraryEventsRepository;

	@Override
	public void processLibraryEvent(ConsumerRecord<Integer, String> consumerRecord)
			throws JsonMappingException, JsonProcessingException {

		LibraryEvent libraryEvent = objectMapper.readValue(consumerRecord.value(), LibraryEvent.class);
		System.out.println("libraryEvent : " + libraryEvent);
		
		switch(libraryEvent.getLibraryEventType()) {
			case NEW:
				//save operation
				save(libraryEvent);
				break;
			case UPDATE:
				//update operation
				validation(libraryEvent);
				save(libraryEvent);
				break;
			default:
				System.out.println("Invalid LibraryEvent Type.");
		}
	}

	private void validation(LibraryEvent libraryEvent) {
		if(libraryEvent.getLibraryEventId() == null)
			throw new IllegalIdentifierException("Library Event Id missing.");
		Optional<LibraryEvent> libraryEvents = libraryEventsRepository.findById(libraryEvent.getLibraryEventId());
		if(!libraryEvents.isPresent())
			throw new IllegalIdentifierException("Not a valid Library Event.");
		System.out.println("Successfully validate the update request.");
	}

	private void save(LibraryEvent libraryEvent) {
		libraryEvent.getBook().setLibraryEvent(libraryEvent);
		libraryEventsRepository.save(libraryEvent);
		System.out.println("Successfully persisted the library events : "+libraryEvent);
	}

}
