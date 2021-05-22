package implService;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ContentBean;
import bean.DiscussionBean;
import bean.EventBean;
import bean.FileBean;
import bean.QuestionBean;
import bean.UserBean;
import service.DatabaseAkses;
import service.EventService;

public class EventServiceImpl implements EventService {
	private DatabaseAkses database;
	private static EventServiceImpl instance;
	private HttpServletResponse response;
	private HttpServletRequest request;

	private EventServiceImpl(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		this.database = database;
		this.request = request;
		this.response = response;
	}

	public static EventServiceImpl getInstance(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		if (instance == null) {
			instance = new EventServiceImpl(database, request, response);
		}
		return instance;
	}

	@Override
	public String createEvent(String startTime, String endTIme, String place,
			String username, String gID, String title, String body, String category) {
		String htmlReturn = "";
		try {
			String query = "select createEvent('" + startTime + "','" + endTIme
					+ "','" + place + "','" + username + "','" + gID + "','"
					+ title + "','" + body + "'," + category + ")";
			System.out.println(query);
		
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if (rs.next() && (rs.getInt(1)!=0)) {
				htmlReturn=rs.getString(1);
				// add feedback
				String[] query2 = {"call insertConfirmEvent2("+gID+",'"+username+"',"+htmlReturn+",1)"};
				boolean st = database.getInstance().executeUpdateQuery(query2);
				System.out.println(query2[0]);
				
				// Add Notife
				ResultSet rs2 = database
						.getInstance()
						.executeSelectQuery(
								"select insertNotifGroup(' membuat acara di grup ','Controller?action=gDetail&gID=','"
										+ username
										+ "',"
										+ gID + ")");
				if (rs2.next()) {
					if (!rs2.getString(1).equals("null")) {
						int idNotif = rs2.getInt(1);
						String query3 = "call viewNotifGroup('"
								+ username
								+ "',"
								+ gID
								+ ")";
						ResultSet rs3 = database
								.getInstance()
								.executeSelectQuery(query3);
						while (rs3.next()) {
							if (!rs3.getString(1).equals(
									"null")) {
								String user = rs3
										.getString(1);
								String query4 = "select addNotifUser('"
										+ user
										+ "',"
										+ idNotif + ")";
								ResultSet rs4 = database
										.getInstance()
										.executeSelectQuery(
												query4);
							}
						}

					}
				}
				// End Notife
				
			} else {
				htmlReturn="false";
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return htmlReturn;
	}

	@Override
	public String createQuizEvent(String startTime, String endTIme,
			String username, String gID, String title, String body, String category) {
		String htmlReturn = "false";
		try {
			String query = "select createQuiz('" + username + "','" + gID
					+ "','" + title + "','" + body + "','" + startTime + "','"
					+ endTIme + "',"+category+")";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if (rs.next()) {
				htmlReturn = rs.getString(1);
				// Add Notife
				ResultSet rs2 = database
						.getInstance()
						.executeSelectQuery(
								"select insertNotifGroup(' mengadakan kuis di grup ','Controller?action=gDetail&gID=','"
										+ username
										+ "',"
										+ gID + ")");
				if (rs2.next()) {
					if (!rs2.getString(1).equals("null")) {
						int idNotif = rs2.getInt(1);
						String query3 = "call viewNotifGroup('"
								+ username
								+ "',"
								+ gID
								+ ")";
						ResultSet rs3 = database
								.getInstance()
								.executeSelectQuery(query3);
						while (rs3.next()) {
							if (!rs3.getString(1).equals(
									"null")) {
								String user = rs3
										.getString(1);
								String query4 = "select addNotifUser('"
										+ user
										+ "',"
										+ idNotif + ")";
								ResultSet rs4 = database
										.getInstance()
										.executeSelectQuery(
												query4);
							}
						}

					}
				}
				// End Notife
				
			}
		} catch (Exception e) {
			System.out.println(e);
			htmlReturn="false";
			
		}
		return htmlReturn;
	}

	@Override
	public String createQuiz(String startTime, String endTIme, String username,
			String gID, String title, String body) {
		String htmlReturn = "";
		try {
			String query = "select createQuiz('" + username + "','" + gID
					+ "','" + title + "','" + body + "','" + startTime + "','"
					+ endTIme + "')";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
		} catch (Exception e) {
			System.out.println(e);
		}
		return htmlReturn;
	}

	public String createQuestion(String eventID, String question,
			String correct, int point, String A, String B, String C, String D,
			String E) {
		String htmlReturn = "";
		// insert into t_question (event_id, question, correct, point, A, B, C,
		// D, E)
		try {
			String query = "select createQuestion(" + eventID + ",'" + question
					+ "','" + correct + "','" + point + "','" + A + "','" + B
					+ "','" + C + "','" + D + "','" + E + "')";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
		} catch (Exception e) {

		}
		return htmlReturn;
	}

	@Override
	public List<ContentBean> eventList(String gID, String username) {
		List<ContentBean> contentList = new ArrayList();
		try {
			String query = "CALL viewEventContent (" + gID + ");";
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
				content.setId(rs.getInt(6));
				user.setUsername(rs.getString(7));
				user.setfName(rs.getString(1));
				user.setlName(rs.getString(2));
				content.setUser(user);
				// event
				EventBean event;
				event = new EventBean();
				event.setId(rs.getInt(8));
				event.setStartTime(rs.getString(9));
				event.setEndTime(rs.getString(10));
				event.setPlace(rs.getString(11));
				String queryQuestion = "CALL viewConfirmEvent(" + event.getId()+ ",'"+username+"');";
				System.out.println("query : " + queryQuestion);
				ResultSet rs4 = database.getInstance().executeSelectQuery(queryQuestion);
				if (rs4.next()) {
					if (rs4.getInt(1)==0) {
						event.setStatus(0);
					} else {
						event.setStatus(rs4.getInt(1));	
					}
				}
				content.setSingleEvent(event);
				
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
	public String confirmEvent(String username, String gID, String cId,
			String act) {
		String stat = "false";
		try {
			String query[] = {"call insertConfirmEvent("+gID+",'"+username+"',"+cId+","+act+")"};
			boolean st = database
			.getInstance()
			.executeUpdateQuery(query);
			System.out.println(query[0]);
			if (st) {
				stat="true";
			}
		} catch (Exception e) {
			stat="false";
			// TODO: handle exception
		}
		return stat;
	}
}
