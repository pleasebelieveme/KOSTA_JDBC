package com.edu.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Stream;

import com.edu.vo.Custom;

import config.ServerInfo;

public class SimpleJDBCTest {
	static String driver;
	static String url;
	static String user;
	static String pass;
	static String query;
	
	public SimpleJDBCTest() throws Exception {
		// 1. 드라이버 로딩.
		Class.forName(ServerInfo.DRIVER_NAME);
		System.out.println("1. 드라이버 로딩... 성공");

	}
	// 고정적으로 각 메소드마다 반복되는 로직을 공통로직으로 정의 
	public Connection getConnect() throws SQLException {
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
		System.out.println("디비 연결... 성공");
		return conn;
	}
	
	public void closeAll(PreparedStatement ps, Connection conn) throws SQLException {
		if (ps != null) ps.close();
		if (conn != null) conn.close();
	}
	
	public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		if (rs != null) rs.close();
		closeAll(ps, conn);
	}
	
	public void addCustom(Custom c) throws SQLException {
		// insert into...
		Connection conn = null;
		PreparedStatement ps = null;
		// 1. 디비연결		
		conn = getConnect();
		
		// 2. PreparedStatement 생성
		String query = "INSERT INTO custom (id, name, address) VALUES (?,?,?)";
		ps = conn.prepareStatement(query);
		System.out.println("PreparedStatement 생성...");
		
		// 3. 바인딩
		ps.setInt(1, c.getId());
		ps.setString(2, c.getName());
		ps.setString(3, c.getAddress());
		
		// 4. 쿼리문 실행
		ps.executeUpdate();
		
		// 5 자원반환
		System.out.println(ps.executeUpdate() + " row UPDATE OK~~!!");
		closeAll(ps, conn);
	}
	
	public Custom getCustom(int id) throws SQLException {
		Custom custom = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		// select where
		// 1. 디비연결
//		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
//		System.out.println("2. 디비 연결... 성공");
		conn = getConnect();
		// 2. PreparedStatement 생성
		String query = "SELECT id, name, address FROM custom WHERE id = ?";
		ps = conn.prepareStatement(query);
		System.out.println("PreparedStatement 생성...");
		// 3. 바인딩
		ps.setInt(1, id);
		
		// 4. 쿼리문 실행
		rs = ps.executeQuery();
		if(rs.next()) {
			custom = new Custom(
					id,
					rs.getString("name"), 
					rs.getString("address"));
		}
		// 5 자원반환
		closeAll(rs, ps, conn);
		return custom;
		
		
	}
	
	
	public ArrayList<Custom> getCustom() throws SQLException {
		ArrayList<Custom> list = new ArrayList<Custom>();
		// select where
		// 1. 디비연결
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
		System.out.println("2. 디비 연결... 성공");
		// 2. PreparedStatement 생성
		String query = "SELECT id, name, address FROM custom";
		PreparedStatement ps = conn.prepareStatement(query);
		System.out.println("PreparedStatement 생성...");
		// 3. 바인딩 WHERE이 없기에 필요X
		
		// 4. 쿼리문 실행
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			list.add(new Custom(rs.getInt("id"),
								rs.getString("name"),
								rs.getString("address")));
		}
		// 5 자원반환
		conn.close();
		return list;
	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		SimpleJDBCTest dao = new SimpleJDBCTest();
//		dao.addCustom(new Custom(555, "Blake", "LA"));
		Custom custom = dao.getCustom(555);
		System.out.println(custom);
		dao.getCustom().forEach(i -> System.out.println(i));
		
	}

	
	
	
	
	
	static {
		// 1. properties파일을 로드한다.
		try {
			Properties p = new Properties();
			p.load(new FileInputStream("src/config/db.properties")); // filePath System -> src명시필요!
//			p.load(new FileInputStream("src/sql/custom.properties"));
			
			driver = p.getProperty("jdbc.mysql.driver");
			url = p.getProperty("jdbc.mysql.url");
			user = p.getProperty("jdbc.mysql.user");
			pass = p.getProperty("jdbc.mysql.pass");
//			query = p.getProperty("jdbc.sql.selectAll");
			
		} catch (Exception e) {
			System.out.println("드라이버 로딩 실패...");
			
		}
	}
}
