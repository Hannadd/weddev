package main;

import controller.PhoneController;


public class Main {

	public static void main(String[] args) {
			
		// 프로그램 시작, 컨트롤러를 new해서 
		// 메뉴 메소드 불러오기
		
			PhoneController control = new PhoneController();
			control.menu();
	
		}

	}

