package com.example.demo.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class LibraryEvent {

	@Id
	@GeneratedValue
	private Integer libraryEventId;
	@Enumerated(EnumType.STRING)
	private LibraryEventType libraryEventType;
	@OneToOne(mappedBy = "libraryEvent", cascade = { CascadeType.ALL })
//	@ToString.Exclude
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
