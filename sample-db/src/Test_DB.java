import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//데이터베이스 연동 , select한 결과를 화면에 출력
public class Test_DB {
	//전체 사원 목록 조회 메소드를 만듦/ 특정 코드를 반복사용하려고, 복잡한 코드를 하나로 묶어 구조화하려고
	public static void selectAll() {
		
			//1. 드라이버 로딩 	2.Connection 	3.SQL 생성 - select	 4. 트럭생성(PrepareStatement) 
			//5. 트럭을 서버로 (실행) 	6. 서버 실행결과가 오면 ResultSet	7.화면에 출력	8.닫기
			// Connection 정보 (ip, port, SID, USER ID, PassWord
			// 드라이버를 지정
			
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; // local~ip, 1521-port, xe-SID
			String user = "ora_user";
			String password = "hong";
			String driver = "oracle.jdbc.driver.OracleDriver"; //orcle.jdbc.driver - 패키지, .OracleDriver-클래스
			
			//변수 선언 - jdbc들이 들어가 있다
			Connection con 			= null; // DB 연결을 처리하는 클래스
			PreparedStatement pstmt = null;
			ResultSet rs 			= null;
			
			//try-catch 이제 시작!!
			try {
//			Class.forName(driver);//드라이버 이름으로 클래스 영역에다 집어두기 (1.드라이버를 로딩) 메모리에 올라감
				con = DriverManager.getConnection(url,user,password); // 드라이버 안에 있는 Static한 메소드 (2.Connection)
				
				StringBuilder sql = new StringBuilder(); // StringBuilde를 써야 메모리 유실을 막는다.(3.SQL 생성 - select)
				sql.append("select e.empmo					");
				sql.append(" 	, e.empnm "						);
				sql.append(" 	, e.salary"						);
				sql.append(" 	, e.deptno"						);		
				sql.append(" 	,( select d.deptnm"				);
				sql.append("      	from dept d"	 			);
				sql.append("     	where d.deptno = e.deptno"	);
				sql.append(" 	 ) as deptnm"				    );
				sql.append("  from emp e"						);
				
				pstmt = con.prepareStatement(sql.toString()); //파라미터로 문자열을 받아서 toString 쓰기 (4. 트럭생성(짐 ))
				rs = pstmt.executeQuery(); // 트럭을 서버로 보낸다. (실행) & 실행결과를 받는다.(5. 트럭을 서버로 (실행)) & rs가 가지고 있음
				
				while (rs.next()) { // Start포인트 부터 End 포인트까지 하나씩 꺼내서 출력한다.(6. 서버 실행결과가 오면 ResultSet)
					System.out.print("사원번호: " +rs.getString("empmo"));  //(7.화면에 출력)
					System.out.print("|사원이름: " +rs.getString("empnm"));  
					System.out.print("|급여: " 	+rs.getInt("salary"));  
					System.out.print("|부서번호: " +rs.getString("deptno"));  
					System.out.print("|부서이름: " +rs.getString("deptnm"));  
					System.out.println();
				}
				
				
			} catch(Exception e) {
				System.out.println("e오류");
				
			}finally {
				try {
					rs.close();
					pstmt.close();
					con.close(); //연결 닫기(역순으로 해야함!!)
				}catch (Exception e) {
					System.out.println("오류");
				}
			}
			
			
		}
	
	//특정 사원 목록 검색 메소드 - 반복사용
	public static void selectByName(String empnm) {
		//이름으로 검색하는 쿼리문 
		
		Connection con 			= null;
		PreparedStatement pstmt = null;
		StringBuilder sql = new StringBuilder();
		ResultSet rs 			= null;
		
		String url 		= "jdbc:oracle:thin:@localhost:1521:xe";
		String user		= "ora_user";
		String password = "hong";
		
	
		sql.append("SELECT E.EMPMO				");
		sql.append("	,E.EMPNM				");
		sql.append("	,d.deptnm				");
		sql.append(" FROM EMP E 				");
		sql.append("	, DEPT D				");
		sql.append(" WHERE E.EMPNM= ?			");// 가변이라 ? 하나면 된다!!!!!!!
		sql.append("	AND E.DEPTNO=d.deptno	");
		
		try {
			con =DriverManager.getConnection(url,user,password);
			pstmt =con.prepareStatement(sql.toString());
			pstmt.setString(1,empnm);
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				System.out.print("사원번호: " +rs.getString("empmo"));  //(7.화면에 출력)
				System.out.print("|사원이름: " +rs.getString("empnm"));  
				System.out.print("|부서이름: " +rs.getString("deptnm"));  
				System.out.println();
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				con.close(); //연결 닫기(역순으로 해야함!!)
			}catch (SQLException e) {
				System.out.println("오류");
			}
		}
	}
	
	
	// 사원 추가 (=> DB emp 테이블에 insert) 메소드
	//	EMPMO EMPNM DEPTNO SALARY (컬럼명 주석걸어서 까먹지 않도록!)
	// 1. connection, SQL, PreparedStatement, 
	//	4 . 값세팅!!! 5. 실행 - insert/true or False (resultSet 없음!)
	public static void insertEmp (String empmo, String empnm
			, String deptno, int salary) {
		
		Connection con 			= null;
		PreparedStatement pstmt = null;
		StringBuilder sql = new StringBuilder();
		
		String url 		= "jdbc:oracle:thin:@localhost:1521:xe";
		String user		= "ora_user";
		String password = "hong";
				
		try {
			con = DriverManager.getConnection(url,user,password);
			
			sql.append("insert into emp(empmo, empnm,deptno,salary)");
			sql.append("		values(?,?,?,?)						"); // 가변인자
			
			pstmt = con.prepareStatement(sql.toString());
			
			// insert는 값을 세팅해주어야
			pstmt.setString(1, empmo);
			pstmt.setString(2, empnm);
			pstmt.setString(3, deptno);
			pstmt.setInt(4, salary);
			
			// 트럭 실행 - insert, update, delete 는 excuteUpdate(); 사용 - 반영된 row 개수 반환
			int rowcnt = pstmt.executeUpdate(); 
			if (rowcnt>0) {
				System.out.println("사원 추가 정상");
			} else {
				System.out.println("사원 추가 에러");
			}
			
		}catch(SQLException e) {
			e.printStackTrace(); //어디서 어떤 에러가 발생했는지 추적해서 출력
		}finally {
			try {
				pstmt.close();   //역순으로 닫기!
				con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	

	public static void main(String[] args) {
		selectAll();
		System.out.println("===========");
		selectByName("홍길동");
		System.out.println("===========");
		selectAll();
	}
}
