package controller;

import java.util.ArrayList;
import java.util.Scanner;

import service.PhoneService;
import view.PhoneView;
import vo.PhoneVO;

public class PhoneController {
	
	//메인에서 여기(콘트롤러)로 온 다음, 메뉴로 가서, 선택받은 메소드를 실행하게 된다.
	
	private PhoneService phoneService = new PhoneService();
	private PhoneView phoneview = new PhoneView();
	private ArrayList <PhoneVO> phoneList = new ArrayList();
	private PhoneVO phone = new PhoneVO();
	Scanner scanner = new Scanner(System.in);
	
	//각 메소드 마다 필요한 Service와 phoneView와 phoeList와 phone를 미리 new 해둔다.
	//컨트롤러에서 스캐너를 이용해 값을 받을 예정
	
	String name;
	int choice;
	
	
	//기본 메뉴창과 메소드 실행 (view와 서비스 연결)
	public void menu() {
	
	phoneview.menu();
	int choicenumber = scanner.nextInt();
	//메뉴창을 출력한다음 받은 값으로 switch 문을 통해 메소드 실행
	
		switch (choicenumber) {
			case 1 :
				selectAll();
				break;
			case 2 :
				insertPhone();
				break;
			case 3 :
				selectByName();
				break;
			case 4 :
				reviseNumber();
				break;
			case 5 :
				deleteNumber();
				break;
		}
	
	}
	
	//1. 전체목록 출력 메소드
	public void selectAll() {
		phoneList = phoneService.selectAll();
		phoneview.printAll(phoneList);
	}
	
	//2. 전화번호 추가 메소드
	public void insertPhone() {
		phoneview.insertPhone(); // 저장 메세지 
		
		phoneview.name();
		phone.setName(scanner.nextLine());
		phoneview.number();
		phone.setPhoneNumber(scanner.nextLine());
		phoneview.group();
		phone.setGroupNumber(scanner.nextLine());
		phoneview.address();
		phone.setAdress(scanner.nextLine());
		
		// 하나하나 입력 받고, 서비스로 보낸다.
		phoneService.insertPhone(phone);
		//view에서 결과창 출력
		phoneview.insertresult(phone);
		
	}
	
	//3. 전화번호 검색
	public void selectByName() {
		
		phoneview.selectByName();
		name = scanner.nextLine();
		ArrayList <PhoneVO> phoneList = phoneService.selectByName(name);
		
		phoneview.printAll(phoneList);
		
	}
	
	//4. 전화번호 수정
	public void reviseNumber() {
		
		phoneview.reviseNumberN();
		name = scanner.nextLine();
		phoneview.reviseNumber();
		choice = scanner.nextInt();

		switch (choice) {
		case 1:
			phoneview.name();
			phone.setName(scanner.nextLine());
		
		case 2:
			phoneview.number();
			phone.setPhoneNumber(scanner.nextLine());
			
		case 3:
			phoneview.group();
			phone.setGroupNumber(scanner.nextLine());
			
		case 4:
			phoneview.address();
			phone.setAdress(scanner.nextLine());
		}
		
		phoneService.reviseNumber(name,choice);
		
	}
	
	//5. 전화번호 삭제
	public void deleteNumber() {
		
		phoneview.deleteNumber();
		name = scanner.nextLine();
		phoneService.deleteNumber(name);
		
	}
	

}
