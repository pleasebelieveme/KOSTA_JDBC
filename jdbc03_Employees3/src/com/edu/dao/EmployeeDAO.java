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
	
	public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException, SQLIntegrityConstraintViolationException {
		if (rs != null) rs.close();
		closeAll(ps, conn);
	}
	
	
	
	//##################### 가변적인 부분 = 비즈니스 로직
	// isExist() 사용안하는 경우... (쿼리문 2번 동작할 이유가 없다 -> select가 안돌아간다)
	// 데이터 삽입
	public void insertEmployee(Employee emp) throws DMLException, DuplicateNumException { 
		String query = "INSERT INTO employee (num, name, salary, address) VALUES (?,?,?)";
		
		// try가 끝날때 오토 클로징
		try (Connection conn = getConnect(); PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setInt(1, emp.getNum());
			ps.setString(2, emp.getName());
			ps.setFloat(3, emp.getSalary());
			ps.setString(4, emp.getAddress());
			
			ps.executeUpdate();
		} catch(SQLIntegrityConstraintViolationException e) { //중복오류
			throw new DuplicateNumException("이미 고객님은 회원 가입된 상태입니다. 다시 확인해주세요");
		} catch(SQLException e) { // SQL문법오류
			throw new DMLException("회원 가입시 문제가 발생해서 가입이 이루어지지 않았습니다.");
		}
	}
	
	
	
	
	// 데이터 삭제
	public void removeEmployee(int num) throws SQLException, DMLException, RecordNotFoundException {
		
		String query = "DELETE FROM employee WHERE num = ?";
		
		try (Connection conn = getConnect(); PreparedStatement ps = conn.prepareStatement(query)) {
		ps.setInt(1, num);
		int empty = ps.executeUpdate();
		
		if (empty == 0) {
			throw new RecordNotFoundException("삭제할 회원이 존재하지 않습니다."); 
			}
		} catch(SQLException e) { // SQL문법오류
			throw new DMLException("회원 가입시 문제가 발생해서 가입이 이루어지지 않았습니다.");
		}
	}
	
	/*  리스트면 이렇게 사용 가능 지금은 DB이기 때문에 불가
	public void removeEmployee(int num) throws RecordNotFoundException {
		Employee employee = selectEmployee().stream().filter(e -> e.getNum() == num).findAny().orElseThrow(RecordNotFoundException::new);
	list.remove(employee);
	}
	*/
	

	// 데이터 수정
	public void updateEmployee(Employee emp) throws SQLException, RecordNotFoundException, DMLException {
		
		String query = "UPDATE employee SET name = ?, salary = ?, address = ? WHERE num = ?";
		try (Connection conn = getConnect(); PreparedStatement ps = conn.prepareStatement(query)) {
		
		ps.setString(1, emp.getName());
		ps.setFloat(2, emp.getSalary());
		ps.setString(3, emp.getAddress());
		ps.setInt(4, emp.getNum());
		
		ps.executeUpdate();
		
		if (ps.executeUpdate() == 0) {
			throw new RecordNotFoundException("수정할 회원이 존재하지 않습니다.");
			}
		} catch(SQLException e) { // SQL문법오류
			throw new DMLException("회원 가입시 문제가 발생해서 가입이 이루어지지 않았습니다.");
		}
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
