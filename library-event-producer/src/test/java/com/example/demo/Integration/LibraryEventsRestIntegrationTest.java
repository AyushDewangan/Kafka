package com.example.demo.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.test.context;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import com.example.demo.domain.Book;
import com.example.demo.domain.LibraryEvent;
import com.example.demo.domain.LibraryEventType;

//@ContextConfiguration(classes = LibraryEventsRestIntegrationTest.class)
//@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = {"library-events"}, partitions = 3)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-server=${spring.embedded.kafka.brokers}",
		"spring.kafka.admin.properties.bootstrap-server=${spring.embedded.kafka.brokers}"})
//Embedded broker not start for the test cases due to kafka embedded annotation not working.
//@RunWith(SpringRunner.class)

class LibraryEventsRestIntegrationTest {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Test
	void postLibarryEvent() {

		// Generate Body/Payload 
		Book book = new Book();
		book.setBookId(1001);
		book.setBookName("Java");
		book.setBookAuthor("BS");
		LibraryEvent libraryEvent = new LibraryEvent();
		libraryEvent.setLibraryEventId(1);
		libraryEvent.setLibraryEventType(LibraryEventType.NEW);
		libraryEvent.setBook(book);
		
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.set("content-type", MediaType.APPLICATION_JSON.toString());
		HttpEntity<LibraryEvent> request = new HttpEntity<LibraryEvent>(libraryEvent, headers);
		// Hit api
		ResponseEntity<LibraryEvent> response = restTemplate.exchange("/v1/libraryevent", HttpMethod.POST, request, LibraryEvent.class);
		// response checking
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
}