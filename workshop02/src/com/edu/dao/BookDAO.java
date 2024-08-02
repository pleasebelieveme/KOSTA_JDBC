package com.edu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import com.edu.exception.DMLException;
import com.edu.exception.DuplicateNumException;
import com.edu.exception.RecordNotFoundException;
import com.edu.vo.Book;

import config.ServerInfo;

public class BookDAO {
	private static BookDAO dao = new BookDAO();
	public BookDAO() {
		try { Class.forName(ServerInfo.DRIVER_NAME); }
		catch (ClassNotFoundException e) {System.out.println("드라이버 로딩 싫패");}
	}
	public static BookDAO getInstance() { return dao; }
	static String driver;
	static String url;
	static String user;
	static String pass;
	static String query;
	Book book;
	
	public Connection getConnect() throws SQLException {
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
//		System.out.println("커넥션 생성 성공");
		return conn;
	}
	public void close(Connection conn) throws SQLException {
		if (conn != null) conn.close();
	}
	public void close(PreparedStatement ps) throws SQLException {
		if (ps != null) ps.close();
	}
	public void close(ResultSet rs) throws SQLException {
		if (rs != null) rs.close();
	}

	public void insertBook(Book book) throws DMLException, DuplicateNumException{
		String query = "INSERT INTO book (isbn, title, author, publisher, price, description) VALUE(?,?,?,?,?,?)";
		
		try (Connection conn = getConnect(); PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, book.getIsbn());
			ps.setString(2, book.getTitle());
			ps.setString(3, book.getAuthor());
			ps.setString(4, book.getPublisher());
			ps.setInt(5, book.getPrice());
			ps.setString(6, book.getDescription());
			
			System.out.println("[ Result OK Message ] => " + ps.executeUpdate() + "row INSERT OK~~!!");
		} catch(SQLIntegrityConstraintViolationException e) {
			throw new DuplicateNumException("[ Result Error Message ] => " + book.getIsbn() + "은 이미 등록된 상태입니다. 다시 확인해주세요");
		} catch(SQLException e) {
			throw new DMLException("[ Result Error Message ] => 문제가 발생해서 요청하신 작업이 정상처리되지 않았습니다.");
		}
	}
	
	public void updateBook(Book book) throws RecordNotFoundException, DMLException {
		String query = "UPDATE book SET title = ?, author = ?, publisher = ?, price = ?, description = ? WHERE isbn = ?";
		
		try (Connection conn = getConnect(); PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getPublisher());
			ps.setInt(4, book.getPrice());
			ps.setString(5, book.getDescription());
			ps.setString(6, book.getIsbn());
			
			ps.executeUpdate();
			
			if (ps.executeUpdate() == 0) {
				throw new RecordNotFoundException("수정할 책이 존재하지 않습니다.");
			}
		} catch(SQLException e) {
			throw new DMLException("문제가 발생해서 요청하신 작업이 정상처리되지 않았습니다.");
		}
	}
	public void deleteBook(String isbn) throws RecordNotFoundException, DMLException {
		String query = "DELETE FROM book WHERE isbn = ?";
		
		// 생성했다는 것이 연다는 뜻이다
		try (Connection conn = getConnect(); PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, isbn);
			
			if (ps.executeUpdate() == 0) {
				throw new RecordNotFoundException("[ Result Error Message ] => 도서 삭제 완료");
			}
		} catch(SQLException e) {
			throw new DMLException("[ Result Error Message ] => 삭제할 도서를 밝견하지 못했습니닼.");
		}
	}
	
	public Book findBook(String isbn) throws SQLException, RecordNotFoundException, DMLException {
		Book book = null;
		String query = "SELECT isbn, title, author, publisher, price, description FROM book WHERE isbn = trim(?)";
		
		try (Connection conn = getConnect();
			 PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, isbn);
			
			try (ResultSet rs = ps.executeQuery()) {
			
			if (rs.next()) {
				book = new Book(isbn,
						  rs.getString("title"), 
				          rs.getString("author"),
				          rs.getString("publisher"),
				          rs.getInt("price"), 
						  rs.getString("description")); 
				} else {
					throw new RecordNotFoundException("[ Result Error Message ] => 일치하는 도서를 발견하지 못했습니다.");
				}
			} catch(SQLException e) {
				throw new DMLException("[ Result Error Message ] => 일치하는 도서를 발견하지 못했습니다.");
			}
		}
		return book;
	}
	
	public ArrayList<Book> listBooks() throws SQLException, DMLException {
		ArrayList<Book> list = new ArrayList<Book>();
		String query = "SELECT isbn, title, author, publisher, price, description FROM book";
		
		try (Connection conn = getConnect();
				 PreparedStatement ps = conn.prepareStatement(query)) {
				
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(new Book(rs.getString("isbn"),
							  rs.getString("title"), 
					          rs.getString("author"),
					          rs.getString("publisher"),
					          rs.getInt("price"), 
							  rs.getString("description"))); 
				}
			}
		} catch(SQLException e) {
			throw new DMLException("[ Result Error Message ] => 일치하는 도서를 발견하지 못했습니다.");
		}
		return list;
	}
	
	public int count() throws DMLException {
		int totalBooks = 0;
		String query = "SELECT count(isbn) count FROM book";
		
		try (Connection conn = getConnect();
				 PreparedStatement ps = conn.prepareStatement(query)) {
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				totalBooks = rs.getInt("count");
//				totalBooks = rs.getInt("1");
			}
		}catch(SQLException e) {
			throw new DMLException("[ Result Error Message ] => 도서의 갯수를 알 수 없습니다");			
		}
		return totalBooks;
//		return listBook().size();
	}
}
