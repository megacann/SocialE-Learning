package service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import bean.ContentBean;

public interface BlogService {
	public List<ContentBean> viewBlog(String username);
	public List<ContentBean> viewDetailBlog(String blogID);
	public String addBlog(String username, String title, String body, String category);
	public String addStatus(String username, String body);
	public String addComment(String username, String name, String contentID, String body);
	public String delBlog(String id);
	public String delComment(String id);
	public boolean editComment(String id, String body);
	public List<ContentBean> viewWiki();
	public List<ContentBean> viewWikiNews();
	public String uploadImg(HttpServletRequest request, String finalLocation, String username);
	public Map<Integer, String> categoryList(String username);
	public Map<Integer, String> categoryListGroup(String gid);
	public Map<Integer, String> titleList(String username);
	public Map<Integer, String> titleListCat(String username, String cId);
	public Map<Integer, String> titleListCatGrup(String gid, String cId);
	public String addCategory(String name);
	public Map<Integer, String> titleListGrup(String gID);
	public Map<Integer, String> titleListType(String gID, String type);
	public void train(String blogID);
	
}
