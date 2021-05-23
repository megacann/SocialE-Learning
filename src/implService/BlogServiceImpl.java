package implService;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import bean.ContentBean;
import bean.DiscussionBean;
import bean.EventBean;
import bean.FileBean;
import bean.GroupBean;
import bean.QuestionBean;
import bean.UserBean;
import service.BlogService;
import service.DatabaseAkses;

public class BlogServiceImpl implements BlogService{

	private DatabaseAkses database;
	private static BlogServiceImpl instance;
	private HttpServletResponse response;
	private HttpServletRequest request;

	private BlogServiceImpl(DatabaseAkses database, HttpServletRequest request,
			HttpServletResponse response) {
		this.database = database;
		this.request = request;
		this.response = response;
	}

	public static BlogServiceImpl getInstance(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		if (instance == null) {
			instance = new BlogServiceImpl(database, request, response);
		}
		return instance;
	}

	
	@Override
	public String addBlog(String username, String title, String body, String category) {
		String id = "0";
		try {
			
			String query = "Select addBlog('" + username + "', '" + title
					+ "', '" + body + "', '" + category + "')";
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
	public List<ContentBean> viewBlog(String username) {
		List<ContentBean> contentList = new ArrayList<>();
		try {
			String query = "Call viewBlog('" + username + "');";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			ContentBean content;
			UserBean user;
			while (rs.next()) {
				content = new ContentBean();
				user = new UserBean();
				content.setId(rs.getInt(1));
				user.setUsername(username);
				content.setUser(user);
				content.setTitle(rs.getString(2));
				content.setBody(rs.getString(3));
				content.setPostedDate(rs.getString(4));
				content.setCategory(rs.getString(5));
				System.out.println(rs.getString(5));
				
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
				// System.out.println(content.getId()+":"+content.getRating()+","+content.getComment());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return contentList;
	}

	@Override
	public String addComment(String username, String name, String contentID,
			String body) {
		String id = "0";
		int idNotif = 0;
		try {
			String query = "Select addComment('" + username + "', " + contentID
					+ ", '" + body + "')";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if (rs.next()) {
				id = (rs.getString(1));
				String query2 = "Select addNotife(' mengomentari konten ', 'Personal?action=vDetailBlog&bID=', '"
						+ username + "'," + contentID + ")";
				ResultSet rs2 = database.getInstance().executeSelectQuery(
						query2);
				if (rs2.next()) {
					idNotif = rs2.getInt(1);
					String query3 = "call viewNotifUser('" + username + "',"
							+ contentID + ")";
					ResultSet rs3 = database.getInstance().executeSelectQuery(
							query3);
					System.out.println("3" + query3);
					while (rs3.next()) {
						String user = rs3.getString(1);
						String query4 = "select addNotifUser('" + user + "',"
								+ idNotif + ")";
						ResultSet rs4 = database.getInstance()
								.executeSelectQuery(query4);
						System.out.println("4" + query4);
						if (!rs4.next()) {
							id = ("0");
						}
					}
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return id;
	}

	@Override
	public String delComment(String id) {
		String status = "0";
		try {
			String[] query = { "Call deleteComment(" + id + ")" };
			boolean stat = database.getInstance().executeUpdateQuery(query);
			if (stat == true) {
				status = "1";
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	@Override
	public String delBlog(String id) {
		String status = "0";
		try {
			String[] query = { "Call deleteBlog(" + id + ")" };
			boolean stat = database.getInstance().executeUpdateQuery(query);
			if (stat == true) {
				status = "1";
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	@Override
	public List<ContentBean> viewDetailBlog(String blogID) {
		List<ContentBean> contentList = new ArrayList<>();
		try {
			String query = "Call viewDetailBlog(" + blogID + ");";
			
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			ContentBean content;
			UserBean user;
			while (rs.next()) {
				user = new UserBean();
				content = new ContentBean();
				content.setId(Integer.parseInt(blogID));
				user.setfName(rs.getString(1));
				content.setUser(user);
				content.setTitle(rs.getString(2));
				content.setBody(rs.getString(3));
				content.setPostedDate(rs.getString(4));
				content.setType(rs.getInt(5));
				content.setCategory(rs.getString(6));
				// select comment
				String queryComment = "Call viewComment(" + content.getId()
						+ ");";
				System.out.println(queryComment);
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
				if (content.getType() == 6) {
					// content sharing
					String queryFile = "CALL viewFile(" + content.getId()
							+ ");";
					System.out.println(queryFile);
					ResultSet rs3 = database.getInstance().executeSelectQuery(
							queryFile);
					while (rs3.next()) {
						FileBean file = new FileBean();
						if (!rs3.getString(4).equals("")) {
							file.setId(rs3.getInt(1));
							file.setFileName(rs3.getString(3)+ "." + rs3.getString(4));
							file.setLocation("sources/GroupContent/"+rs3.getInt(5)+"/" + rs3.getString(1)
									+ "." + rs3.getString(4));
							content.setSingleFile(file);
						}
					}
				}
				contentList.add(content);
				// System.out.println(content.getId()+":"+content.getRating()+","+content.getComment());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return contentList;
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
				user = new UserBean();
				content.setId(rs.getInt(1));
				content.setTitle(rs.getString(2));
				content.setBody(rs.getString(3));
				content.setPostedDate(rs.getString(4));
				user.setfName(rs.getString(5));
				content.setUser(user);

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
				// System.out.println(content.getId()+":"+content.getRating()+","+content.getComment());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return contentList;
	}

	@Override
	public List<ContentBean> viewWikiNews() {
		List<ContentBean> contentList = new ArrayList();
		try {
			String query = "CALL viewHomePemkot ();";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			ContentBean content;
			UserBean user;
			GroupBean group;
			while (rs.next()) {
				content = new ContentBean();
				user = new UserBean();
				group = new GroupBean();
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
				if (content.getType() == 6) {
					// content sharing
					String queryFile = "CALL viewFile(" + content.getId()
							+ ");";
					System.out.println(queryFile);
					ResultSet rs2 = database.getInstance().executeSelectQuery(
							queryFile);
					while (rs2.next()) {
						FileBean file = new FileBean();
						file.setId(rs2.getInt(1));
						file.setFileName(rs2.getString(3));
						file.setLocation("sources/GroupContent/0/" + rs2.getString(1)
								+ "." +rs2.getString(4));
						content.setSingleFile(file);
					}
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
	public boolean editComment(String id, String body) {
		boolean st=false;
		try {
			String[] query = { "Call editComment(" + id + ",'"+body+"')" };
			st = database.getInstance().executeUpdateQuery(query);
		} catch (Exception e) {
			st = false;
		}
		return st;
	}

	@Override
	public String uploadImg(HttpServletRequest request, String finalLocation, String username) {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		FileItem item = null;
		List items = null;
		boolean status = true;
		String nameandExt = "";
		String ext = "";
		String fileName = "";
		String currentName = "";
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		Iterator itr = items.iterator();
		// int a=0;
		while (itr.hasNext()) {
			item = (FileItem) itr.next();
			// System.out.println("name="+item.getFieldName());
			// System.out.println("name="+item.getString());
			if (item.isFormField()) {
			} else {
				nameandExt = item.getName();
				String splits[] = nameandExt.split("\\.");				
				ext = splits[splits.length-1].toLowerCase();
			}
		}
		if (ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg")
				|| ext.equalsIgnoreCase("bmp") || ext.equalsIgnoreCase("gif")
				|| ext.equalsIgnoreCase("tiff")|| ext.equalsIgnoreCase("jpeg")) {
			try {
				File savedFile = new File(finalLocation + nameandExt);
				System.out.println("file ada di" + finalLocation + nameandExt);
				item.write(savedFile);
				// copy(finalLocation,"F:\\New Data\\Skull\\Kuliah\\SI-34-03\\Tahun IV\\Semester VII\\TA\\Dropbox\\[TA] Aplikasi\\Eclipse Herdi\\SocialELearning\\WebContent\\sources\\GroupContent\\"+gID+"/"+currentName+"."+ext);
			} catch (Exception e) {
				System.out.println(e);
				status = false;
			}
		} else {
			status = false;
		}
		if (status)
			return "sources/userContent/" + username + "/" + nameandExt;
		else
			return "Gagal";
	}

	@Override
	public String addStatus(String username, String body) {
		String id = "0";
		try {
			
			String query = "Select addStatus('" + username + "', '" + body + "')";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			System.out.println(query);
			if (rs.next()) {
				id = (rs.getString(1));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return id;
	}

	@Override
	public Map<Integer, String> categoryList(String username) {
		Map<Integer, String> userListAndID = new TreeMap<Integer, String>();
		try {
			String query = "call viewCategoryUser ('" + username + "')";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			int id;
			String cat;
			while (rs.next()) {
				id = rs.getInt(1);
				cat = rs.getString(2);
				userListAndID.put(id, cat);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return userListAndID;

	}

	@Override
	public Map<Integer, String> titleList(String username) {
		Map<Integer, String> userListAndID = new TreeMap<Integer, String>();
		try {
			String query = "call viewTitleBlog('" + username + "')";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			int id;
			String cat;
			while (rs.next()) {
				id = rs.getInt(1);
				cat = rs.getString(2);
				userListAndID.put(id, cat);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return userListAndID;
	}

	@Override
	public String addCategory(String name) {
		String id = "0";
		try {
			String query = "Select addCategory('" + name+ "')";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			System.out.println(query);
			if (rs.next()) {
				id = (rs.getString(1));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return id;
	}

	@Override
	public Map<Integer, String> titleListCat(String username, String cId) {
		Map<Integer, String> userListAndID = new TreeMap<Integer, String>();
		try {
			String query = "call viewTitleBlogCat('" + username+ "', " + cId + ")";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			int id;
			String cat;
			while (rs.next()) {
				id = rs.getInt(1);
				cat = rs.getString(2);
				userListAndID.put(id, cat);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return userListAndID;

	}
	@Override
	public Map<Integer, String> titleListCatGrup(String gid, String cId) {
		Map<Integer, String> userListAndID = new TreeMap<Integer, String>();
		try {
			String query = "call viewTitleCat(" + gid+ ", " + cId + ")";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			int id;
			String cat;
			while (rs.next()) {
				id = rs.getInt(1);
				cat = rs.getString(2);
				userListAndID.put(id, cat);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return userListAndID;

	}

	@Override
	public Map<Integer, String> categoryListGroup(String gid) {
		Map<Integer, String> userListAndID = new TreeMap<Integer, String>();
		try {
			String query = "call viewCategoryGroup (" + gid+ ")";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			int id;
			String cat;
			while (rs.next()) {
				id = rs.getInt(1);
				cat = rs.getString(2);
				userListAndID.put(id, cat);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return userListAndID;
	}

	@Override
	public Map<Integer, String> titleListGrup(String gID) {
		Map<Integer, String> userListAndID = new TreeMap<Integer, String>();
		try {
			String query = "call viewTitleGroup(" + gID + ")";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			int id;
			String cat;
			while (rs.next()) {
				id = rs.getInt(1);
				cat = rs.getString(2);
				userListAndID.put(id, cat);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return userListAndID;
	}
	
	@Override
	public Map<Integer, String> titleListType(String gID, String type) {
		Map<Integer, String> userListAndID = new TreeMap<Integer, String>();
		try {
			String query = "call viewTitleType(" + gID + ", "+type+")";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			System.out.println(query);
			int id;
			String cat;
			while (rs.next()) {
				id = rs.getInt(1);
				cat = rs.getString(2);
				userListAndID.put(id, cat);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return userListAndID;
	}

	@Override
	public void train(String blogID) {
		// TODO Auto-generated method stub
		
	}

	
}
