package implService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ContentBean;
import bean.DiscussionBean;
import bean.EventBean;
import bean.FileBean;
import bean.GroupBean;
import bean.QuestionBean;
import bean.UserBean;
import service.DatabaseAkses;
import service.GroupService;
import sun.org.mozilla.javascript.internal.json.JsonParser;

public class GroupServiceImpl implements GroupService {
	private DatabaseAkses database;
	private static GroupServiceImpl instance;
	private HttpServletResponse response;
	private HttpServletRequest request;

	private GroupServiceImpl(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		this.database = database;
		this.request = request;
		this.response = response;
	}

	public static GroupServiceImpl getInstance(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		if (instance == null) {
			instance = new GroupServiceImpl(database, request, response);
		}
		return instance;
	}

	@Override
	public List<UserBean> memberList(String gID) {
		List<UserBean> memberList = new ArrayList();
		try {
			String query = "CALL viewMemberList(" + gID + ");";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			UserBean member;
			while (rs.next()) {
				member = new UserBean();
				member.setfName(rs.getString(1));
				member.setlName(rs.getString(2));
				member.setRole(rs.getInt(3));
				if (rs.getString(4).equals("")) {
					member.setFoto("sources/images/userpic.gif");
				} else {
					member.setFoto("sources/userContent/" + rs.getString(6)
							+ "/" + rs.getString(6) + "." + rs.getString(4));
				}

				member.setMembershipStatus(rs.getInt(5));
				member.setUsername(rs.getString(6));
				// System.out.println(member.getfName());
				memberList.add(member);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return memberList;
	}

	@Override
	public List<ContentBean> contentList(String gID, String username) {
		List<ContentBean> contentList = new ArrayList();
		try {
			String query = "CALL viewContent (" + gID + ");";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			ContentBean content;
			UserBean user;
			while (rs.next()) {
				content = new ContentBean();
				user = new UserBean();
				content.setTitle(rs.getString(3));
				content.setBody(rs.getString(4));
				content.setPostedDate(rs.getString(5));
				content.setType(rs.getInt(6));
				content.setId(rs.getInt(7));
				user.setUsername(rs.getString(8));
				content.setCategory(rs.getString(9));
				user.setfName(rs.getString(1));
				user.setlName(rs.getString(2));
				content.setUser(user);
				if (content.getType() == 4) {

					// content sharing
					String queryFile = "CALL viewFile(" + content.getId()
							+ ");";
					ResultSet rs2 = database.getInstance().executeSelectQuery(
							queryFile);
					while (rs2.next()) {
						FileBean file = new FileBean();
						file.setId(rs2.getInt(1));
						file.setFileName(rs2.getString(3));
						file.setLocation(rs2.getString(4).toLowerCase());
						file.setPageSize(rs2.getInt(6));
						content.setSingleFile(file);
					}
				} else if (content.getType() == 5) {
					// event
					List<EventBean> eventList = new ArrayList();
					String queryEvent = "CALL viewEvent(" + content.getId()
							+ ");";
					System.out.println("query : " + queryEvent);
					ResultSet rs3 = database.getInstance().executeSelectQuery(
							queryEvent);
					EventBean event;
					while (rs3.next()) {
						event = new EventBean();
						event.setId(rs3.getInt(1));
						event.setStartTime(rs3.getString(2));
						event.setEndTime(rs3.getString(3));
						event.setPlace(rs3.getString(4));
						event.setType(rs3.getInt(5));

						String queryKonfirm = "CALL viewConfirmEvent("
								+ event.getId() + ",'" + username + "');";
						System.out.println("query : " + queryKonfirm);
						ResultSet rs5 = database.getInstance()
								.executeSelectQuery(queryKonfirm);
						if (rs5.next()) {
							if (rs5.getInt(1) == 0) {
								event.setStatus(0);
							} else {
								event.setStatus(rs5.getInt(1));
							}
						}

						if (event.getType() == 1) {
							List<QuestionBean> questionList = new ArrayList();
							String queryQuestion = "CALL viewQuestion("
									+ event.getId() + ");";
							System.out.println("query : " + queryQuestion);
							ResultSet rs4 = database.getInstance()
									.executeSelectQuery(queryQuestion);
							QuestionBean question;
							while (rs4.next()) {
								// QUESTION,CORRECT,POINT,A,B,C,D,E
								question = new QuestionBean();
								question.setQuestion(rs4.getString(1));
								question.setCorrectAnswer(rs4.getString(2));
								question.setMaxPoint(rs4.getInt(3));
								question.setA(rs4.getString(4));
								question.setB(rs4.getString(5));
								question.setC(rs4.getString(6));
								question.setD(rs4.getString(7));
								question.setE(rs4.getString(8));
								questionList.add(question);
							}
							event.setQuestion(questionList);
						}
						eventList.add(event);
					}
					content.setEvent(eventList);
				}
				// select comment
				String queryComment = "Call viewComment(" + content.getId()
						+ ");";
				ResultSet rs2 = database.getInstance().executeSelectQuery(
						queryComment);
				List<DiscussionBean> commentList = new ArrayList<>();
				DiscussionBean comment;
				while (rs2.next()) {
					comment = new DiscussionBean();
					comment.setId(rs2.getInt(1));
					comment.setTime(rs2.getString(2));
					comment.setComment(rs2.getString(3));
					comment.setCommentator(rs2.getString(4) + " "
							+ rs2.getString(5));
					commentList.add(comment);
				}
				content.setComment(commentList);

				contentList.add(content);

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return contentList;
	}

	@Override
	public boolean memberRequestAction(String username, String action,
			String groupID, String invitor) {
		boolean status = false;
		try {
			String query[] = new String[1];
			if (action.equals("3")) {
				query[0] = "Call deleteMember('" + username + "'," + groupID
						+ ")";
			} else {
				query[0] = "Call approveMember('" + username + "'," + groupID
						+ ")";
				// Add Notife
				ResultSet rs2 = database
						.getInstance()
						.executeSelectQuery(
								"select insertNotifGroup(' menerima Anda menjadi anggota grup ','Controller?action=gDetail&gID=','"
										+ invitor + "'," + groupID + ")");
				if (rs2.next()) {
					if (!rs2.getString(1).equals("null")) {
						int idNotif = rs2.getInt(1);
						String query4 = "select addNotifUser('" + username
								+ "'," + idNotif + ")";
						ResultSet rs4 = database.getInstance()
								.executeSelectQuery(query4);
						if (!rs4.next()) {
							status = false;
						} else {
							status = true;
						}
					}
				}
				// End Notife

			}
			System.out.println(query[0]);
			database.getInstance().executeUpdateQuery(query);
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	@Override
	public String createGroup(String groupName, String username) {
		String currentGroupID = "false";
		try {
			String query = "select createGroup('" + groupName + "','"
					+ username + "')";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			System.out.println(query);
			if (rs.next()) {
				currentGroupID = rs.getString(1);
			}
		} catch (Exception e) {
			System.out.println(e);
			currentGroupID = "false";
		}
		return currentGroupID;
	}

	@Override
	public List<GroupBean> searchGroup(String key, String username) {
		List<GroupBean> groupList = new ArrayList();
		try {
			String query = "Call searchGroup('%" + key + "%');";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			GroupBean groupLists;
			while (rs.next()) {
				groupLists = new GroupBean();
				groupLists.setId(rs.getInt(1));
				groupLists.setGroupName(rs.getString(2));
				String queryComment = "CALL viewMembership(" + rs.getInt(1)
						+ ",'" + username + "')";
				ResultSet rs2 = database.getInstance().executeSelectQuery(
						queryComment);
				if (rs2.next()) {
					if (rs2.getString(1) != null) {
						groupLists.setStatus(rs2.getInt(1));
					} else {
						groupLists.setStatus(0);
					}

				}
				groupList.add(groupLists);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return groupList;
	}

	@Override
	public String inviteUser(List<UserBean> username, String gID,
			String invitator) {
		String uName = "", foto = "", fullName = "", finalResult = "";
		int idNotif = 0;
		int idM = 0;
		try {
			ResultSet rs2 = database
					.getInstance()
					.executeSelectQuery(
							"select insertNotifGroup(' mengundang ke Group ','Controller?action=gDetail&gID=','"
									+ invitator + "'," + gID + ")");
			if (rs2.next()) {
				if (!rs2.getString(1).equals("null")) {
					idNotif = rs2.getInt(1);
				}
			}
			for (Object user : username) {
				// System.out.println(((UserBean) user).getUsername());
				String query = "call inviteUsertoGroup(" + gID + ",'"
						+ ((UserBean) user).getUsername() + "')";
				String query3 = "select addNotifUser('"
						+ ((UserBean) user).getUsername() + "'," + idNotif
						+ ")";
				database.getInstance().executeSelectQuery(query3);
				System.out.println(query);
				ResultSet rs = database.getInstance().executeSelectQuery(query);
				if (rs.next()) {
					uName = rs.getString(1);
					if (rs.getString(2).equals("")) {
						foto = "sources/images/userpic.gif";
					} else {
						foto = "sources/userContent/"
								+ ((UserBean) user).getUsername() + "/"
								+ ((UserBean) user).getUsername() + "."
								+ rs.getString(2);
					}
					idM = rs.getInt(4);
					fullName = rs.getString(3);
					finalResult += "<li id='member"
							+ idM
							+ "'><a href=\"Personal?action=vUser&username="
							+ uName
							+ "\"> <img src="
							+ foto
							+ "> <br>"
							+ fullName
							+ "</a><input type='button' value='Jadikan admin' class='btn btn-info btn-block btn-xs' id='button1' onclick=\"makeAsAdmin('"
							+ uName
							+ "',member"
							+ idM
							+ ")\"> <input type='button' value='Kick' class='btn btn-warning btn-block btn-xs' id='button2' onclick=\"deleteFromGroup('"
							+ uName + "',member" + idM + ")\" ></li>";

				}
			}
			// System.out.println(username.get(0).getUsername());
		} catch (Exception e) {
			finalResult = "0";
			System.out.println(e);
		}
		return finalResult;
	}

	@Override
	public String invitationUserList(String gID, String fullName) {
		int size = 0;
		int position = 0;
		UserBean user;
		String intermediateResult = "";
		String finalResult = "";
		try {
			String query = "call invitationUserList('" + gID + "','%"
					+ fullName + "%')";
			// System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			rs.last();
			size = rs.getRow();
			rs.beforeFirst();
			// System.out.println(size);
			while (rs.next()) {
				position++;
				user = new UserBean();
				user.setUsername(rs.getString(1));
				user.setfName(rs.getString(2));
				if (position == size)
					intermediateResult += "{\"id\":\"" + user.getUsername()
							+ "\",\"label\":\"" + user.getfName()
							+ "\",\"value\":\"" + user.getUsername() + "\"}";
				else
					intermediateResult += "{\"id\":\"" + user.getUsername()
							+ "\",\"label\":\"" + user.getfName()
							+ "\",\"value\":\"" + user.getUsername() + "\"},";
			}
			finalResult = "[" + intermediateResult + "]";
			// System.out.println(finalResult);
		} catch (Exception e) {
			System.out.println(e);
		}
		return finalResult;
	}

	@Override
	public GroupBean groupSessionList(String gID, String username) {
		GroupBean sessionGroup = new GroupBean();
		UserBean user = new UserBean();
		List<UserBean> member = new ArrayList<>();
		try {
			String query = "call selectGroupSession(" + gID + ",'" + username
					+ "')";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if (rs.next()) {
				sessionGroup.setGroupName(rs.getString(1));
				user.setRole(rs.getInt(2));
				member.add(user);
				sessionGroup.setMember(member);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return sessionGroup;
	}

	@Override
	public String makeAsAdmin(String gID, String username) {
		String htmlReturn = "";
		UserBean user = new UserBean();
		try {
			String query = "call makeAsAdmin('" + username + "'," + gID + ")";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if (rs.next()) {
				user.setfName(rs.getString(2));

				if (rs.getString(3).equals("")) {
					user.setFoto("sources/images/userpic.gif");
				} else {
					user.setFoto("sources/userContent/" + username + "/"
							+ username + "." + rs.getString(3));
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		htmlReturn = "<li><a href=\"Personal?action=vUser&amp;username="
				+ username + "\"> <img src=\"" + user.getFoto() + "\"> <br>"
				+ user.getfName() + "</a></li>";
		System.out.println(htmlReturn);
		return htmlReturn;
	}

	@Override
	public boolean deleteMember(String username, String gID) {
		boolean status = false;
		try {
			String query[] = { "Call deleteMember('" + username + "'," + gID
					+ ")" };
			status = database.getInstance().executeUpdateQuery(query);
			System.out.println(query);
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	@Override
	public String addMember(String username, String groupID) {
		String id = "0";
		try {
			String query = "Select addMember ('" + username + "', " + groupID
					+ ")";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if (rs.next()) {
				id = (rs.getString(1));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return id;
	}

	@Override
	public boolean isMember(String gID, String username) {
		boolean status = false;
		try {
			String query = "CALL isMember('" + username + "'," + gID + ")";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			System.out.println(query);
			if (rs.next()) {
				if (!rs.getString(1).equals("null")) {
					status = true;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	@Override
	public GroupBean viewGroup(String gID, String username) {
		GroupBean grup = new GroupBean();
		try {
			String query = "CALL viewGroup(" + gID + ",'" + username + "')";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if (rs.next()) {
				if (!rs.getString(2).equals("null")) {
					grup.setId(rs.getInt(1));
					grup.setGroupName(rs.getString(2));
					grup.setStatus(rs.getInt(3));
				} else {
					grup.setId(rs.getInt(1));
					grup.setGroupName(rs.getString(2));
					grup.setStatus(0);
				}
			}
			List<UserBean> memberList = new ArrayList();

			String query2 = "CALL viewMemberList(" + gID + ");";
			System.out.println(query2);
			ResultSet rs2 = database.getInstance().executeSelectQuery(query2);
			UserBean member;
			while (rs2.next()) {
				member = new UserBean();
				member.setfName(rs2.getString(1));
				member.setlName(rs2.getString(2));
				member.setRole(rs2.getInt(3));
				if (rs2.getString(4).equals("")) {
					member.setFoto("sources/images/userpic.gif");
				} else {
					member.setFoto("sources/userContent/" + rs2.getString(6)
							+ "/" + rs2.getString(6) + "." + rs2.getString(4));
				}
				member.setMembershipStatus(rs2.getInt(5));
				member.setUsername(rs2.getString(6));
				// System.out.println(member.getfName());
				memberList.add(member);
			}
			grup.setMember(memberList);
		} catch (Exception e) {
			System.out.println(e);
		}
		return grup;
	}

}
