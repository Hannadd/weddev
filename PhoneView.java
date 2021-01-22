package view;

import java.util.ArrayList;
import java.util.Scanner;

import service.PhoneService;
import vo.PhoneVO;

public class PhoneView {
	
	private PhoneService phoneService = new PhoneService();
	private int rowcnt;
	
	public void menu() { // 기본 메뉴
		System.out.println("==============================");
		System.out.println("1. 저장된 전화번호 목록 보기");
		System.out.println("2. 새로운 전화번호 등록하기");
		System.out.println("3. 전화번호 검색하기 ");
		System.out.println("4. 등록된 정보 수정하기");
		System.out.println("5. 등록된 정보 삭제하기");
		System.out.println("6. 프로그램 종료");
		System.out.println("==============================");
	}
	
	// 1. 전체 출력
	public void printAll (ArrayList<PhoneVO> phoneList) {
		for(int i=0; i<phoneList.size(); i++) {
			System.out.println(phoneList.get(i));
		}
	}
	
	public void name() {
		System.out.println("이름 :");
	}
	
	public void number() {
		System.out.println("전화번호 :");
	}
	
	public void group() {
		System.out.println("그룹번호 :");
	}
	
	public void address() {
		System.out.println("주소 :");
	}
	
	// 2. 전화번호 추가
	public void insertPhone() {
		System.out.println("==============================");
		System.out.println("저장하실 전화번호의 정보를 입력해주세요.");
		
	}
	
	// 3. 전화번호 검색  
	public void selectByName () {
		
		System.out.println("==============================");
		System.out.println("검색하실 이름을 입력해주세요.");
		System.out.println("이름 :");
		
		
	}
	
	
	//4. 전화번호 수정 
	
	public void reviseNumberN () { // 이름 검색부터 받는다.
		
		System.out.println("==============================");
		System.out.println("수정하실 번호의 이름을 입력해주세요.");
		System.out.println("이름 :");

	}
	
	public void reviseNumber () { // 검색 번호를 받아서 수정을 한다.
	
		System.out.println("==============================");
		System.out.println("수정하실 정보를 선택해주세요.");
		System.out.println("1.이름, 2.번호, 3.그룹, 4.주소");
	}
	public void revise() {
		System.out.println("==============================");
		System.out.println("수정하실 정보를 입력해주세요.");
		
	}
	
	//5. 전화번호 삭제 
	
	public void deleteNumber () { // 이름 검색부터 받는다.
		
		System.out.println("==============================");
		System.out.println("삭제하실 번호의 이름을 입력해주세요.");
		System.out.println("이름 :");
		
	}
	
	//마지막 결과 출력들 ! 
	//select 결과 말고 , insert, update, delete 결과들 
	public void insertresult(PhoneVO phone) {
		rowcnt = phoneService.insertPhone(phone);
		if(rowcnt > 0) {
			System.out.println("전화번호 입력 성공");			
		}else {			
			System.out.println("전화번호 입력 실패");			
		}
	}
	
	public void reviseresult(String name, int choice) {
		rowcnt = phoneService.reviseNumber(name, choice);
		if(rowcnt > 0) {
			System.out.println("전화번호 수정 성공");			
		}else {			
			System.out.println("전화번호 수정 실패");			
		}
		
	}
	
	public void deleteresult(String name) {
		rowcnt=phoneService.deleteNumber(name);
		if(rowcnt >0) {
			System.out.println("전화번호 삭제 완료");
		}else {
			System.out.println("전화번호 삭제 실패");
		}
	}

}
