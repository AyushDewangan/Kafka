package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Book {

	@Id
	private Integer bookId;
	private String bookName;
	private String bookAuthor;

	@OneToOne
	@JoinColumn(name = "libraryEventId")
	private LibraryEvent libraryEvent;

	public LibraryEvent getLibraryEvent() {
		return libraryEvent;
	}

	public void setLibraryEvent(LibraryEvent libraryEvent) {
		this.libraryEvent = libraryEvent;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookName=" + bookName + ", bookAuthor=" + bookAuthor + "]";
	}

	public Book(Integer bookId, String bookName, String bookAuthor) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
	}

	public Book() {
		super();
	}

}
