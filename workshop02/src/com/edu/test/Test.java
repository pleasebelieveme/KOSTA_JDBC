package com.edu.test;

import java.sql.SQLException;
import java.util.List;

import com.edu.dao.BookDAO;
import com.edu.exception.DMLException;
import com.edu.exception.DuplicateNumException;
import com.edu.exception.RecordNotFoundException;
import com.edu.vo.Book;

public class Test {

	public static void main(String[] args) throws SQLException, RecordNotFoundException, DMLException, DuplicateNumException {
		BookDAO dao = BookDAO.getInstance();
		
		// 1.
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		dao.insertBook(new Book("a1101", "JAVA 기본", "자앤 기술연구소", "자앤 출판사", 23000, "기본"));
		dao.insertBook(new Book("a1102", "JAVA 중급", "자앤 기술연구소", "자앤 출판사", 25000, "중급"));
		dao.insertBook(new Book("a1103", "JAVA 실전", "자앤 기술연구소", "자앤 출판사", 30000, "실전"));
		// 2.
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		printAllBooks(dao.listBooks());
		
		// 3.
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		System.out.println(dao.findBook("a1102"));
		
		// 4.
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		dao.insertBook(new Book("a1104", "JAVA 심화", "자앤 기술연구소", "자앤 출판사", 28000, "심화"));
		
		// 5.
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		dao.updateBook(new Book("a1101", "JAVA 기본", "자앤 기술연구소", "자앤 출판사", 20000, "기본"));
		printAllBooks(dao.listBooks());
		
		// 6.
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		dao.deleteBook("a1103");
		printAllBooks(dao.listBooks());
		
		// 7.
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		System.out.println(dao.count());
	}
	
	private static void printAllBooks(List<Book> list) {
		for (Book book : list) {
			System.out.println(book);
		}
	}
}
	
	
