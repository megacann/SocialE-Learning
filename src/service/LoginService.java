package service;
import java.util.List;

import bean.GroupBean;
import bean.UserBean;

public interface LoginService {
	public UserBean checkLogin(String username, String password);
	public List<GroupBean> groupList(String username);
	public UserBean signUp(String username, String password, String fname, String lname);
}
