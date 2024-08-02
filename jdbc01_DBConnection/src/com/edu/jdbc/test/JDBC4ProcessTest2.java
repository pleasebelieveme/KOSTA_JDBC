package com.edu.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC4ProcessTest2 {
	public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://127.0.0.1:3306/kosta?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
	public static final String USER = "root";
	public static final String PASSWORD = "1234";
	
	public JDBC4ProcessTest2 () {
		
		try {
			// 2.
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("디비연결 성공...");
			
			
			
			
			// 3. 
			// INSERT
			/*
			String query = "INSERT INTO custom (id, name, address) VALUES (?,?,?)";
			PreparedStatement ps = conn.prepareStatement(query); // 미리 쿼리문이 컴파일만 먼저 수행됨...
			System.out.println("PreparedStatement 생성...");
			
			// 4. 바인딩 및 쿼리문 실행
			ps.setInt(1, 444);
			ps.setString(2, "황선우");
			ps.setString(3, "서울");
			
			System.out.println(ps.executeUpdate() + " ROW INSERT OK~~!!");
			*/
			
			
			
			
			// DELETE...id값이 2인 사람을 삭제
			/*
			String query = "DELETE FROM custom WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			System.out.println("PreparedStatement 생성..."); // 실행확인 중요!
			ps.setInt(1, 222);
			
			System.out.println(ps.executeUpdate() + " row DELETE OK~~!!");
			*/
			
			
			
			/*
			// UPDATE... id값이 4인 사람의 정보를 수정... name(오상욱), address(광주)
			String query = "UPDATE custom SET name = ?, address = ? WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			System.out.println("PreparedStatement 생성..."); // 실행확인 중요!
			ps.setString(1, "오상욱");
			ps.setString(2, "광주");
			ps.setInt(3, 444);
			System.out.println(ps.executeUpdate() + " row UPDATE OK~~!!");
			 */

			
			// CREATE는 프로그램에서 하는건 아니다
			
			
			// SELECT 조회하기
			String query = "SELECT id, name, address FROM custom";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			System.out.println("-----------------------------");
			while (rs.next()) {
				System.out.println(rs.getInt("id") + ", " + 
								   rs.getString("name") + ", " +
								   rs.getShort("address")); // 컬럼명으로 대입 인덱스는 헷갈림
			}//while
			System.out.println("-----------------------------");
			
			
			
		} catch (SQLException e) {
			System.out.println("DB Connection Fail...");
		}
		
	}//class
	
	public static void main(String[] args) {
		new JDBC4ProcessTest2();
	}
	
	// 1.
	static {
		try {
			Class.forName(DRIVER_NAME);
			System.out.println("드라이버 로딩 성공...");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패...");
		}
	}

}
