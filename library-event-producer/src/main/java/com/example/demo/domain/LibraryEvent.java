package com.example.demo.domain;

import jakarta.annotation.Nonnull;

public class LibraryEvent {

	private Integer libraryEventId;
	private LibraryEventType libraryEventType;
	@Nonnull
	private Book book;

	public Integer getLibraryEventId() {
		return libraryEventId;
	}

	public void setLibraryEventId(Integer libraryEventId) {
		this.libraryEventId = libraryEventId;
	}
	
	public LibraryEventType getLibraryEventType() {
		return libraryEventType;
	}

	public void setLibraryEventType(LibraryEventType libraryEventType) {
		this.libraryEventType = libraryEventType;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "LibraryEvent [libraryEventId=" + libraryEventId + ", libraryEventType=" + libraryEventType + ", book="
				+ book + "]";
	}

	public LibraryEvent(Integer libraryEventId, Book book) {
		super();
		this.libraryEventId = libraryEventId;
		this.book = book;
	}

	public LibraryEvent() {
		super();
	}

}
