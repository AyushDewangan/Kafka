package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.domain.LibraryEvent;

public interface LibraryEventsRepository extends CrudRepository<LibraryEvent, Integer> {

}
