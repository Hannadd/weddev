package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import vo.PhoneVO;

public class PhoneDAO { // DAO는 DB와 연동하는 기능

	
	private Connection getConnection() { 
		//연결 메소드  url, user, password를 이용해서 연결 
		Connection con= null;
		String url 		= "jdbc:oracle:thin:@localhost:1521:xe";
		String user 	= "ora_user";
		String password = "hong";
		
		try {
			
			con = DriverManager.getConnection(url, user, password);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	private void close(Connection con, PreparedStatement pstmt
			,ResultSet rs ) { // 닫기 메소드(1)
		
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}if (con != null) {
				con.close();
			}
			}catch (SQLException e) {
				System.out.println("Close Error");
			}
	}
	
	private void close(Connection con, PreparedStatement pstmt ) { // 닫기 메소드(2)
		
		try {
			if (pstmt != null) {
				pstmt.close();
			}if (con != null) {
				con.close();
			}
			}catch (SQLException e) {
				System.out.println("Close Error");
				e.printStackTrace();
			}
	}
	
	// 1. 전화번호 목록 전체출력
	public ArrayList<PhoneVO> selectAll(){
		
		ArrayList<PhoneVO> phoneList = new ArrayList();
		
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT B.PHONAME, B.PHONENU, B.GROUPNU, G.GROUPNAME");
		sql.append("	FROM PHONEBOOK B, PHONEGROUP G");
		sql.append("	WHERE B.GROUPNU = G.GROUPNU;");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				PhoneVO phone = new PhoneVO();
				phone.setName(rs.getString("name"));
				phone.setPhoneNumber(rs.getString("phonenumber"));
				phone.setGroupNumber(rs.getString("groupnumber"));
				phone.setGroupName(rs.getString("groupname"));
				phone.setAdress(rs.getString("adress"));
				
				phoneList.add(phone);
				
			}
			}catch (SQLException e) {
				System.out.println("SelectAll Error");
			}finally {
				close(con,pstmt,rs);
			}
		
		return phoneList;
	}
		
	// 2. 전화번호 추가
	public int insertPhone(PhoneVO phone) {
		
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		int rowcnt=0;
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into phonebook(name,phonenumber,groupnumber,address)");
		sql.append("       values('?','?','?','?','?');");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, phone.getName());
			pstmt.setString(2, phone.getPhoneNumber());
			pstmt.setString(3, phone.getGroupNumber());
			pstmt.setString(4, phone.getAddress());
			
			rowcnt = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(con,pstmt);
		}
		return rowcnt;
	}
		
	// 3. 전화번호 검색 (이름 이용)
		public ArrayList<PhoneVO> selectByName(String name) {
			
			Connection con = getConnection();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			ArrayList<PhoneVO> phoneList = new ArrayList<>();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT B.PHONAME, B.PHONENU, B.GROUPNU, G.GROUPNAME");
			sql.append("	FROM PHONEBOOK B, PHONEGROUP G"); 
			sql.append("	WHERE B.GROUPNU = G.GROUPNU"); 
			sql.append("	AND B.PHONAME LIKE '%?%';"); 
			
			
			try {
				pstmt = con.prepareStatement(sql.toString());
				rs = pstmt.executeQuery();
			
			while (rs.next()) {
				PhoneVO phone = new PhoneVO();
				phone.setName(rs.getString("name"));
				phone.setPhoneNumber(rs.getString("phonenumber"));
				phone.setGroupNumber(rs.getString("groupnumber"));
				phone.setGroupName(rs.getString("groupname"));
				phone.setAdress(rs.getString("address"));
				
				phoneList.add(phone);
				
			}
			}catch (SQLException e) {
				System.out.println("selectByName Error");
			}finally {
				close(con,pstmt,rs);
			}
		
		return phoneList;
			
		}
		
	// 4. 전화번호 수정 (이름 이용)
		
		public int reviseNumber(String name, int choice) {
			
			Connection con = getConnection();
			PreparedStatement pstmt = null;
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("UPDATE PHONEBOOK" );
			
			PhoneVO phone = new PhoneVO();
			int rowcnt =0;
			
			try {
				pstmt = con.prepareStatement(sql.toString());
				
				switch (choice) {
				case 1:
					sql.append("SET PHONAME = '?'" );
					pstmt.setString(1, phone.getName());
					sql.append("WHERE PHONAME = '"+name+"';");
				
				case 2:
					sql.append("SET PHONENU = '?'" );
					pstmt.setString(1, phone.getPhoneNumber());
					sql.append("WHERE PHONAME = '"+name+"';");
					
				case 3:
					sql.append("SET GROUPNU = '?'" );
					pstmt.setString(1, phone.getGroupNumber());
					sql.append("WHERE PHONAME = '"+name+"';");
					
				case 4:
					sql.append("SET ADDRESS = '?'" );
					pstmt.setString(1, phone.getAddress());
					sql.append("WHERE PHONAME = '"+name+"';");
					
					rowcnt = pstmt.executeUpdate();
			
				}
			}catch (SQLException e) {
				System.out.println("revise Error");
			}finally {
				close(con,pstmt);
			}
		return rowcnt;
			
		}
		
	// 5. 전화번호 삭제 (이름 이용)
		
		public int deleteNumber(String name) {
			
			Connection con = getConnection();
			PreparedStatement pstmt = null;
			
			StringBuilder sql = new StringBuilder();
			
			int rowcnt = 0;
				try {
					pstmt = con.prepareStatement(sql.toString());
					
					sql.append("DELETE PHONEBOOK");
					sql.append("WHERE PHONAME = '?'; ");
					pstmt.setString(1, name); 
					
					if(rowcnt >0) {
						System.out.println("전화번호 삭제 완료");
					}else {
						System.out.println("전화번호 삭제 실패");
					}
				
				}catch (SQLException e) {
					System.out.println("delete Error");
				}finally {
					close(con,pstmt);
				}
			return rowcnt;
		}
		
		
		
	
	
	
	
}
