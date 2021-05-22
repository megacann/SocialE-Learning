package implService;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UserBean;
import service.DatabaseAkses;
import service.FriendService;

public class FriendServiceImpl implements FriendService {
	private DatabaseAkses database;
	private static FriendServiceImpl instance;
	private HttpServletResponse response;
	private HttpServletRequest request;

	private FriendServiceImpl(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		this.database = database;
		this.request = request;
		this.response = response;
	}

	public static FriendServiceImpl getInstance(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		if (instance == null) {
			instance = new FriendServiceImpl(database, request, response);
		}
		return instance;
	}

	@Override
	public Map<Integer, List> friendList(String uID) {
		List<UserBean> userList;
		Map<Integer, List> userListAndID = new TreeMap<Integer, List>();
		try {
			String query = "call viewFriend ('" + uID + "')";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			UserBean friend;
			int id;
			while (rs.next()) {
				userList = new ArrayList();
				friend = new UserBean();
				id = rs.getInt(1);
				friend.setUsername(rs.getString(2));
				friend.setMembershipStatus(rs.getInt(3));
				friend.setfName(rs.getString(4));
				friend.setlName(rs.getString(5));
				if (rs.getString(6).equals("") || rs.getString(6).equals("null")) {
					friend.setFoto("sources/images/userpic.gif");
				} else {
					friend.setFoto("sources/userContent/" + rs.getString(2) + "/"
							+ rs.getString(2) + "." + rs.getString(6));
				}
				userList.add(friend);
				userListAndID.put(id, userList);
			}
			// Pending Friend
			String query2 = "call viewPendingFriend ('" + uID + "')";
			ResultSet rs2 = database.getInstance().executeSelectQuery(query2);
			while (rs2.next()) {
				userList = new ArrayList();
				friend = new UserBean();
				id = rs2.getInt(1);
				friend.setUsername(rs2.getString(2));
				friend.setMembershipStatus(rs2.getInt(3));
				friend.setfName(rs2.getString(4));
				friend.setlName(rs2.getString(5));
				if (rs2.getString(6).equals("")) {
					friend.setFoto("sources/images/userpic.gif");
				} else {
					friend.setFoto("sources/userContent/" + rs2.getString(2) + "/"
							+ rs2.getString(2) + "." + rs2.getString(6));
				}
				userList.add(friend);
				userListAndID.put(id, userList);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return userListAndID;
	}

	@Override
	public boolean approveFriend(int ID, String action) {
		boolean updateStatus = false;
		int status = Integer.parseInt(action);
		try {
			if (status == 1) {
				String query[] = { "call approveFriend(" + ID + ",'" + status
						+ "');" };
				updateStatus = database.getInstance().executeUpdateQuery(query);
			} else {
				String query[] = { "call rejectFriend(" + ID + ");" };
				updateStatus = database.getInstance().executeUpdateQuery(
						query);
			}
		} catch (Exception e) {
			System.out.println(e);
			updateStatus=false;
		}
		return updateStatus;
	}

	@Override
	public String updateFriend(String username, String username2) {
		String status = "0";
		try {
			String query[] = { "call updateFriend('" + username + "','"
					+ username2 + "');" };
			boolean updateStatus = database.getInstance().executeUpdateQuery(
					query);
			if (updateStatus) {
				status = "1";
			}
		} catch (Exception e) {
			System.out.println(e);
			status = "0";
		}
		return status;
	}

	@Override
	public String addFriend(String username, String username2) {
		String id = "0";
		try {
			String query = "Select addFriend ('" + username + "', '"
					+ username2 + "')";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if (rs.next()) {
				id = (rs.getString(1));
			}
		} catch (Exception e) {
			System.out.println(e);
			id = "0";
		}
		return id;
	}

	@Override
	public String deleteFriend(String username, String username2) {
		String status = "0";
		try {
			String query[] = { "call deleteFriend('" + username + "','"
					+ username2 + "');" };
			boolean updateStatus = database.getInstance().executeUpdateQuery(
					query);
			if (updateStatus) {
				status = "1";
			}
		} catch (Exception e) {
			System.out.println(e);
			status = "0";
		}
		return status;
	}

	@Override
	public String countFriendPending(String username) {
		String status = "0";
		try {
			String query = "Call countPendingFriend('" + username + "')";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if (rs.next()) {
				if (rs.getInt(1) == 0) {
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

}
