package bean;

import java.util.List;

public class UserBean {
	private String username;
	private String password;
	private String fName;
	private String lName;
	private String address;
	private int gender;
	private int status;
	private String birthPlace;
	private String birthDate;
	private int religion;
	private int role;
	private String phone;
	private String email;
	private String website;
	private String foto;
	private int membershipStatus;
	private List<EducationBean> educationList;
	private List<SkillBean> skillList;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		if (fName==null) {
			fName="-";
		}
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		if (lName==null) {
			lName="-";
		}
		this.lName = lName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		if (address==null) {
			address="-";
		}
		this.address = address;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		if (gender==0) {
			gender=1;
		}
		this.gender = gender;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		if (status==0) {
			status=1;
		}
		this.status = status;
	}
	public String getBirthPlace() {
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		if (birthPlace==null) {
			birthPlace="-";
		}
		this.birthPlace = birthPlace;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public int getReligion() {
		return religion;
	}
	public void setReligion(int religion) {
		if (religion==0) {
			religion=1;
		}
		this.religion = religion;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		if (role==0) {
			role=2;
		}
		this.role = role;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		if (phone==null) {
			phone="-";
		}
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if (email==null) {
			email="-";
		}
		this.email = email;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		if (website==null) {
			website="-";
		}
		this.website = website;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public int getMembershipStatus() {
		return membershipStatus;
	}
	public void setMembershipStatus(int membershipStatus) {
		this.membershipStatus = membershipStatus;
	}
	public List<EducationBean> getEducationList() {
		return educationList;
	}
	public void setEducationList(List<EducationBean> educationList) {
		this.educationList = educationList;
	}
	public List<SkillBean> getSkillList() {
		return skillList;
	}
	public void setSkillList(List<SkillBean> skillList) {
		this.skillList = skillList;
	} 
	
}
