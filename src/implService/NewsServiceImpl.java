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
import bean.UserBean;
import service.BlogService;
import service.DatabaseAkses;
import service.NewsService;
import service.WikiService;

public class NewsServiceImpl implements NewsService {

	private DatabaseAkses database;
	private static NewsServiceImpl instance;
	private HttpServletResponse response;
	private HttpServletRequest request;

	private NewsServiceImpl(DatabaseAkses database, HttpServletRequest request,
			HttpServletResponse response) {
		this.database = database;
		this.request = request;
		this.response = response;
	}

	public static NewsServiceImpl getInstance(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		if (instance == null) {
			instance = new NewsServiceImpl(database, request, response);
		}
		return instance;
	}
	
	@Override
	public List<ContentBean> viewNews() {
		List<ContentBean> contentList = new ArrayList<>();
		try {
			String query = "Call viewNews();";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			ContentBean content;
			FileBean file;
			while (rs.next()) {
				content = new ContentBean();
				file = new FileBean();
				content.setId(rs.getInt(1));
				content.setTitle(rs.getString(2));
				content.setBody(rs.getString(3));
				content.setPostedDate(rs.getString(4));
				content.setCategory(rs.getString(8));
				if (!rs.getString(7).equals("")) {
					
					file.setFileName(rs.getString(6));
					file.setLocation("sources/GroupContent/0/" + rs.getString(5)
							+ "." + rs.getString(7));
				}
				
				content.setSingleFile(file);
				contentList.add(content);
				// System.out.println(content.getId()+":"+content.getRating()+","+content.getComment());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return contentList;
	}

	@Override
	public String addNews(HttpServletRequest request, String finalLocation, String tempLocation, String gID, String username) {
		int a=0;
		int test=0;
		String status = "0";
		String nameandExt = "";
		String ext = "";
		String fileName = "";
		String currentName = "";
		String id = "", time = "";
		String title = "", desc = "", cat = "";
		FileItem item = null;
		boolean isJudulKosong = false;
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
			item = (FileItem) itr.next();
			if (item.isFormField()) {
				String name = item.getFieldName();
				String value = item.getString();
				if (name.equals("title")) {
					title = value;
					if(title.equals("")){isJudulKosong=true;}
				}
				if (name.equals("desc")) {
					desc = value;
				}
				if (name.equals("category")) {
					cat = value;
					if (cat.equals("")) {
						cat = "1";
					}
				}

			}}
		 itr = items.iterator();
		while (itr.hasNext()) {
			item = (FileItem) itr.next();
			if (item.isFormField()) {
				

			} else {
				nameandExt = item.getName();
				String splits[] = nameandExt.split("\\.");				
				fileName= nameandExt.substring(0,nameandExt.lastIndexOf('.'));
				if(nameandExt.length()>45)
				fileName = fileName.substring(0,45);
				ext = splits[splits.length-1].toLowerCase();
				//simpan file
				if ((ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg")
						|| ext.equalsIgnoreCase("bmp") || ext.equalsIgnoreCase("gif")
						|| ext.equalsIgnoreCase("tiff") || ext.equalsIgnoreCase("jpeg") || ext.equals(""))  && isJudulKosong==false) {
					try {
						if(a==0){
							try {
								String query = "call UploadContent('"+username+"',"+gID+",'"+title+"','"+desc+"','6','"+fileName+"','"+ext+"',"+cat+");";
								System.out.println("query"+query);
								ResultSet rs=database.getInstance().executeSelectQuery(query);
								if(rs.next()){
									id = rs.getString(1);
									currentName = rs.getString(2);
									time = rs.getString(3);
								}
							} catch (Exception e) {
								status = "0";
								System.out.println(e);
							}
						}
						a++;
						finalLocation=finalLocation+"/sources/GroupContent/"+gID+"/"+currentName+"."+ext;
						File savedFile = new File(finalLocation);
						System.out.println("file ada di"+finalLocation);
						item.write(savedFile);
						status = "<tr id='wiki"+id+"'> <td><a href='Personal?action=vDetailWiki&wID="+id+"'>" + title + "</a></td> <td>" + desc + "</td> <td><img src="+"sources/GroupContent/" + gID
								+ "/" + currentName + "." + ext+"  width='80%' ></td> <td>"+time+"</td> <td><span class='glyphicon glyphicon-trash' id='delIcon' onclick='delWiki("+id+")'></span> <a href='Personal?action=editWiki&wID="+id+"'><span class='glyphicon glyphicon-edit'></span></a></td></tr>";
					} catch (Exception e) {
						System.out.println(e);
						status = "0";
					}
				} else {
					status = "salah";
					if(isJudulKosong){
						status = "judulKosong";
					}
				}
			}
		}
		return status;
	}

	public String uNews(HttpServletRequest request, String finalLocation, String tempLocation) {
		int a=0;
		int test=0;
		String status = "0";
		String nameandExt = "";
		String ext = "";
		String fileName = "";
		String currentName = "";
		String id = "", time = "";
		String title = "", desc = "", cat = "";
		FileItem item = null;
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List items = null;
		boolean isJudulKosong = false;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
			item = (FileItem) itr.next();
			if (item.isFormField()) {
				String name = item.getFieldName();
				String value = item.getString();
				if (name.equals("idNews")) {
					id = value;
				}
				if (name.equals("title")) {
					title = value;
					if(title.equals("")){isJudulKosong=true;}
				}
				if (name.equals("desc")) {
					desc = value;
				}
				if (name.equals("category2")) {
					cat = value;
					if (cat.equals("")) {
						cat = "1";
					}
				}

			}}
		 itr = items.iterator();
		while (itr.hasNext()) {
			item = (FileItem) itr.next();
			if (item.isFormField()) {
				

			} else {
				nameandExt = item.getName();
				if (!nameandExt.equals("")) {
					String splits[] = nameandExt.split("\\.");				
					fileName= nameandExt.substring(0,nameandExt.lastIndexOf('.'));
					if(nameandExt.length()>45)
					fileName = fileName.substring(0,45);
					ext = splits[splits.length-1].toLowerCase();
				}
				//simpan file
				if ((ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg")
						|| ext.equalsIgnoreCase("bmp") || ext.equalsIgnoreCase("gif")
						|| ext.equalsIgnoreCase("tiff") || ext.equalsIgnoreCase("jpeg")) && isJudulKosong==false) {
					try {
						if(a==0){
							try {
								String query= "call updateContent('"+title+"','"+desc+"','"+fileName+"','"+ext+"',"+id+","+cat+");";
								ResultSet rs=database.getInstance().executeSelectQuery(query);
								if(rs.next()){
									currentName = rs.getString(1);
									status="1";
								}
							} catch (Exception e) {
								status = "0";
								System.out.println(e);
							}
						}
						a++;
						finalLocation=finalLocation+"/sources/GroupContent/0/"+currentName+"."+ext;
						System.out.println(finalLocation);
						File savedFile = new File(finalLocation);
						item.write(savedFile);
					} catch (Exception e) {
						System.out.println(e);
						status = "0";
					}
				} else if (ext.equals("") && isJudulKosong==false) {
					try {
								String query[] = {"UPDATE t_content SET TITLE='"+title+"', TEXT='"+desc+"', TIME=NOW(), CATEGORY_ID="+cat+" WHERE CONTENT_ID = '"+id+"';"};
								System.out.println(query[0]);
								if(database.getInstance().executeUpdateQuery(query)){
									status="1";
								}
							} catch (Exception e) {
								status = "0";
								System.out.println(e);
							}
				} else {
					status = "salah";
					if(isJudulKosong){
						status = "judulKosong";
					}
				}
			}
		}
		return status;
	}
}
