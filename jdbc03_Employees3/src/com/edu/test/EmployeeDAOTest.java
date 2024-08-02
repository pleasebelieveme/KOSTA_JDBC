package com.edu.test;

import java.sql.SQLException;

import com.edu.dao.EmployeeDAO;
import com.edu.exception.DMLException;
import com.edu.exception.RecordNotFoundException;
import com.edu.vo.Employee;

public class EmployeeDAOTest {

	public static void main(String[] args) throws SQLException {
		EmployeeDAO dao = EmployeeDAO.getInstance();
		
		
		
		// 데이터 삽입
//		try {
//			dao.insertEmployee(new Employee(100, "One", 1.11F, "원동"));
//		} catch (DuplicateNumException e ) {
//			System.out.println(e.getMessage());
//		} catch (DMLException e) {
//			System.out.println(e.getMessage());
//		}
//		dao.insertEmployee(new Employee(222, "Two", 2.22F, "투동"));
//		dao.insertEmployee(new Employee(333, "Three", 3.33F, "쓰리동"));
//		dao.insertEmployee(new Employee(444, "Four", 4.44F, "포동"));
				
		
		
		
		// 데이터 삭제
//		try {
//			dao.removeEmployee(888);
//		} catch(RecordNotFoundException e) {
//			System.out.println(e.getMessage());
//		} catch(DMLException e) {
//			System.out.println(e.getMessage());
//		}
		
		
		
		
		
		// 데이터 수정
		try {
			dao.updateEmployee(new Employee(222, "Five", 5.5F, "파이브동"));
		} catch(RecordNotFoundException e) {
			System.out.println(e.getMessage());
		} catch(DMLException e) {
			System.out.println(e.getMessage());
		}
	
		// 모든 데이터 조회
//		ArrayList<Employee> list = dao.selectEmployee();
//		dao.selectEmployee().forEach(i -> System.out.println(i));
		
		// 개별 데이터 조회
//		System.out.println(dao.selectEmployee(111));
	}

}
