package service;

import java.util.List;
import bean.NotificationsBean;

public interface NotifeService {
	public List<NotificationsBean> viewNotife(String username);
	public String updateNotife(String notifID);
	public String countNotife(String username);
	public String deleteNotife(String username);
	
}
