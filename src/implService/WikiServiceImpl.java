package implService;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import bean.ContentBean;
import bean.DiscussionBean;
import bean.FileBean;
import bean.GroupBean;
import bean.UserBean;
import service.BlogService;
import service.DatabaseAkses;
import service.WikiService;

public class WikiServiceImpl implements WikiService {

	private DatabaseAkses database;
	private static WikiServiceImpl instance;
	private HttpServletResponse response;
	private HttpServletRequest request;

	private WikiServiceImpl(DatabaseAkses database, HttpServletRequest request,
			HttpServletResponse response) {
		this.database = database;
		this.request = request;
		this.response = response;
	}

	public static WikiServiceImpl getInstance(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		if (instance == null) {
			instance = new WikiServiceImpl(database, request, response);
		}
		return instance;
	}

	@Override
	public String addWiki(String username, String title, String body, String category) {
		String id = "0";
		try {
			String query = "Select addWiki('" + username + "', '" + title
					+ "', '" + body + "', '"+category+"')";
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
	public List<ContentBean> viewWiki() {
		List<ContentBean> contentList = new ArrayList<>();
		try {
			String query = "Call viewWiki();";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			ContentBean content;
			UserBean user;
			while (rs.next()) {
				content = new ContentBean();
				content.setId(rs.getInt(1));
				content.setTitle(rs.getString(2));
				content.setBody(rs.getString(3));
				content.setPostedDate(rs.getString(4));
				user = new UserBean();
				user.setfName(rs.getString(5));
				content.setUser(user);
				contentList.add(content);
				// System.out.println(content.getId()+":"+content.getRating()+","+content.getComment());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return contentList;
	}

	@Override
	public boolean updateWiki(String id, String username, String title,
			String body, String category) {
		boolean status = false;
		try {
			String[] query = { "call updateWiki(" + id + ",'" + username
					+ "', '" + title + "', '" + body + "',"+category+")" };
			status = database.getInstance().executeUpdateQuery(query);
		System.out.println(query[0]);
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	@Override
	public String searchWiki(String key) {
		String result="";
		List<ContentBean> contentList = new ArrayList();
		try {
			String query = "CALL searchWiki('%"+key+"%');";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			ContentBean content;
			while (rs.next()) {
				content = new ContentBean();
				content.setTitle(rs.getString(2));
				content.setBody(rs.getString(3));
				content.setPostedDate(rs.getString(4));
				content.setId(rs.getInt(1));
				contentList.add(content);
				
				result+="<li class='resultWiki'><a href=\"Personal?action=vDetailWiki&wID="+content.getId()+"\">"+content.getTitle()+"</a> <span class='tglWiki'>Terakhir disunting "+content.getPostedDate()+"</span><div class='bodyWIki'>"+content.getBody()+"</div></li>";				
			}
			
		} catch (Exception e) {
			System.out.println(e);
			result="";
		}
		return result;
	}
}
