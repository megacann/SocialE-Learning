package controller;

import implService.BlogServiceImpl;
import implService.EventServiceImpl;
import implService.GroupServiceImpl;
import implService.FriendServiceImpl;
import implService.LoginServiceImpl;
import implService.ContentServiceImpl;
import implService.NewsServiceImpl;
import implService.WikiServiceImpl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;

import bean.EventBean;
import bean.GroupBean;
import bean.UserBean;
import bean.ContentBean;
import service.DatabaseAkses;
import sun.net.www.content.image.gif;

//import service.DatabaseAkses;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class ColCumSpaceController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ColCumSpaceController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	HttpSession session;
	private static final int ERROR = -1;
	private static final int LOGIN = 0;
	private static final int LOGOUT = 1;
	private static final int SIGNUP = 2;
	private static final int GDETAIL = 3;
	private static final int MEMREQACT = 4;
	private static final int GMEMBER = 5;
	private static final int CGROUP = 7;
	private static final int FRIENDSHIP = 8;
	private static final int FRIENDSHIPRESPONSE = 9;
	private static final int INVITETOGROUP = 10;
	private static final int SENDINVITATION = 11;
	private static final int MAKEASADMIN = 12;
	private static final int KICKMEMBER = 13;
	private static final int UPLOADCONTENT = 14;
	private static final int CREATECOURSE = 15;
	private static final int GBLOG = 16;
	private static final int ECOURSE = 17;
	private static final int UCOURSE = 18;
	private static final int VDCOURSE = 19;
	private static final int CREATEEVENT = 20;
	private static final int CREATQUIZEVENT = 21;
	private static final int CREATQUESTION = 22;
	private static final int VIEWFILEINGROUP = 23;
	private static final int ADDPOSTTOGROUP = 24;
	private static final int VIEWQUIZINGROUP = 25;
	private static final int ATTEMPQUIZ = 26;
	private static final int UPDATEANSWER = 27;
	private static final int SHOWSCOREDETAIL = 28;
	private static final int GROUPEVENT = 29;
	private static final int CONFIRMEVENT = 30;

	String actions[] = { "login", "logout", "signup", "gDetail",
			"memberReqAct", "groupMember", "memberList", "createGroup",
			"friendship", "friendReqAct", "inviteToGroup", "sendInvitation",
			"makeAsAdmin", "kickMember", "uploadContent", "createCourse",
			"groupBlog", "editCourse", "updateCourse", "vDetailCourse",
			"createEvent", "createQuizEvent", "createQuestion",
			"viewFileInGroup", "addPostToGroup", "viewQuizInGroup",
			"attemptQuiz", "updateAnswer", "showScoreDetail", "groupEvent",
			"confirmEvent" };

	private DatabaseAkses database = null;

	private int selectAction(String action) {
		for (int a = 0; a < actions.length; a++) {
			if (actions[a].equals(action)) {
				return a;
			}
		}
		return -1;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 * request.setAttribute("a", "b"); doPost(request, response);
		 * System.out.print("Masuk Controller"); StringBuffer requestURL =
		 * request.getRequestURL(); if (request.getQueryString() != null) {
		 * requestURL.append("?").append(request.getQueryString()); } String
		 * completeURL = requestURL.toString();
		 * System.out.println("url : "+completeURL);
		 */
		String nextPage = "";
		String htmlReturn = "";
		boolean isNextPage = true;
		switch (selectAction(request.getParameter("action"))) {
		case LOGIN:
			System.out.println("case - login");
			nextPage = login(request, response);
			break;
		case LOGOUT:
			System.out.println("case - logout");
			nextPage = logout(request, response);
			break;
		case SIGNUP:
			System.out.println("case - signup");
			nextPage = signup(request, response);
			break;
		case GDETAIL:
			System.out.println("case - group detail");
			nextPage = gDetail(request, response);
			break;
		case GMEMBER:
			System.out.println("case - view member");
			nextPage = groupMember(request, response);
			break;
		case CGROUP:
			System.out.println("case - create group");
			htmlReturn = createGroup(request, response);
			isNextPage = false;
			break;
		case FRIENDSHIP:
			System.out.println("case - friendship");
			nextPage = friendship(request, response);
			break;
		case INVITETOGROUP:
			System.out.println("case - Invite member to group");
			htmlReturn = inviteToGroup(request, response);
			isNextPage = false;
			break;
		case MEMREQACT:
			System.out.println("case - member request action");
			memberReqAct(request, response);
			isNextPage = false;
			break;
		case FRIENDSHIPRESPONSE:
			System.out.println("case - friendship response");
			friendReqAct(request, response);
			isNextPage = false;
			break;
		case SENDINVITATION:
			System.out.println("case - send invitation to group");
			htmlReturn = sendInvitation(request, response);
			isNextPage = false;
			break;
		case MAKEASADMIN:
			System.out.println("case - send invitation to group");
			htmlReturn = makeAsAdmin(request, response);
			isNextPage = false;
			break;
		case KICKMEMBER:
			System.out.println("case - send invitation to group");
			htmlReturn = kickMember(request, response);
			isNextPage = false;
			break;
		case UPLOADCONTENT:
			System.out.println("case - Upload Content");
			htmlReturn = uploadContent(request, response);
			isNextPage = false;
			break;
		case CREATECOURSE:
			System.out.println("case - Create Course");
			htmlReturn = createCourse(request, response);
			isNextPage = false;
			break;
		case GBLOG:
			System.out.println("case - view Blog");
			nextPage = groupBlog(request, response);
			break;
		case ECOURSE:
			System.out.println("case - edit Blog");
			nextPage = editBlog(request, response);
			break;
		case UCOURSE:
			System.out.println("case - update Blog");
			nextPage = updateBlog(request, response);
			break;
		case VDCOURSE:
			System.out.println("case - update Blog");
			nextPage = vdBlog(request, response);
			break;
		case CREATEEVENT:
			System.out.println("case - Create Event");
			htmlReturn = createEvent(request, response);
			isNextPage = false;
			break;
		case CREATQUIZEVENT:
			System.out.println("case - Create Quiz Event");
			htmlReturn = createQuizEvent(request, response);
			isNextPage = false;
			break;
		case CREATQUESTION:
			System.out.println("case - Create Question");
			htmlReturn = createQuestion(request, response);
			isNextPage = false;
			break;
		case VIEWFILEINGROUP:
			System.out.println("case - view file in group");
			nextPage = viewFileInGroup(request, response);
			break;
		case ADDPOSTTOGROUP:
			System.out.println("case - add post to group");
			htmlReturn = addPostGroup(request, response);
			isNextPage = false;
			break;
		case VIEWQUIZINGROUP:
			System.out.println("case - view Quiz in Group");
			nextPage = viewQuizInGroup(request, response);
			break;
		case ATTEMPQUIZ:
			System.out.println("case - attempt quiz");
			nextPage = attemptQuiz(request, response);
			break;
		case UPDATEANSWER:
			System.out.println("case - Update answer");
			htmlReturn = updateAnswer(request, response);
			isNextPage = false;
			break;
		case SHOWSCOREDETAIL:
			System.out.println("case - Show score Detail");
			htmlReturn = showScoreDetail(request, response);
			isNextPage = false;
			break;
		case GROUPEVENT:
			System.out.println("case - view event");
			nextPage = groupEvent(request, response);
			break;
		case CONFIRMEVENT:
			System.out.println("case - CONFIRM");
			htmlReturn = confirmEvent(request, response);
			isNextPage = false;
			break;
		default:
			nextPage = "WEB-INF/error.jsp";
		}

		if (isNextPage) {
			System.out.println("nextPage : " + nextPage);
			RequestDispatcher dispatcher = request
					.getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);
		} else {
			PrintWriter out = response.getWriter();
			out.write(htmlReturn);
		}

	}

	private String confirmEvent(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		String cId = request.getParameter("id");
		String act = request.getParameter("act");
		System.out.println("sini");
		String x = "false";
		x = EventServiceImpl.getInstance(database, request, response)
				.confirmEvent(username, gID, cId, act);
		return x;
	}

	private String vdBlog(HttpServletRequest request,
			HttpServletResponse response) {
		String cId = request.getParameter("cID");
		String gId = request.getParameter("gID");
		session = request.getSession();
		try {
			if (!gId.equals("null")) {
				session.setAttribute("sessionSelectedGroup", gId);
				groupSessionList(request, response);
			}
		} catch (Exception e) {
			session = request.getSession();
			gId = (String) session.getAttribute("sessionSelectedGroup");
		}

		request.setAttribute("contentList",
				ContentServiceImpl.getInstance(database, request, response)
						.viewContent(cId));
				
		request.setAttribute("titleList",
				BlogServiceImpl.getInstance(database, request, response)
						.titleListCatGrup(gId,cId));
		request.setAttribute("catList",
				BlogServiceImpl.getInstance(database, request, response)
						.categoryListGroup(gId));
		return "group.jsp";
	}

	private String updateBlog(HttpServletRequest request,
			HttpServletResponse response) {
		String next = "";
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String cId = request.getParameter("blogId");
		String title = request.getParameter("blogName");
		String body = request.getParameter("blogBody");
		String category = request.getParameter("category");
		if (category.equals("")) {
			category="1";
		}
		if (WikiServiceImpl.getInstance(database, request, response)
				.updateWiki(cId, username, title, body, category)) {
			request.setAttribute("contentList",
					BlogServiceImpl.getInstance(database, request, response)
							.viewDetailBlog(cId));
			next = "gBlog.jsp";
		} else {
			request.setAttribute("contentList",
					BlogServiceImpl.getInstance(database, request, response)
							.viewDetailBlog(cId));
			next = "gBlogEdit.jsp";
		}
		return next;
	}

	private String editBlog(HttpServletRequest request,
			HttpServletResponse response) {
		String cId = request.getParameter("cID");
		String gId = request.getParameter("gID");
		session = request.getSession();
		try {
			if (!gId.equals("null")) {
				session.setAttribute("sessionSelectedGroup", gId);
				groupSessionList(request, response);
			}
		} catch (Exception e) {
			session = request.getSession();
			gId = (String) session.getAttribute("sessionSelectedGroup");
		}
		request.setAttribute("catList",
				BlogServiceImpl.getInstance(database, request, response)
						.categoryListGroup(gId));
		request.setAttribute("titleList",
				BlogServiceImpl.getInstance(database, request, response)
						.titleListCatGrup(gId,cId));
		String username = (String) session.getAttribute("sessionUsername");
		request.setAttribute("contentList",
				BlogServiceImpl.getInstance(database, request, response)
						.viewDetailBlog(cId));
		return "gBlogEdit.jsp";
	}

	private String groupBlog(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		request.setAttribute("contentList",
				ContentServiceImpl.getInstance(database, request, response)
						.viewCourse(gID));
		request.setAttribute("titleList",
				BlogServiceImpl.getInstance(database, request, response)
						.titleListType(gID,"3"));
		return "gBlog.jsp";
	}

	private String createCourse(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		String title = request.getParameter("titleBlog");
		String body = request.getParameter("bodyBlog");
		String category = request.getParameter("category");
		if (category.equals("")) {
			category="1";
		}
		htmlReturn = ContentServiceImpl
				.getInstance(database, request, response).addCourse(username,
						title, body, gID, category);
		System.out.println("ret=" + htmlReturn);
		return htmlReturn;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private String signup(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session, sessionGroup;
		session = request.getSession();
		sessionGroup = request.getSession();
		String username = request.getParameter("uname");
		String password = request.getParameter("passwd");
		String fistname = request.getParameter("fname");
		String lastname = request.getParameter("lname");
		session.setAttribute("userInfo",
				LoginServiceImpl.getInstance(database, request, response)
						.signUp(username, password, fistname, lastname));
		sessionGroup.setAttribute("groupList",
				LoginServiceImpl.getInstance(database, request, response)
						.groupList(username));
		session.setAttribute("newsList",
				NewsServiceImpl.getInstance(database, request, response)
						.viewNews());
		String posisi = getServletContext().getRealPath("")
				+ "/sources/userContent/" + username;
		new File(posisi).mkdir();
		return "index.jsp";
	}

	private String login(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		HttpSession session2 = request.getSession();
		List<ContentBean> content = new ArrayList<>();
		String username = request.getParameter("uname");
		String password = request.getParameter("passwd");
		if (LoginServiceImpl.getInstance(database, request, response)
				.checkLogin(username, password) == null) {
			return "login.jsp";
		} else {
			session.setAttribute("userInfo",
					LoginServiceImpl.getInstance(database, request, response)
							.checkLogin(username, password));
			session.setAttribute("newsList",
					NewsServiceImpl.getInstance(database, request, response)
							.viewNews());
			if (username.equals("pemkot")) {
				request.setAttribute("contentList", BlogServiceImpl
						.getInstance(database, request, response)
						.viewWikiNews());
				session.setAttribute("sessionSelectedGroup", "0");
				return "wiki.jsp";
			} else {
				session2.setAttribute(
						"groupList",
						LoginServiceImpl.getInstance(database, request,
								response).groupList(username));
				content = ContentServiceImpl.getInstance(database, request,
						response).viewContentList(username);
				request.setAttribute("contentList", content);
				return "index.jsp";
			}
		}
	}

	private String logout(HttpServletRequest request,
			HttpServletResponse response) {
		// ServletContext loginSession = getServletContext();
		session = request.getSession();
		// loginSession.removeAttribute("loginSessionApp");
		session.removeAttribute("userInfo");
		session.removeAttribute("event");
		session.removeAttribute("feedBackID");
		session.removeAttribute("sessionRoleinGroup");
		return "login.jsp";
	}

	private String gDetail(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String gID = request.getParameter("gID");
		String username = (String) session.getAttribute("sessionUsername");
		String address = "";
		boolean ismember = GroupServiceImpl.getInstance(database, request,
				response).isMember(gID, username);
		if (ismember) {
			List<ContentBean> content = new ArrayList<>();
			content = GroupServiceImpl.getInstance(database, request, response)
					.contentList(gID, username);
			request.setAttribute("catList",
					BlogServiceImpl.getInstance(database, request, response)
							.categoryListGroup(gID));
			request.setAttribute("contentList", content);
			session.setAttribute("sessionSelectedGroup", gID);
			request.setAttribute("titleList",
					BlogServiceImpl.getInstance(database, request, response)
							.titleListGrup(gID));
			groupSessionList(request, response);
			address = "group.jsp";
		} else {
			GroupBean grup = GroupServiceImpl.getInstance(database, request,
					response).viewGroup(gID, username);
			request.setAttribute("group", grup);
			address = "groupNM.jsp";
		}
		request.setAttribute("groupList",
				LoginServiceImpl.getInstance(database, request, response)
						.groupList(username));
		return address;
	}

	private String groupMember(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		String username = (String) session.getAttribute("sessionUsername");
		// System.out.println("session username ="+username);
		request.setAttribute("groupList",
				LoginServiceImpl.getInstance(database, request, response)
						.groupList(username));
		request.setAttribute("titleList",
				BlogServiceImpl.getInstance(database, request, response)
						.titleListGrup(gID));
		request.setAttribute("memberList",
				GroupServiceImpl.getInstance(database, request, response)
						.memberList(gID));
		return "gMember.jsp";
	}

	private String memberReqAct(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		String userName = request.getParameter("userName");
		String invitor = (String) session.getAttribute("sessionUsername");
		String action = request.getParameter("status");
		GroupServiceImpl.getInstance(database, request, response)
				.memberRequestAction(userName, action, gID, invitor);
		return "gMember.jsp";
	}

	private String createGroup(HttpServletRequest request,
			HttpServletResponse response) {		
		String groupName = request.getParameter("groupName");
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String gID = GroupServiceImpl.getInstance(database, request, response)
				.createGroup(groupName, username);
		String posisi = getServletContext().getRealPath("")
				+ "/sources/GroupContent/" + gID;
		// System.out.println(posisi);
		session.setAttribute(
				"groupList",
				LoginServiceImpl.getInstance(database, request,
						response).groupList(username));
		new File(posisi).mkdir();
		return gID;
	}

	private String friendship(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		// System.out.println("ini bukan ="+request.getAttribute("a"));
		request.setAttribute("friendList",
				FriendServiceImpl.getInstance(database, request, response)
						.friendList(username));
		// request.setAttribute("userInfo",LoginServiceImpl.getInstance(database,
		// request, response).checkLogin(username, password));
		// request.setAttribute("groupList",LoginServiceImpl.getInstance(database,
		// request, response).groupList(username));
		return "friend.jsp";
	}

	private String friendReqAct(HttpServletRequest request,
			HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		String status = request.getParameter("status");
		FriendServiceImpl.getInstance(database, request, response)
				.approveFriend(id, status);
		return "friend.jsp";
	}

	private String inviteToGroup(HttpServletRequest request,
			HttpServletResponse response) {
		String username = request.getParameter("term");
		// System.out.println(username);
		String data = "";
		session = request.getSession();
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		data = GroupServiceImpl.getInstance(database, request, response)
				.invitationUserList(gID, username);
		// System.out.println("data = " + data);
		// data=
		// "[{\"id\":3,\"label\":\"Hazel Grouse\",\"value\":\"Hazel Grouse\"},{\"id\":22,\"label\":\"Wood Nuthatch\",\"value\":\"Wood Nuthatch\"},{\"id\":26,\"label\":\"Chaffinch\",\"value\":\"Chaffinch\"},{\"id\":28,\"label\":\"Hawfinch\",\"value\":\"Hawfinch\"}]";
		return data;
	}

	private String sendInvitation(HttpServletRequest request,
			HttpServletResponse response) {
		// System.out.println("send invitation");
		UserBean user;
		List<UserBean> users = new ArrayList();
		session = request.getSession();
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		String username = (String) session.getAttribute("sessionUsername");
		// String invitation[]=request.getParameterValues("tag");
		String invit = request.getParameter("member");
		String invitation[] = invit.split(",");
		// System.out.println(invit+"=");
		for (String s : invitation) {
			user = new UserBean();
			user.setUsername(s);
			users.add(user);
			// System.out.println("send"+s);
		}
		String html = GroupServiceImpl.getInstance(database, request, response)
				.inviteUser(users, gID, username);
		System.out.println(html);
		return html;
	}

	private void groupSessionList(HttpServletRequest request,
			HttpServletResponse response) {
		GroupBean groupInfo = new GroupBean();
		session = request.getSession();
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		String username = (String) session.getAttribute("sessionUsername");
		groupInfo = GroupServiceImpl.getInstance(database, request, response)
				.groupSessionList(gID, username);
		String groupName = groupInfo.getGroupName();
		session.setAttribute("sessionGroupName", groupName);
		// System.out.println("role ="+ groupInfo.getMember().get(0).getRole());
		int role = 0;
		try {
			role = groupInfo.getMember().get(0).getRole();
		} catch (Exception e) {
		}
		session.setAttribute("sessionRoleinGroup", role);
	}

	private String makeAsAdmin(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		String username = request.getParameter("username");
		String htmlReturn = GroupServiceImpl.getInstance(database, request,
				response).makeAsAdmin(gID, username);
		return htmlReturn;
	}

	private String kickMember(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "false";
		session = request.getSession();
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		String username = request.getParameter("username");
		boolean success = GroupServiceImpl.getInstance(database, request,
				response).deleteMember(username, gID);
		if (success)
			htmlReturn = "true";
		return htmlReturn;
	}

	private String uploadContent(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		String username = (String) session.getAttribute("sessionUsername");
		String finalLocation = "";
		String webInfPath = getServletContext().getRealPath("");
		// System.out.println(webInfPath);
		String success = ContentServiceImpl.getInstance(database, request,
				response).uploadContent(request, webInfPath, gID.trim(),
				username);
		return success;
	}

	private String createEvent(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "0";
		String startDate = request.getParameter("eventStart");
		String endDate = request.getParameter("eventEnd");
		String startTime = startDate + " " + request.getParameter("timeStart");
		String endTime = endDate + " " + request.getParameter("timeEnd");
		String title = request.getParameter("eventName");
		String body = request.getParameter("eventDetail");
		String place = request.getParameter("eventPlace");
		String category = request.getParameter("category");
		if (category.equals("")) {
			category="1";
		}
		session = request.getSession();
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		String username = (String) session.getAttribute("sessionUsername");
		// System.out.println(startDate+"  "+startTime);
		htmlReturn = EventServiceImpl.getInstance(database, request, response)
				.createEvent(startTime, endTime, place, username, gID, title,
						body, category);
		return htmlReturn;
	}

	private String createQuizEvent(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "0";
		session = request.getSession();
		String startDate = request.getParameter("QuizStart");
		String endDate = request.getParameter("QuizEnd");
		String startTime = startDate + " "
				+ request.getParameter("quizTimeStart");
		String endTime = endDate + " " + request.getParameter("quizTimeEnd");
		String title = request.getParameter("QuizTitle");
		String body = request.getParameter("QuizDesc");
		String cat = request.getParameter("quizCat");
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		String username = (String) session.getAttribute("sessionUsername");
		htmlReturn = EventServiceImpl
				.getInstance(database, request, response)
				.createQuizEvent(startTime, endTime, username, gID, title, body, cat);
		session.setAttribute("seesionEID", htmlReturn);
		// System.out.println((String) session.getAttribute("seesionEID"));
		return htmlReturn;
	}

	private String createQuestion(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "0";
		session = request.getSession();
		String eventID = (String) session.getAttribute("seesionEID");
		String answer1 = request.getParameter("answer1");
		String answer2 = request.getParameter("answer2");
		String answer3 = request.getParameter("answer3");
		String answer4 = request.getParameter("answer4");
		String answer5 = request.getParameter("answer5");
		String soal = request.getParameter("soal");
		String key = request.getParameter("key");
		String answer1Array[] = answer1.split(",");
		String answer2Array[] = answer2.split(",");
		String answer3Array[] = answer3.split(",");
		String answer4Array[] = answer4.split(",");
		String answer5Array[] = answer5.split(",");
		String keyArray[] = key.split(",");
		String soalArray[] = soal.split(",");
		for (int i = 0; i < answer1Array.length; i++) {
			// bug event_id
			htmlReturn += EventServiceImpl.getInstance(database, request,
					response).createQuestion(eventID, soalArray[i],
					keyArray[i], 1, answer1Array[i], answer2Array[i],
					answer3Array[i], answer4Array[i], answer5Array[i]);
		}
		session.removeAttribute("seesionEID");
		return htmlReturn;
	}

	private String viewFileInGroup(HttpServletRequest request,
			HttpServletResponse response) {
		String nextPage = "gFile.jsp";
		session = request.getSession();
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		request.setAttribute("fileList",
				ContentServiceImpl.getInstance(database, request, response)
						.viewFileInGroup(gID));
		request.setAttribute("titleList",
				BlogServiceImpl.getInstance(database, request, response)
						.titleListType(gID,"4"));
		return nextPage;
	}

	private String addPostGroup(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "0";
		session = request.getSession();
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		String username = (String) session.getAttribute("sessionUsername");
		htmlReturn = "";
		return htmlReturn;
	}

	private String groupEvent(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		String username = (String) session.getAttribute("sessionUsername");
		// request.setAttribute("groupList",
		// LoginServiceImpl.getInstance(database, request, response)
		// .groupList(username));
		request.setAttribute("eventList",
				EventServiceImpl.getInstance(database, request, response)
						.eventList(gID, username));
		request.setAttribute("titleList",
				BlogServiceImpl.getInstance(database, request, response)
						.titleListType(gID,"5"));
		return "gEvent.jsp";
	}

	private String viewQuizInGroup(HttpServletRequest request,
			HttpServletResponse response) {
		String nextPage = "gKuis.jsp";
		session = request.getSession();
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		String username = (String) session.getAttribute("sessionUsername");
		request.setAttribute("titleList",
				BlogServiceImpl.getInstance(database, request, response)
						.titleListType(gID,"5"));
		request.setAttribute("quizList",
				ContentServiceImpl.getInstance(database, request, response)
						.viewQuestionList(gID, username));
		request.setAttribute("scoreList",
				ContentServiceImpl.getInstance(database, request, response)
						.showScore(gID, username));
		return nextPage;
	}

	private String attemptQuiz(HttpServletRequest request,
			HttpServletResponse response) {
		Object a = null;
		EventBean event = new EventBean();
		String nextPage = "gDoKuis.jsp";
		String eventID = request.getParameter("eventID");
		session = request.getSession();
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		String username = (String) session.getAttribute("sessionUsername");
		a = session.getAttribute("event");
		System.out.println(a);
		if (a == null) {
			
			event=ContentServiceImpl.getInstance(database, request, response)
					.attemptDoQuiz(username, gID, eventID);
			session.setAttribute("event", event);
			session.setAttribute("feedBackID", event.getParticipant().getParticipantID());
			//System.out.println("get participant : "+event.getParticipant());
			System.out.println("attemt quiz");
		}
		int fID = (Integer)session.getAttribute("feedBackID");
		request.setAttribute("time",
				ContentServiceImpl.getInstance(database, request, response)
						.getTime(fID));
		/*
		 * try { Thread.sleep(10000); request.setAttribute("time",
		 * ContentServiceImpl.getInstance(database, request,
		 * response).getTime()); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		return nextPage;
	}

	private String updateAnswer(HttpServletRequest request,
			HttpServletResponse response) {
		String answer = request.getParameter("answer");
		String qID = request.getParameter("qID");
		session = request.getSession();
		int fID = (Integer) session.getAttribute("feedBackID");
		String htmlReturn = ContentServiceImpl.getInstance(database, request,
				response).updateAnswer(answer, fID, qID);
		return htmlReturn;
	}

	private String showScoreDetail(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		String eID = request.getParameter("eventID");
		String fID = request.getParameter("feedbackID");
		htmlReturn = ContentServiceImpl
				.getInstance(database, request, response).showScoreDetail(eID,
						fID);
		return htmlReturn;
	}
}
