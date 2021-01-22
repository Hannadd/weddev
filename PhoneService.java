package service;

import java.util.ArrayList;

import dao.PhoneDAO;
import vo.PhoneVO;

public class PhoneService {
	PhoneDAO dao = new PhoneDAO();
	int rowcnt;
	ArrayList<PhoneVO> phoneList = new ArrayList<PhoneVO>();
	
	//1. 전화번호 전체 목록
	public ArrayList<PhoneVO> selectAll(){
		
		phoneList = dao.selectAll();
		return phoneList;
	}
	
	//2. 전화번호 추가
	public int insertPhone(PhoneVO phone){
		
		//컨트롤러에서 phone 객체를 받아서 다오로 보내주고 
		//다오에서 받아온 rowcnt를 반환한다. 
		//(받아온 rowcnt는 view에서 받아 결과값을 보내줄 때 사용)
		rowcnt = dao.insertPhone(phone);
		return rowcnt;
		
		
	}
	
	//3. 전화번호 검색
	public ArrayList<PhoneVO> selectByName(String name){
		
		phoneList = dao.selectByName(name);
		return phoneList;
	}

	//4. 전화번호 수정
	
	public int reviseNumber(String name, int choice){
		
		rowcnt=dao.reviseNumber(name, choice);
		return rowcnt;
		
	}
	
	//5. 전화번호 삭제
	public int deleteNumber(String name){
		
		rowcnt=dao.deleteNumber(name);
		return rowcnt;
		
	}
	
}
