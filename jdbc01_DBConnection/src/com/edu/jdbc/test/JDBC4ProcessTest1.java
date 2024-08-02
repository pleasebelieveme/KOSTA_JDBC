package com.edu.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * JDBC 기본 4단계 작업
 * 1. 서버정보를 담고 있는... 드라이버 로딩
 * 2. DB서버 연결... Connection객체가 만들어지고 Client로 반환
 * 3. PreparedStatement 객체 생성..쿼리를 실행하는 함수를 가지기 때문에 필요
 * 4. SQL 쿼리문 실행
 */
public class JDBC4ProcessTest1 {

	public JDBC4ProcessTest1 () {
			// 1. 오류를 던저주기 때문에 try-catch로 잡아서 사용해야 한다
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // 드라이버의 실제이름을 인자값으로 넣어줌 FQCN .class확장자를 뺴고
			System.out.println("Driver Loading Success...");
		
			// 2. protocal (jdbc:oracle), (http:) 등등 오라클포트는 1521, kosta뒤의 내용은 한글처리
			String url = 
					"jdbc:mysql://127.0.0.1:3306/kosta?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
			Connection conn = DriverManager.getConnection(url, "root", "1234");
			System.out.println("DBConnection...");
			
			// 3. 
			String query = "INSERT INTO custom (id, name, address) VALUES(?,?,?)";
			PreparedStatement ps = conn.prepareStatement(query); // 실행X, 컴파일O // 실행은 ps.executeUpdate();를 실행해야 쿼리문실행 -> 이때 DB입력
			System.out.println("PreparedStatement Creating...");
			
			// 4. 1)?에 값 바인딩 2)sql 실행.. 이때 DB에 데이터가 입력됨 데이터타입을 알아야 setter가능
			ps.setInt(1, 111);
			ps.setString(2, "JAMES");
			ps.setString(3, "PARIS");
			
			int  row = ps.executeUpdate();
			System.out.println(row + " ROW Record 등록 성공~~!");
			
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Loading Fail...");
		} catch (SQLException e) {
			System.out.println("DB Connection Fail...");
		}
	}//class
	
	public static void main(String[] args) {
		new JDBC4ProcessTest1();
		
		
	}

}
