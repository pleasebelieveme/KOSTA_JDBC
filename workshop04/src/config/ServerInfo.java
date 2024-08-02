package config;

public interface ServerInfo {
	// 파편화된 정보는 properties 사용
	String DRIVER_NAME = "com.mysql.cj.jdbc.Driver"; // public static final이 생략
	String URL = "jdbc:mysql://127.0.0.1:3306/kosta?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
	String USER = "root";
	String PASSWORD = "1234";
}
