package bean;

import java.util.List;

public class NotificationsBean {
	private String date;
	private List<NotificationBean> notife;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<NotificationBean> getNotife() {
		return notife;
	}
	public void setNotife(List<NotificationBean> notife) {
		this.notife = notife;
	}
	
}
