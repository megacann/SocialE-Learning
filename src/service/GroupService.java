package service;

import java.util.List;

import bean.ContentBean;
import bean.GroupBean;
import bean.UserBean;

public interface GroupService {
	public List<UserBean> memberList(String gID);
	public List<ContentBean> contentList(String gID, String username);
	public boolean memberRequestAction(String username, String action, String groupID, String invitor);
	public String createGroup(String groupName, String username);
	public List<GroupBean> searchGroup(String key, String username);
	public String inviteUser(List<UserBean> username, String gID, String invitator);
	public String invitationUserList(String gID, String fullName);
	public GroupBean groupSessionList(String gID, String username);
	public String makeAsAdmin(String gID, String username);
	public String addMember(String username, String groupID);
	public boolean deleteMember(String gID, String username);
	public boolean isMember(String gID, String username);
	public GroupBean viewGroup(String gID, String username);
}
