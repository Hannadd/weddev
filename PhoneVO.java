package vo;

public class PhoneVO { // 변수 선언하고, get, set
	private String name;
	private String phoneNumber;
	private String groupNumber;
	private String groupName;
	private String address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getAddress() {
		return address;
	}
	public void setAdress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return 		"[이름=" + name + ""
				+ ", 전화번호=" + phoneNumber + ""
				+ ", 그룹번호=" + groupNumber
				+ ", 그룹이름=" + groupName 
				+ ", 주소=" + address + "]";
	}
	
}
