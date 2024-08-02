package com.edu.vo;

public class Employee {
	private int num;
	private String name;
	private float salary;
	private String address;
	
	public Employee() {}
	public Employee(int num, String name, float salary, String address) {
		super();
		this.num = num;
		this.name = name;
		this.salary = salary;
		this.address = address;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "EmployeeDAO [num=" + num + ", name=" + name + ", salary=" + salary + ", address=" + address + "]";
	}
}
