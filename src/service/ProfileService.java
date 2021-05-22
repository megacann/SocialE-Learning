package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import bean.UserBean;

public interface ProfileService {
	public UserBean viewProfile(String username, String otherUser);

	public UserBean viewMyProfile(String username);

	public String addEducation(String username, String institution,
			String course, String start, String end);

	public String deleteEducation(String eduID);

	public String addSkill(String username, String skill);

	public String deleteSkill(String skillID);

	public UserBean editPassword(String username, String Password);

	public List<UserBean> searchUser(String key, String userN);

	public String viewAllUser(String username, String key);

	public String updateFoto(HttpServletRequest request, String username,
			String fileName);

	public String updateProfile(String username, String fName, String lName,
			String address, String gender, String status, String bPlace,
			String bDate, String religion, String phone, String email,
			String website);

	public String updatePassword(String username, String passO, String passN);
}
