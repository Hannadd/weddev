import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.EmpVO;

public class Test_DB_01 {
	
	//1. connection 분리 - return 해야 다른 데서 씀
	
	public static Connection getConnection() {
		
		String url 		= "jdbc:oracle:thin:@localhost:1521:xe";
		String user 	= "ora_user";
		String password = "hong";
		Connection con 			= null; //밖에 선언해야 오류가 나도 선언이 가능
		
		try {
			con = DriverManager.getConnection(url, user, password);
		}catch(SQLException e) {
			System.out.println("Connection Error");
		}
		return con;
	}
	
	//2. close() 분리 - 오버로딩이 되어야 (이름동일, 파라미터 상이)
	
	public static void close(Connection con, PreparedStatement pstmt
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
	
	public static void close(Connection con, PreparedStatement pstmt ) {
		
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
	
	//3. 출력 분리
	
	public static void printEmp(ArrayList<EmpVO> empList) {
		for (int i=0; i<empList.size(); i++) {
			System.out.println(empList.get(i).toString());
		}
		
	}
	
	
	
	//4. 전사원 조회
	public static void selectAll() {
		Connection con = getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rs			= null;
		
		ArrayList<EmpVO> empList = new ArrayList<>();
		
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
			rs=pstmt.executeQuery();
			while (rs.next()) { // arraryList 완성
				EmpVO emp = new EmpVO(); // 한 명 사원정보 저장
				emp.setEmpmo(rs.getString("empmo"));
				emp.setEmpnm(rs.getString("empnm"));
				emp.setSalary(rs.getInt("salary"));
				emp.setDeptno(rs.getString("deptno"));
				emp.setDeptnm(rs.getString("deptnm"));
				
				empList.add(emp);
			}
			printEmp(empList);
		}catch (SQLException e) {
			System.out.println("SelectAll Error");
		}finally {
			close(con,pstmt,rs);
		}
		
	}
	
	//5. 특정 사원명으로 조회
	public static void SelectByName(String empnm) {
		Connection con = getConnection();
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		ArrayList<EmpVO> empList = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT E.EMPMO				");
		sql.append("	,E.EMPNM				");
		sql.append("	,E.salary				");
		sql.append("	,d.deptno				");
		sql.append("	,d.deptnm				");
		sql.append(" FROM EMP E 				");
		sql.append("	, DEPT D				");
		sql.append(" WHERE E.EMPNM= ?			");
		sql.append("	AND E.DEPTNO=d.deptno	");
			
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, empnm);
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
			printEmp(empList);
			
		}catch (SQLException e) {
			System.out.println("SelectByName Error");
		}finally {
			close(con,pstmt,rs);
		}
		
	}
	
	//6. 사원 추가
	public static void insertEmp(EmpVO emp) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into emp(empmo,empnm,deptno,salary)");
		sql.append("		values(?,?,?,?)						"); // 가변인자
		
		int rowcnt =0;
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, emp.getEmpmo());
			pstmt.setString(2, emp.getEmpnm());
			pstmt.setString(3, emp.getDeptno());
			pstmt.setInt(4, emp.getSalary());
			rowcnt = pstmt.executeUpdate();		
			if(rowcnt >0) {
				System.out.println("사원추가 정상");
			}else {
				System.out.println("사원추가 비정상");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(con,pstmt);
		}
		
	}
	
	public static void main(String[] args) {
		selectAll();
		SelectByName("홍길동");
		System.out.println("===========");
		
		System.out.println("===========");
		SelectByName("홍길순");
		System.out.println("===========");

	}

}
