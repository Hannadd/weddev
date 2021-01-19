package service;

import java.util.ArrayList;

import dao.EmpDAO;
import vo.EmpVO;

public class EmpService {
	
	//1. 전사원 목록 
	public ArrayList<EmpVO> selectAll(){
		ArrayList<EmpVO> empList = new ArrayList<EmpVO>();
		EmpDAO empDao= new EmpDAO();
		empList = empDao.selectAll();		
		return empList;
		
	}
	
	//사원 수정

	public int updateEmp(EmpVO emp) {
		int rowcnt = 0;
		
		EmpDAO empDao = new EmpDAO();		
//		EmpDAO.SelectByName(emp.getEmpnm());
		return rowcnt;
	}
}
