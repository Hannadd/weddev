package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.EmpVO;

public class EmpDAO { // 데이터 베이스와 연동하는 기능
	private Connection getConnection() {
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
			,ResultSet rs ) {
		
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
	
	private void close(Connection con, PreparedStatement pstmt ) {
		
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
	
	// 1. 사원목록 , 2. 사원추가, 3. 사원수정, 4. 사원삭제    5. 사원명 검색
	public ArrayList<EmpVO> selectAll(){
		ArrayList<EmpVO> empList = new ArrayList();
		
		Connection con = getConnection();
		PreparedStatement pstmt = null;
				ResultSet rs = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("select e.empmo					");
		sql.append("     , e.empnm					");
		sql.append("     , salary				");
		sql.append("     , d.deptno					");
		sql.append("     , d.deptnm					");
		sql.append("  from emp e					"); 
		sql.append("     , dept d					"); 
		sql.append(" where e.deptno = d.deptno   	");
		
		try {
			pstmt= con.prepareStatement(sql.toString());
			rs= pstmt.executeQuery();
			while (rs.next()) { // arraryList 완성
			EmpVO emp = new EmpVO(); // 한 명 사원정보 저장
			emp.setEmpmo(rs.getString("empmo"));
			emp.setEmpnm(rs.getString("empnm"));
			emp.setSalary(rs.getInt("salary"));
			emp.setDeptno(rs.getString("deptno"));
			emp.setDeptnm(rs.getString("deptnm"));
				
				empList.add(emp);
			}
			}catch (SQLException e) {
				System.out.println("SelectAll Error");
			}finally {
				close(con,pstmt,rs);
			}
		
		return empList;
	}
		
	public int insertemp(EmpVO emp) {
		int rowcount = 0;
		return rowcount;
	}
	
	
}
