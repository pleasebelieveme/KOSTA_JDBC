package broker.twotier.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import broker.twotier.exception.DuplicateSSNException;
import broker.twotier.exception.InvalidTransactionException;
import broker.twotier.exception.RecordNotFoundException;
import broker.twotier.vo.CustomerRec;
import broker.twotier.vo.SharesRec;
import broker.twotier.vo.StockRec;
import config.ServerInfo;

public class DatabaseOriginal implements DatabaseTemplate{
	
	private static DatabaseOriginal db = new DatabaseOriginal();
	private DatabaseOriginal() {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("드라이버 로딩 성공..");
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패..");
		}
	}
	
	public static DatabaseOriginal getInstance() {
		return db;
	}
	@Override
	public Connection getConnect() throws SQLException {
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
		System.out.println("디비 연결 성공...");
		return conn;
	}

	@Override
	public void closeAll(PreparedStatement ps, Connection conn) throws SQLException {
		if(ps!=null) ps.close();
		if(conn!=null) conn.close();
	}

	@Override
	public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		if(rs!=null) rs.close();
		closeAll(ps, conn);
	}
	// 존재유무 확인하는 비지니스 로직..
	private boolean isExist(String ssn, Connection conn) throws SQLException{
		String query = "SELECT ssn FROM customer WHERE ssn = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, ssn);
		
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}
	/////////////////////// 비지니스 로직 /////////////////////////
	@Override
	public void addCustomer(CustomerRec cust) throws SQLException, DuplicateSSNException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		
		try {
			conn = getConnect();
			if(!isExist(cust.getSsn(), conn)) { // 추가하려는 ssn이 존재하지 않는다면..
				String query = "INSERT INTO customer (ssn, cust_name, address) VALUES (?,?,?)";
				ps = conn.prepareStatement(query);
				
				ps.setString(1, cust.getSsn());
				ps.setString(2, cust.getName());
				ps.setString(3, cust.getAddress());
				
				System.out.println(ps.executeUpdate() + "명 addCustomer() OK~~!!");
			} else {
				throw new DuplicateSSNException("[Error]고객님은 이미 회원이십니다.");
			}
		} finally {
			closeAll(ps, conn);
		}
		
	}

	@Override
	public void deleteCustomer(String ssn) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		
		try {
			conn = getConnect();
			if(isExist(ssn, conn)) { // 추가하려는 ssn이 존재하지 않는다면..
				String query = "DELETE FROM customer WHERE ssn = ?";
				ps = conn.prepareStatement(query);
				
				ps.setString(1, ssn);
				
				System.out.println(ps.executeUpdate() + "명 deleteCustomer() OK~~!!");
			} else {
				throw new RecordNotFoundException("[Error] 존재하지 않는 회원이십니다.");
			}
		} finally {
			closeAll(ps, conn);
		}
		
	}

	@Override
	public void updateCustomer(CustomerRec cust) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		
		try {
			conn = getConnect();
			if(isExist(cust.getSsn(), conn)) { // 추가하려는 ssn이 존재하지 않는다면..
				String query = "UPDATE customer SET cust_name = ?, address = ? WHERE ssn = ?";
				ps = conn.prepareStatement(query);
				
				ps.setString(1, cust.getName());
				ps.setString(2, cust.getAddress());
				ps.setString(3, cust.getSsn());
				
				System.out.println(ps.executeUpdate() + "명 updateCustomer() OK~~!!");
			} else {
				throw new RecordNotFoundException("[Error] 존재하지 않는 회원이십니다.");
			}
		} finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public ArrayList<SharesRec> getPortfolio(String ssn) throws SQLException {
		ArrayList<SharesRec> slist = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConnect();
			if(isExist(ssn, conn)) {
				String query = "SELECT ssn, symbol, quantity FROM shares WHERE ssn = ?";
				ps = conn.prepareStatement(query);
				ps.setString(1, ssn);
				rs = ps.executeQuery();
				
				while (rs.next()) {
					slist.add(new SharesRec(rs.getString("ssn"),
					         rs.getString("symbol"), 
							 rs.getInt("quantity")));					
				}
//				System.out.println(ps.executeQuery() + "          명 getPortfolio() OK~~!!");
			} else {
				throw new SQLException("[Error] 존재하지 않는 회원이십니다.");
			}
		} finally {
			closeAll(ps, conn);
		}
		return slist;
	}

	@Override
	public CustomerRec getCustomer(String ssn) throws SQLException {
		CustomerRec clist = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConnect();
			if(isExist(ssn, conn)) {
				String query = "SELECT ssn, cust_name, address FROM customer WHERE ssn = ?";
				ps = conn.prepareStatement(query);
				ps.setString(1, ssn);
				rs = ps.executeQuery();
				
				if (rs.next()) {
					clist = new CustomerRec(rs.getString("ssn"),
					         rs.getString("cust_name"), 
							 rs.getString("address"));	
					clist.setPortfolio(getPortfolio(ssn));
				}
//				System.out.println(ps.executeQuery() + "          명 getPortfolio() OK~~!!");
			} else {
				throw new SQLException("[Error] 존재하지 않는 회원이십니다.");
			}
		} finally {
			closeAll(ps, conn);
		}
		
		return clist;
	}

	@Override
	public ArrayList<CustomerRec> getAllCustomers() throws SQLException {
		ArrayList<CustomerRec> alllist = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConnect();
			String query = "SELECT ssn, cust_name, address FROM customer";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				String ssn = rs.getString("ssn");
				ArrayList<SharesRec> shares = getPortfolio(ssn);
				alllist.add(new CustomerRec(ssn,
						  rs.getString("cust_name"), 
				          rs.getString("address"),
				          shares));

			
			}
//				System.out.println(ps.executeQuery() + "명 getAllCustomers() OK~~!!");
			} finally {
			closeAll(ps, conn);
		}
		return alllist;
	}

	@Override
	public ArrayList<StockRec> getAllStocks() throws SQLException {
		ArrayList<StockRec> allslist = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConnect();
			String query = "SELECT symbol, price FROM stock";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				allslist.add(new StockRec(rs.getString("symbol"),
				          			rs.getFloat("price")));
			} 
//			System.out.println(ps.executeQuery() + "명 getAllStock() OK~~!!");
		} finally {
			closeAll(ps, conn);
		}
		return allslist;
	}

	
	
	//0802JDBC끝
	@Override
	public void buyShares(String ssn, String symbol, int quantity) throws SQLException {
		
	}

	@Override
	public void sellShares(String ssn, String symbol, int quantity) throws SQLException, InvalidTransactionException, RecordNotFoundException {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws SQLException, DuplicateSSNException, RecordNotFoundException {//단위테스트용...
		DatabaseOriginal db = DatabaseOriginal.getInstance();
//		System.out.println(db.isExist(null, null));
//		db.addCustomer(new CustomerRec("111-110", "Yufirst10", "Seoul"));
//		db.updateCustomer(new CustomerRec("111-110", "Yufirst10", "Busan"));
//		db.deleteCustomer("111-110");
//		db.getPortfolio("111-111").stream().forEach(System.out::print);
//		CustomerRec cust = db.getCustomer("111-111");
//		System.out.println(cust.toString());
		db.getAllCustomers().stream().forEach(System.out::println);
//		db.getAllStocks().stream().forEach(System.out::println);
		
	}
	
}
