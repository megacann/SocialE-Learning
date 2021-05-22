package service;

import java.util.List;
import java.util.Map;

import bean.UserBean;

public interface FriendService {
	public Map<Integer, List> friendList(String uID);
	public boolean approveFriend(int ID, String action);
	public String updateFriend(String username, String username2);
	public String addFriend(String username, String username2);
	public String deleteFriend(String username, String username2);
	public String countFriendPending(String username);
}
