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
//		dao.insertBook(new Book("a1101", "JAVA 기본", "자앤 기술연구소", 23000, "자앤 출판사", 10));
//		dao.insertBook(new Book("a1102", "JAVA 중급", "자앤 기술연구소", 25000, "자앤 출판사", 10));
//		dao.insertBook(new Book("a1103", "JAVA 실전", "자앤 기술연구소", 30000, "자앤 출판사", 10));
		// 2.
//		System.out.println("++++++++++++++++++++++++++++++++++++++++");
//		printAllBooks(dao.listBooks());
		printAllBooks(dao.listBooks("황금가지"));
//		
//		// 3.
//		System.out.println("++++++++++++++++++++++++++++++++++++++++");
//		System.out.println(dao.findBookByIsbn("a1102"));
//		System.out.println("++++++++++++++++++++++++++++++++++++++++");
//		printAllBooks(dao.findBookByTitle("JAVA"));
//		System.out.println("++++++++++++++++++++++++++++++++++++++++");
//		printAllBooks(dao.findBookByPublisher("자앤"));
//		System.out.println("++++++++++++++++++++++++++++++++++++++++");
//		printAllBooks(dao.findBookByAuthor("김"));
//		System.out.println("++++++++++++++++++++++++++++++++++++++++");
//		printAllBooks(dao.findBookByPrice(20000, 25000));
//		System.out.println("++++++++++++++++++++++++++++++++++++++++");
//		printAllBooks(dao.findBookByPrice(20000));
//		System.out.println("########################################");
//		dao.bargainSale("자앤 출판사", 10);
//		printAllBooks(dao.listBooks());
//		System.out.println("########################################");
//		// 4.
//		System.out.println("++++++++++++++++++++++++++++++++++++++++");
//		dao.insertBook(new Book("a1104", "JAVA 심화", "자앤 기술연구소", 28000, "자앤 출판사", 10));
//		
//		// 5.
//		System.out.println("++++++++++++++++++++++++++++++++++++++++");
//		dao.updateBook(new Book("a1101", "JAVA 기본", "자앤 기술연구소",  20000, "자앤 출판사", 10));
//		printAllBooks(dao.listBooks());
//		
//		// 6.
//		System.out.println("++++++++++++++++++++++++++++++++++++++++");
//		dao.deleteBook("a1103");
//		printAllBooks(dao.listBooks());
//		
//		// 7.
//		System.out.println("++++++++++++++++++++++++++++++++++++++++");
//		System.out.println(dao.count());
		
		// 8. 책 정보 조회
		dao.bookInfo();
	}
	
	private static void printAllBooks(List<Book> list) {
		for (Book book : list) {
			System.out.println(book);
		}
	}
}
	
	
