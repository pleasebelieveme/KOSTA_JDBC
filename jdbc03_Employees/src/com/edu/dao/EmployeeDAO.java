package com.edu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.edu.exception.DuplicateNumException;
import com.edu.vo.Employee;

import config.ServerInfo;

public class EmployeeDAO {
	private static EmployeeDAO dao = new EmployeeDAO();
	
	public EmployeeDAO() {
		// 드라이버 로딩
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
//			System.out.println("드라이버 로딩 성공");
		} catch (ClassNotFoundException e){
//			System.out.println("드라이버 로딩 싫패");
		}
	}

	public static EmployeeDAO getInstance() {
		return dao;
	}
	
	static String driver;
	static String url;
	static String user;
	static String pass;
	static String query;
	Employee emp;
	

	//################ 공통적인 부분
	// Connection 생성
	public Connection getConnect() throws SQLException {
		
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
//		System.out.println("커넥션 생성 성공");
		return conn;
	}
	
	
	
	// 반환
	public void closeAll(PreparedStatement ps, Connection conn) throws SQLException {
		if (ps != null) ps.close();
		if (conn != null) conn.close();
	}
	
	public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		if (rs != null) rs.close();
		closeAll(ps, conn);
	}
	
	
	
	//##################### 가변적인 부분 = 비즈니스 로직
	private boolean isExist(int num, Connection conn) throws SQLException { // 한국개발자들은 반환값을 int로 한다, PK에 해당하는 사람의 존재여부를 확인하는 함수
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT num FROM employee WHERE num = ?";
		
		ps = conn.prepareStatement(query);
		ps.setInt(1, num);
		rs = ps.executeQuery();
		
		return rs.next();
		// select절은 안써도 무방하다 없으면 안가져오고 있으면 출력해주면 되기 떄문에

	}
	
	
	
	// 데이터 삽입
	public void insertEmployee(Employee emp) throws SQLException, RuntimeException, DuplicateNumException { 
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
	//		System.out.println("디비 연결 성공");
			
			if (!isExist(emp.getNum(), conn)) { // 인자값 conn추가
			
			String query = "INSERT INTO employee (num, name, salary, address) VALUES (?,?,?,?)";
			ps = conn.prepareStatement(query);
	//		System.out.println("PrepareStatement 생성 성공");
			
			ps.setInt(1, emp.getNum());
			ps.setString(2, emp.getName());
			ps.setFloat(3, emp.getSalary());
			ps.setString(4, emp.getAddress());
			
			ps.executeUpdate();
	//		System.out.println(ps.executeUpdate() + "줄 추가 성공");
			} else {
				throw new DuplicateNumException(emp.getName() + " 님은 이미 회원입니다.");
			}
		} finally {
			closeAll(ps, conn); // 무조건 자원 반환 finally
		}
	}
	
	// 데이터 삭제
	public void removeEmployee(int num) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		conn = getConnect();
		System.out.println("디비 연결 성공");
		
		String query = "DELETE FROM employee WHERE num = ?";
		ps = conn.prepareStatement(query);
		System.out.println("PrepareStatement 생성 성공");
		
		ps.setInt(1, num);
		
		ps.executeUpdate();
		System.out.println(ps.executeUpdate() + "줄 삭제 성공");
		closeAll(ps, conn);
	}
	
	// 데이터 수정
	public void updateEmployee(Employee emp) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		conn = getConnect();
		System.out.println("디비 연결 성공");
		
		String query = "UPDATE employee SET name = ?, salary = ?, address = ? WHERE num = ?";
		ps = conn.prepareStatement(query);
		System.out.println("PrepareStatement 생성 성공");
		
		ps.setString(1, emp.getName());
		ps.setFloat(2, emp.getSalary());
		ps.setString(3, emp.getAddress());
		ps.setInt(4, emp.getNum());
		
		
		ps.executeUpdate();
		System.out.println(ps.executeUpdate() + " : 데이터 수정 성공");
		closeAll(ps, conn);
	}
	
	// 모든 데이터 조회
	public ArrayList<Employee> selectEmployee() throws SQLException {
		ArrayList<Employee> list = new ArrayList<Employee>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		conn = getConnect();
//		System.out.println("디비 연결 성공");
		
		String query = "SELECT num, name, salary, address FROM employee";
		ps = conn.prepareStatement(query);
//		System.out.println("PrepareStatement 생성 성공");
		
		rs = ps.executeQuery();
		
		while(rs.next()) {
			list.add(new Employee(rs.getInt("num"),
								  rs.getString("name"),
								  rs.getFloat("salary"),
								  rs.getString("address")));
		}
		closeAll(rs, ps, conn);
		return list;
	}
	
	public Employee selectEmployee(int num) throws SQLException {
		Employee emp = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		conn = getConnect();
		String query = "SELECT num, name, salary, address FROM employee WHERE num = ?";
		ps = conn.prepareStatement(query);
		ps.setInt(1, num);
		
		rs = ps.executeQuery();
		
		if(rs.next()) {
			emp = new Employee(num,
					          rs.getString("name"),
					          rs.getFloat("salary"),
					          rs.getString("address")); 
		}
		
		closeAll(rs, ps, conn);
		
		return emp;
	}
}
