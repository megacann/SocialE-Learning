package controller;

import implService.BlogServiceImpl;
import implService.ContentServiceImpl;
import implService.GroupServiceImpl;
import implService.FriendServiceImpl;
import implService.LoginServiceImpl;
import implService.MessageServiceImpl;
import implService.NewsServiceImpl;
import implService.NotifeServiceImpl;
import implService.ProfileServiceImpl;
import implService.WikiServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ContentBean;
import bean.MessageBean;
import service.DatabaseAkses;
import service.NewsService;

//import service.DatabaseAkses;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Personal")
public class PersonalSpaceController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PersonalSpaceController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	HttpSession session;
	private static final int ERROR = -1;
	private static final int VPROFILE = 0;
	private static final int UPROFILE = 1;
	private static final int AEDUCATION = 2;
	private static final int DEDUCATION = 3;
	private static final int ASKILL = 4;
	private static final int DSKILL = 5;
	private static final int VBLOG = 6;
	private static final int ABLOG = 7;
	private static final int DBLOG = 8;
	private static final int UBLOG = 9;
	private static final int VDETAILBLOG = 10;
	private static final int ACOMMENT = 11;
	private static final int DCOMMENT = 12;
	private static final int VNOTIFE = 13;
	private static final int DNOTIFE = 14;
	private static final int CNOTIFE = 15;
	private static final int UNOTIFE = 16;
	private static final int VMESSAGE = 17;
	private static final int VDETAILMESSAGE = 18;
	private static final int DMESSAGE = 19;
	private static final int AMESSAGE = 20;
	private static final int CMESSAGE = 21;
	private static final int SEARCH = 22;
	private static final int VALLUSER = 23;
	private static final int VUSER = 24;
	private static final int VBLOGUSER = 25;
	private static final int VFRIENDUSER = 26;
	private static final int VGROUPUSER = 27;
	private static final int AFRIEND = 28;
	private static final int CFRIEND = 29;
	private static final int HOMEPEMKOT = 30;
	private static final int UWIKI = 31;
	private static final int EDITWIKI = 32;
	private static final int AWIKI = 33;
	private static final int ALLWIKI = 34;
	private static final int VDWIKI = 35;
	private static final int UFOTO = 36;
	private static final int EDITBLOG = 37;
	private static final int ALLNEWS = 38;
	private static final int UPLOADNEWS = 39;
	private static final int HOME = 40;
	private static final int AFSEARCH = 41;
	private static final int AGSEARCH = 42;
	private static final int UPASSWORD = 43;
	private static final int BANDUNGWIKI = 44;
	private static final int SWIKI = 45;
	private static final int UCOMMENT = 46;
	private static final int UPLOADIMAGES = 47;
	private static final int ADDSTATUS = 48;
	private static final int ADDCATEGORY = 49;
	private static final int UNEWS = 50;

	String actions[] = { "vProfile", "uProfile", "aEducation", "dEducation",
			"aSkill", "dSkill", "Blogs", "aBlog", "dBlog", "uBlog",
			"vDetailBlog", "aComment", "dComment", "Notifications", "dNotife",
			"cNotife", "uNotife", "Messages", "vDetailMessage", "dMessage",
			"aMessage", "cMessage", "search", "vAllUser", "vUser", "vBlogUser",
			"vFriendUser", "vGroupUser", "aFriend", "cFriend", "HomePemkot",
			"uWiki", "editWiki", "aWiki", "AllWiki", "vDetailWiki", "uFoto",
			"editBlog", "AllNews", "uploadNews", "Home", "aFSearch",
			"aGSearch", "uPassword", "BandungWiki", "searchWiki","uComment", "uploadImages", "addStatus","aCategory","uNews" };

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
		String nextPage = "";
		String htmlReturn = "";
		boolean isNextPage = true;
		switch (selectAction(request.getParameter("action"))) {
		case VPROFILE:
			nextPage = vProfile(request, response);
			break;
		case UPROFILE:
			htmlReturn = uProfile(request, response);
			isNextPage = false;
			break;
		case VBLOG:
			nextPage = vBlog(request, response);
			break;
		case VDETAILBLOG:
			nextPage = vDetailBlog(request, response);
			break;
		case EDITBLOG:
			nextPage = editBlog(request, response);
			break;
		case UBLOG:
			nextPage = uBlog(request, response);
			break;
		case VNOTIFE:
			nextPage = vNotife(request, response);
			break;
		case VMESSAGE:
			nextPage = vMessage(request, response);
			break;
		case SEARCH:
			nextPage = search(request, response);
			break;
		case AFSEARCH:
			htmlReturn = aFSearch(request, response);
			isNextPage = false;
			break;
		case AGSEARCH:
			htmlReturn = aGSearch(request, response);
			isNextPage = false;
			break;
		case VUSER:
			nextPage = vUser(request, response);
			break;
		case VBLOGUSER:
			nextPage = vBlogUser(request, response);
			break;
		case VFRIENDUSER:
			nextPage = vFriendUser(request, response);
			break;
		case VGROUPUSER:
			nextPage = vGroupUser(request, response);
			break;
		case AEDUCATION:
			htmlReturn = aEducation(request, response);
			isNextPage = false;
			break;
		case DEDUCATION:
			htmlReturn = dEducation(request, response);
			isNextPage = false;
			break;
		case ASKILL:
			htmlReturn = aSkill(request, response);
			isNextPage = false;
			break;
		case DSKILL:
			htmlReturn = dSkill(request, response);
			isNextPage = false;
			break;
		case ABLOG:
			htmlReturn = aBlog(request, response);
			isNextPage = false;
			break;
		case DBLOG:
			htmlReturn = dBlog(request, response);
			isNextPage = false;
			break;
		case ACOMMENT:
			htmlReturn = aComment(request, response);
			isNextPage = false;
			break;
		case DCOMMENT:
			htmlReturn = dComment(request, response);
			isNextPage = false;
			break;
		case CNOTIFE:
			htmlReturn = cNotife(request, response);
			isNextPage = false;
			break;
		case DNOTIFE:
			htmlReturn = dNotife(request, response);
			isNextPage = false;
			break;
		case UNOTIFE:
			htmlReturn = uNotife(request, response);
			isNextPage = false;
			break;
		case VDETAILMESSAGE:
			htmlReturn = vDetailMessage(request, response);
			isNextPage = false;
			break;
		case DMESSAGE:
			htmlReturn = dMessage(request, response);
			isNextPage = false;
			break;
		case CMESSAGE:
			htmlReturn = cMessage(request, response);
			isNextPage = false;
			break;
		case AMESSAGE:
			htmlReturn = aMessage(request, response);
			isNextPage = false;
			break;
		case VALLUSER:
			isNextPage = false;
			htmlReturn = vAllUser(request, response);
			break;
		case AFRIEND:
			htmlReturn = aFriend(request, response);
			isNextPage = false;
			break;
		case CFRIEND:
			htmlReturn = cFriend(request, response);
			isNextPage = false;
			break;
		case HOMEPEMKOT:
			nextPage = homePemkot(request, response);
			// nextPage = vWiki(request, response);
			break;
		case UWIKI:
			nextPage = uWiki(request, response);
			break;
		case EDITWIKI:
			nextPage = editWiki(request, response);
			break;
		case AWIKI:
			htmlReturn = aWiki(request, response);
			isNextPage = false;
			break;
		case ALLWIKI:
			nextPage = allWiki(request, response);
			break;
		case VDWIKI:
			nextPage = vdWiki(request, response);
			break;
		case UFOTO:
			htmlReturn = uFoto(request, response);
			isNextPage = false;
			break;
		case ALLNEWS:
			nextPage = AllNews(request, response);
			break;
		case UPLOADNEWS:
			htmlReturn = uploadNews(request, response);
			isNextPage = false;
			break;
		case HOME:
			nextPage = home(request, response);
			break;
		case UPASSWORD:
			htmlReturn = uPassword(request, response);
			isNextPage = false;
			break;
		case BANDUNGWIKI:
			nextPage = bandungWiki(request, response);
			break;
		case SWIKI:
			htmlReturn = sWiki(request, response);
			isNextPage = false;
			break;
		case UCOMMENT:
			htmlReturn = uComment(request, response);
			isNextPage = false;
			break;
		case UPLOADIMAGES:
			htmlReturn = uploadImages(request, response);
			isNextPage = false;
			break;
		case ADDSTATUS:
			htmlReturn = addStatus(request, response);
			isNextPage = false;
			break;
		case ADDCATEGORY:
			htmlReturn = addCategory(request, response);
			isNextPage = false;
			break;
		case UNEWS:
			htmlReturn = uNews(request, response);
			isNextPage = false;
			break;
		default:
			nextPage = "WEB-INF/error.jsp";
		}

		if (isNextPage) {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);
		} else {
			PrintWriter out = response.getWriter();
			out.write(htmlReturn);
		}
	}

	private String uNews(HttpServletRequest request,
			HttpServletResponse response) {
		String finalLocation = "";
		// String finalLocation="c:\\";
		String tempLocation = "WEB-INF/Temp/";
		String webInfPath = getServletContext().getRealPath("");
		// System.out.println(webInfPath);
		String success = NewsServiceImpl.getInstance(database, request,
				response).uNews(request, webInfPath, tempLocation);
		// System.out.println(success);
		return success;
	}

	private String addCategory(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		String name = request.getParameter("nameCat");
		htmlReturn = BlogServiceImpl.getInstance(database, request, response)
				.addCategory(name);
		return htmlReturn;
	}

	private String addStatus(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String body = request.getParameter("bodyBlog");
		htmlReturn = BlogServiceImpl.getInstance(database, request, response)
				.addStatus(username, body);
		return htmlReturn;
	}

	private String uploadImages(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String finalLocation = getServletContext().getRealPath("")
				+ "/sources/userContent/" + username + "/";
		System.out.println("lokasi=" + finalLocation);
		String success = BlogServiceImpl.getInstance(database, request,
				response).uploadImg(request, finalLocation, username);
		return success;
	}

	private String uComment(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		String commentID = request.getParameter("cid");
		String body = request.getParameter("body");
		boolean a = BlogServiceImpl.getInstance(database, request, response)
				.editComment(commentID, body);
		if (a) {
			htmlReturn="true";
		}
		return htmlReturn;
	}

	private String sWiki(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		String key = request.getParameter("key");
		htmlReturn = WikiServiceImpl.getInstance(database, request, response)
				.searchWiki(key);
		return htmlReturn;
	}

	private String bandungWiki(HttpServletRequest request,
			HttpServletResponse response) {
		return "bandungWiki.jsp";
	}

	private String homePemkot(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("contentList",
				BlogServiceImpl.getInstance(database, request, response)
						.viewWikiNews());
		return "wiki.jsp";
	}

	private String uPassword(HttpServletRequest request,
			HttpServletResponse response) {
		String passO = request.getParameter("passO");
		String passN = request.getParameter("passN");
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String htmlReturn = ProfileServiceImpl.getInstance(database, request,
				response).updatePassword(username, passO, passN);
		return htmlReturn;
	}

	private String aGSearch(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		boolean success = false;
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String groupID = request.getParameter("groupID");
		int status = Integer.parseInt(request.getParameter("status"));
		switch (status) {
		case 0:
			htmlReturn = GroupServiceImpl.getInstance(database, request,
					response).addMember(username, groupID);
			break;
		case 1:
			success = GroupServiceImpl.getInstance(database, request, response)
					.deleteMember(username, groupID);
			if (success) {
				htmlReturn = "1";
			}
			break;
		case 2:
			success = GroupServiceImpl.getInstance(database, request, response)
					.deleteMember(username, groupID);
			if (success) {
				htmlReturn = "1";
			}
			break;
		default:
			htmlReturn = "0";
			break;
		}
		return htmlReturn;
	}

	private String aFSearch(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String username2 = request.getParameter("username");
		int status = Integer.parseInt(request.getParameter("status"));
		switch (status) {
		case 0:
			htmlReturn = FriendServiceImpl.getInstance(database, request,
					response).addFriend(username, username2);
			break;
		case 2:
			htmlReturn = FriendServiceImpl.getInstance(database, request,
					response).updateFriend(username, username2);
			break;
		default:
			htmlReturn = FriendServiceImpl.getInstance(database, request,
					response).deleteFriend(username, username2);
			break;
		}
		return htmlReturn;
	}

	private String home(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession();
		List<ContentBean> content = new ArrayList<>();
		String username = (String) session.getAttribute("sessionUsername");
		request.setAttribute("contentList", ContentServiceImpl.getInstance(database, request, response)
				.viewContentList(username));
		return "index.jsp";
	}

	private String uploadNews(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String gID = (String) session.getAttribute("sessionSelectedGroup");
		String username = (String) session.getAttribute("sessionUsername");
		String finalLocation = "";
		// String finalLocation="c:\\";
		String tempLocation = "WEB-INF/Temp/";
		String webInfPath = getServletContext().getRealPath("");
		// System.out.println(webInfPath);
		String success = NewsServiceImpl.getInstance(database, request,
				response).addNews(request, webInfPath, tempLocation,
				gID.trim(), username);
		// System.out.println(success);
		return success;
	}

	private String AllNews(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("contentList",
				NewsServiceImpl.getInstance(database, request, response)
						.viewNews());
		request.setAttribute("catList",
				BlogServiceImpl.getInstance(database, request, response)
						.categoryListGroup("0"));
		return "listEvent.jsp";
	}

	private String uBlog(HttpServletRequest request,
			HttpServletResponse response) {
		String next = "";
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String cId = request.getParameter("blogId");
		System.out.println(cId);
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
			next = "myBlog.jsp";
		} else {
			request.setAttribute("contentList",
					BlogServiceImpl.getInstance(database, request, response)
							.viewDetailBlog(cId));
			next = "eBlog.jsp";
			request.setAttribute("titleList",
					BlogServiceImpl.getInstance(database, request, response)
							.viewBlog(username));
		}
		return next;
	}

	private String editBlog(HttpServletRequest request,
			HttpServletResponse response) {
		String cId = request.getParameter("bID");
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		request.setAttribute("contentList",
				BlogServiceImpl.getInstance(database, request, response)
						.viewDetailBlog(cId));
		request.setAttribute("catList",
				BlogServiceImpl.getInstance(database, request, response)
						.categoryList(username));
		request.setAttribute("titleList",
				BlogServiceImpl.getInstance(database, request, response)
						.titleListCat(username,cId));
		return "eBlog.jsp";
	}

	private String uProfile(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String fName = request.getParameter("fName");
		String lName = request.getParameter("lName");
		String address = request.getParameter("address");
		String gender = request.getParameter("gender");
		String status = request.getParameter("status");
		String bPlace = request.getParameter("bPlace");
		String bDate = request.getParameter("bDate");
		if (bDate.equals("")) {
			bDate = "0001-01-01";
		}
		String religion = request.getParameter("religion");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String website = request.getParameter("website");

		String success = ProfileServiceImpl.getInstance(database, request,
				response).updateProfile(username, fName, lName, address,
				gender, status, bPlace, bDate, religion, phone, email, website);
		session.setAttribute("userInfo",
				ProfileServiceImpl.getInstance(database, request, response)
						.viewMyProfile(username));
		return success;
	}

	private String uFoto(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String finalLocation = getServletContext().getRealPath("")
				+ "/sources/userContent/" + username + "/";
		System.out.println("lokasi=" + finalLocation);
		String success = ProfileServiceImpl.getInstance(database, request,
				response).updateFoto(request, username, finalLocation);
		session.setAttribute("userInfo",
				ProfileServiceImpl.getInstance(database, request, response)
						.viewMyProfile(username));
		return success;
	}

	private String vdWiki(HttpServletRequest request,
			HttpServletResponse response) {
		String cId = request.getParameter("wID");
		request.setAttribute("contentList",
				BlogServiceImpl.getInstance(database, request, response)
						.viewDetailBlog(cId));
		request.setAttribute("titleList",
				BlogServiceImpl.getInstance(database, request, response)
						.titleListCatGrup("0",cId));
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		if (username.equals("pemkot")) {
			return "dWiki.jsp";
		} else {
			return "dBlog.jsp";
		}
	}

	private String allWiki(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("contentList",
				WikiServiceImpl.getInstance(database, request, response)
						.viewWiki());
		request.setAttribute("catList",
				BlogServiceImpl.getInstance(database, request, response)
						.categoryListGroup("0"));
		return "listWiki.jsp";
	}

	private String aWiki(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String title = request.getParameter("titleWiki");
		String body = request.getParameter("bodyWiki");
		String category = request.getParameter("category");
		if (category.equals("")) {
			category="1";
		}
		htmlReturn = WikiServiceImpl.getInstance(database, request, response)
				.addWiki(username, title, body, category);
		return htmlReturn;
	}

	private String editWiki(HttpServletRequest request,
			HttpServletResponse response) {
		String cId = request.getParameter("wID");
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		request.setAttribute("contentList",
				BlogServiceImpl.getInstance(database, request, response)
						.viewDetailBlog(cId));
		request.setAttribute("catList",
				BlogServiceImpl.getInstance(database, request, response)
						.categoryListGroup("0"));
		request.setAttribute("titleList",
				BlogServiceImpl.getInstance(database, request, response)
						.titleListCatGrup("0",cId));
		if (username.equals("pemkot")) {
			return "eWiki.jsp";			
		} else {
			return "eWiki2.jsp";
		}

	}

	private String uWiki(HttpServletRequest request,
			HttpServletResponse response) {
		String next = "";
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String cId = request.getParameter("wikiId");
		String title = request.getParameter("wikiName");
		String body = request.getParameter("wikiBody");
		String category = request.getParameter("category");
		if (category.equals("")) {
			category="1";
		}

		if (WikiServiceImpl.getInstance(database, request, response)
				.updateWiki(cId, username, title, body, category)) {
			request.setAttribute("contentList",
					BlogServiceImpl.getInstance(database, request, response)
							.viewDetailBlog(cId));
			if (username.equals("pemkot")) {
				next = "dWiki.jsp";
			} else {
				next = "dBlog.jsp";
			}
			
		} else {
			request.setAttribute("contentList",
					BlogServiceImpl.getInstance(database, request, response)
							.viewDetailBlog(cId));
			if (username.equals("pemkot")) {
				next = "eWiki.jsp";
			} else {
				next = "eBlog.jsp";
			}
		}
		return next;
	}

	private String vWiki(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("contentList",
				BlogServiceImpl.getInstance(database, request, response)
						.viewWiki());
		return "wiki.jsp";
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private String cFriend(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "0";
		try {
			session = request.getSession();
			String username = (String) session.getAttribute("sessionUsername");
			if (!username.equals("null")) {
				htmlReturn = FriendServiceImpl.getInstance(database, request,
						response).countFriendPending(username);
			}
		} catch (Exception e) {
			htmlReturn = "0";
		}
		return htmlReturn;
	}

	private String aFriend(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String username2 = (String) session.getAttribute("sessionOtherUser");
		int status = Integer.parseInt(request.getParameter("status"));
		switch (status) {
		case 0:
			htmlReturn = FriendServiceImpl.getInstance(database, request,
					response).addFriend(username, username2);
			break;
		case 2:
			htmlReturn = FriendServiceImpl.getInstance(database, request,
					response).updateFriend(username, username2);
			break;
		default:
			htmlReturn = FriendServiceImpl.getInstance(database, request,
					response).deleteFriend(username, username2);
			break;
		}
		return htmlReturn;
	}

	private String dEducation(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		String skillID = request.getParameter("eduID");
		htmlReturn = ProfileServiceImpl
				.getInstance(database, request, response).deleteEducation(
						skillID);
		return htmlReturn;
	}

	private String dSkill(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		String skillID = request.getParameter("skillID");
		htmlReturn = ProfileServiceImpl
				.getInstance(database, request, response).deleteSkill(skillID);
		return htmlReturn;
	}

	private String vGroupUser(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String username = (String) session.getAttribute("sessionOtherUser");
		request.setAttribute("groupLists",
				LoginServiceImpl.getInstance(database, request, response)
						.groupList(username));
		return "pGroup.jsp";
	}

	private String vFriendUser(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String username = (String) session.getAttribute("sessionOtherUser");
		request.setAttribute("friendList",
				FriendServiceImpl.getInstance(database, request, response)
						.friendList(username));
		return "pFriend.jsp";
	}

	private String vBlogUser(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String username = (String) session.getAttribute("sessionOtherUser");
		request.setAttribute("contentList",
				BlogServiceImpl.getInstance(database, request, response)
						.viewBlog(username));
		return "pBlog.jsp";
	}

	private String vUser(HttpServletRequest request,
			HttpServletResponse response) {
		String returnAddres = "profile.jsp";
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String otheruser = request.getParameter("username");
		session.setAttribute("sessionOtherUser", otheruser);
		session.setAttribute("userProfile",
				ProfileServiceImpl.getInstance(database, request, response)
						.viewProfile(otheruser, username));
		if (otheruser.equals(username)) {
			returnAddres = "myProfile.jsp";
		}
		return returnAddres;
	}

	private String vAllUser(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		String key = request.getParameter("key");
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		if (!key.equals("")) {
			htmlReturn = ProfileServiceImpl.getInstance(database, request,
					response).viewAllUser(username, key);

		} else {
			htmlReturn = "";
		}
		return htmlReturn;
	}

	private String cMessage(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "0";
		try {
			session = request.getSession();
			String username = (String) session.getAttribute("sessionUsername");
			if (!username.equals("null")) {
				htmlReturn = MessageServiceImpl.getInstance(database, request,
						response).countMessage(username);
			}
		} catch (Exception e) {
			htmlReturn = "0";
		}
		return htmlReturn;
	}

	private String aMessage(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		session = request.getSession();
		String body = request.getParameter("body");
		String name = request.getParameter("name");
		String sender = (String) session.getAttribute("sessionUsername");
		// String category=request.getParameter("category");
		System.out.println("Controler");
		htmlReturn = MessageServiceImpl
				.getInstance(database, request, response).addMessage(sender,
						body, name);
		return htmlReturn;
	}

	private String dMessage(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		String receiver = request.getParameter("receiver");
		session = request.getSession();
		String sender = (String) session.getAttribute("sessionUsername");
		htmlReturn = MessageServiceImpl
				.getInstance(database, request, response).deleteMessage(
						receiver, sender);
		return htmlReturn;
	}

	private String vMessage(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		request.setAttribute("receiverList",
				MessageServiceImpl.getInstance(database, request, response)
						.viewReceiver(username));
		if (username.equals("pemkot")) {
			return "messagePemkot.jsp";
		} else {
			return "message.jsp";
		}
		
	}

	private String vDetailMessage(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		List<MessageBean> messageList = new ArrayList<>();
		String receiver = request.getParameter("uName");
		session = request.getSession();
		String sender = (String) session.getAttribute("sessionUsername");
		messageList = MessageServiceImpl.getInstance(database, request,
				response).viewMessage(receiver, sender);
		MessageServiceImpl.getInstance(database, request, response)
				.updateMessage(sender, receiver);
		for (int i = 0; i < messageList.size(); i++) {
			htmlReturn += "<li class='r" + messageList.get(i).getStatus()
					+ "' id='" + messageList.get(i).getId()
					+ "'><a href='Personal?action=vUser&username="
					+ messageList.get(i).getReceiver()
					+ "'><span class='receiver'>"
					+ messageList.get(i).getSender()
					+ "</span></a><span class='sendTime'>"
					+ messageList.get(i).getTime()
					+ "</span><span class='bodyMsg'>"
					+ messageList.get(i).getBody() + "</span></li>";
		}
		return htmlReturn;
	}

	private String search(HttpServletRequest request,
			HttpServletResponse response) {
		String key = request.getParameter("keys");
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");

		request.setAttribute("userSearch",
				ProfileServiceImpl.getInstance(database, request, response)
						.searchUser(key, username));
		request.setAttribute("groupSearch",
				GroupServiceImpl.getInstance(database, request, response)
						.searchGroup(key, username));
		return "search.jsp";
	}

	private String vNotife(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		request.setAttribute("notifeList",
				NotifeServiceImpl.getInstance(database, request, response)
						.viewNotife(username));
		return "notife.jsp";
	}

	private String dNotife(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		htmlReturn = NotifeServiceImpl.getInstance(database, request, response)
				.deleteNotife(username);
		return htmlReturn;
	}

	private String cNotife(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "0";
		try {
			session = request.getSession();
			String username = (String) session.getAttribute("sessionUsername");
			if (!username.equals("null")) {
				htmlReturn = NotifeServiceImpl.getInstance(database, request,
						response).countNotife(username);
			}
		} catch (Exception e) {
			htmlReturn = "0";
		}
		return htmlReturn;
	}

	private String uNotife(HttpServletRequest request,
			HttpServletResponse response) {
		String nID = request.getParameter("notifID");
		NotifeServiceImpl.getInstance(database, request, response)
				.updateNotife(nID);
		return "";
	}

	private String dComment(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		String commentID = request.getParameter("commentID");
		htmlReturn = BlogServiceImpl.getInstance(database, request, response)
				.delComment(commentID);
		return htmlReturn;
	}

	private String aComment(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String name = (String) session.getAttribute("sessionName");
		String contentID = request.getParameter("contentID");
		String body = request.getParameter("body");
		// String category=request.getParameter("category");
		htmlReturn = BlogServiceImpl.getInstance(database, request, response)
				.addComment(username, name, contentID, body);
		return htmlReturn;
	}

	private String vBlog(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		request.setAttribute("contentList",
				BlogServiceImpl.getInstance(database, request, response)
						.viewBlog(username));
		request.setAttribute("titleList",
				BlogServiceImpl.getInstance(database, request, response)
						.titleList(username));
		request.setAttribute("catList",
				BlogServiceImpl.getInstance(database, request, response)
						.categoryList(username));
		return "myBlog.jsp";
	}

	private String aBlog(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String title = request.getParameter("titleBlog");
		String body = request.getParameter("bodyBlog");
		String category = request.getParameter("category");
		if (category.equals("")) {
			category="1";
		}
		htmlReturn = BlogServiceImpl.getInstance(database, request, response)
				.addBlog(username, title, body, category);
		return htmlReturn;
	}

	private String dBlog(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		String contentID = request.getParameter("contentID");
		htmlReturn = BlogServiceImpl.getInstance(database, request, response)
				.delBlog(contentID);
		return htmlReturn;
	}

	private String vDetailBlog(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String cId = request.getParameter("bID");
		request.setAttribute("contentList",
				BlogServiceImpl.getInstance(database, request, response)
						.viewDetailBlog(cId));
		request.setAttribute("titleList",
				BlogServiceImpl.getInstance(database, request, response)
						.titleListCat(username,cId));
		request.setAttribute("catList",
				BlogServiceImpl.getInstance(database, request, response)
						.categoryList(username));
		return "myBlog.jsp";
	}

	private String aSkill(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String skill = request.getParameter("nameS");
		htmlReturn = ProfileServiceImpl
				.getInstance(database, request, response).addSkill(username,
						skill);
		return htmlReturn;
	}

	private String aEducation(HttpServletRequest request,
			HttpServletResponse response) {
		String htmlReturn = "";
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		String institution = request.getParameter("instName");
		String course = request.getParameter("courseN");
		String start = request.getParameter("startY");
		String end = request.getParameter("endY");
		htmlReturn = ProfileServiceImpl
				.getInstance(database, request, response).addEducation(
						username, institution, course, start, end);
		return htmlReturn;
	}

	private String vProfile(HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
		String username = (String) session.getAttribute("sessionUsername");
		session.setAttribute("userProfile",
				ProfileServiceImpl.getInstance(database, request, response)
						.viewProfile(username, ""));
		
		if (username.equals("pemkot")) {
			return "pemkotProfile.jsp";
		} else {
			return "myProfile.jsp";
		}
	}

}
