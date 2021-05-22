package implService;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ContentBean;
import bean.DiscussionBean;
import bean.NotificationBean;
import bean.NotificationsBean;
import service.BlogService;
import service.DatabaseAkses;
import service.NotifeService;

public class NotifeServiceImpl implements NotifeService {

	private DatabaseAkses database;
	private static NotifeServiceImpl instance;
	private HttpServletResponse response;
	private HttpServletRequest request;

	private NotifeServiceImpl(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		this.database = database;
		this.request = request;
		this.response = response;
	}

	public static NotifeServiceImpl getInstance(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		if (instance == null) {
			instance = new NotifeServiceImpl(database, request, response);
		}
		return instance;
	}

	@Override
	public List<NotificationsBean> viewNotife(String username) {
		List<NotificationsBean> notifeLists = new ArrayList<>();
		NotificationsBean notifications;
		try {
			String query = "Call viewDateNotife('" + username + "');";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			NotificationBean notification;
			while (rs.next()) {
				notifications = new NotificationsBean();
				List<NotificationBean> notifeList = new ArrayList<>();
				// select comment
				String queryComment = "Call viewNotife ('" + rs.getString(1)
						+ "', '" + username + "');";
				ResultSet rs2 = database.getInstance().executeSelectQuery(
						queryComment);
				while (rs2.next()) {
					notification = new NotificationBean();
					notification.setId(rs2.getInt(1));
					notification.setDescription(rs2.getString(7)+rs2.getString(2)+rs2.getString(8));
					notification.setLink(rs2.getString(3)+rs2.getString(5));
					notification.setStatus(rs2.getInt(4));
					notification.setTime(rs2.getString(6));
					notifeList.add(notification);
				}
				notifications.setDate(rs.getString(1));
				notifications.setNotife(notifeList);
				notifeLists.add(notifications);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return notifeLists;
	}

	@Override
	public String updateNotife(String notifID) {
		String status = "0";
		try {
			String[] query = { "Call updateNotife(" + notifID + ")" };
			boolean stat = database.getInstance().executeUpdateQuery(query);
			if (stat == true) {
				status = "1";
			}
		} catch (Exception e) {
			System.out.println(e);
			status = "0";
		}
		return status;
	}

	@Override
	public String countNotife(String username) {
		String status = "0";
		try {
			String query = "Call countNotife('" + username + "')";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if (rs.next()) {
				if (rs.getInt(1)==0) {
					status = "0";
				} else {
				status = rs.getString(1);	
				}
			}
		} catch (Exception e) {
			status = "0";
			System.out.println(e);
		}
		return status;
	}

	@Override
	public String deleteNotife(String username) {
		String status = "0";
		try {
			String[] query = { "Call deleteNotife('" + username + "')" };
			boolean stat = database.getInstance().executeUpdateQuery(query);
			if (stat == true) {
				status = "1";
			}
		} catch (Exception e) {
			System.out.println(e);
			status = "0";
		}
		return status;
	}
}
