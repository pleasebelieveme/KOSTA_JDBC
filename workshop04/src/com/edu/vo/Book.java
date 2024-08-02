package com.edu.vo;

public class Book {
	private String isbn;
	private String title;
	private int authorno;
	private String publisher;
	private int price;
	private String description;
	private String author;
	private String name;
	private String phone;
//	Author author;
	
	public Book() {}
	public Book(String isbn, String title, String publisher, int price, String description, int authorno, String name, String phone) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.publisher = publisher;
		this.price = price;
		this.description = description;
		this.authorno = authorno;
		this.name = name;
		this.phone = phone;
	}
	public Book(String string, String string2, String string3, int int1, String string4, int int2, String string5) {
		// TODO Auto-generated constructor stub
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getAuthorno() {
		return authorno;
	}
	public void setAuthorno(int authorno) {
		this.authorno = authorno;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", title=" + title + ", authorno=" + authorno + ", publisher=" + publisher
				+ ", price=" + price + ", description=" + description + ", name=" + name + ", phone=" + phone + "]";
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
}    
