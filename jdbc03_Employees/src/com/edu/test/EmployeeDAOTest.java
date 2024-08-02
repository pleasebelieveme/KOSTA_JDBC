package com.edu.test;

import java.sql.SQLException;

import com.edu.dao.EmployeeDAO;
import com.edu.exception.DuplicateNumException;
import com.edu.vo.Employee;

public class EmployeeDAOTest {

	public static void main(String[] args) {
		EmployeeDAO dao = EmployeeDAO.getInstance();
		// 데이터 삽입
		try {
			dao.insertEmployee(new Employee(111, "One", 1.11F, "원동"));
		} catch (DuplicateNumException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println("쿼리문 실행도중 알 수 없는 문제가 발생했습니다.");
		} 
		
		//		dao.insertEmployee(new Employee(222, "Two", 2.22F, "투동"));
//		dao.insertEmployee(new Employee(333, "Three", 3.33F, "쓰리동"));
//		dao.insertEmployee(new Employee(444, "Four", 4.44F, "포동"));
				
		// 데이터 삭제
//		dao.removeEmployee(111);
		
		// 데이터 수정
//		dao.updateEmployee(new Employee(444, "Five", 5.5F, "파이브동"));
	
		// 모든 데이터 조회
//		ArrayList<Employee> list = dao.selectEmployee();
//		dao.selectEmployee().forEach(i -> System.out.println(i));
		
		// 개별 데이터 조회
//		System.out.println(dao.selectEmployee(111));
	}

}
