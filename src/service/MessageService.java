package service;

import java.util.List;

import bean.MessageBean;
import bean.UserBean;

public interface MessageService {
	public List<MessageBean> viewMessage(String receiver,String sender);
	public List<UserBean> viewReceiver(String username);
	public String countMessage(String username);
	public String updateMessage(String receiver,String sender);
	public String deleteMessage(String receiver,String sender);
	public String addMessage(String sender, String body, String name);
}
