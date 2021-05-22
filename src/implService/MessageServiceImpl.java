package implService;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.MessageBean;
import bean.MessagesBean;
import bean.NotificationBean;
import bean.NotificationsBean;
import bean.UserBean;
import service.DatabaseAkses;
import service.MessageService;

public class MessageServiceImpl implements MessageService {

	private DatabaseAkses database;
	private static MessageServiceImpl instance;
	private HttpServletResponse response;
	private HttpServletRequest request;

	private MessageServiceImpl(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		this.database = database;
		this.request = request;
		this.response = response;
	}

	public static MessageServiceImpl getInstance(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		if (instance == null) {
			instance = new MessageServiceImpl(database, request, response);
		}
		return instance;
	}

	@Override
	public List<MessageBean> viewMessage(String receiver, String sender) {
		List<MessageBean> messageList = new ArrayList<>();
		MessageBean message;
		try {
			String queryComment = "CALL viewDetailMessage('" + sender + "', '"
					+ receiver + "');";
			ResultSet rs2 = database.getInstance().executeSelectQuery(
					queryComment);
			while (rs2.next()) {
				message = new MessageBean();
				message.setId(rs2.getInt(1));
				message.setSender(rs2.getString(2) + " " + rs2.getString(3));
				message.setBody(rs2.getString(4));
				message.setStatus(rs2.getInt(5));
				message.setTime(rs2.getString(6));
				message.setReceiver(rs2.getString(7));
				messageList.add(message);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return messageList;
	}

	@Override
	public List<UserBean> viewReceiver(String username) {
		List<UserBean> receiverList = new ArrayList<>();
		UserBean receiver;
		try {
			String query = "CALL viewReceiver('" + username + "')";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			while (rs.next()) {
				receiver = new UserBean();
				receiver.setUsername(rs.getString(1));
				receiver.setfName(rs.getString(2));
				receiver.setlName(rs.getString(3));
				String query2 = "CALL viewStatusMsg ('"
						+ receiver.getUsername() + "','" + username + "')";
				ResultSet rs2 = database.getInstance().executeSelectQuery(
						query2);
				if (rs2.next()) {
					receiver.setStatus(rs2.getInt(1));
				} else {
					receiver.setStatus(2);
				}
				receiverList.add(receiver);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return receiverList;
	}

	@Override
	public String deleteMessage(String receiver, String sender) {
		String status = "0";
		try {
			String[] query = { "CALL deleteMessage('" + sender + "', '"
					+ receiver + "')" };
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
	public String addMessage(String sender, String body, String name) {
		String receiver = "0";
		try {
			String query = "Select viewUsername ('" + name + "')";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if (rs.next()) {
				if (!rs.getString(1).equals("null")) {
					receiver = (rs.getString(1));
					String query2 = "Select addMessage ('" + receiver + "', '"
							+ sender + "', '" + body + "')";
					ResultSet rs2 = database.getInstance().executeSelectQuery(
							query2);
				}
			} else {
				receiver = "0";
			}
		} catch (Exception e) {
			receiver = "0";
			System.out.println(e);
		}
		return receiver;
	}

	@Override
	public String countMessage(String username) {
		String status = "0";
		try {
			String query = "Call countMessage('" + username + "')";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if (rs != null && rs.next()) {
				if (rs.getInt(1) == 0) {
					status = "0";
				} else {
					status = rs.getString(1);
				}
			} else {
				status = "0";
			}
		} catch (Exception e) {
			status = "0";
			System.out.println(e);
		}
		return status;
	}

	@Override
	public String updateMessage(String receiver, String sender) {
		String status = "0";
		try {
			if (!sender.equals("")) {
				String[] query = { "CALL updateMessage('" + sender + "', '"
						+ receiver + "')" };
				boolean stat = database.getInstance().executeUpdateQuery(query);
				if (stat == true) {
					status = "1";
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			status = "0";
		}
		return status;
	}

}
