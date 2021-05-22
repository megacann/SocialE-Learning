package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import bean.ContentBean;

public interface WikiService {
	public String addWiki(String username, String title, String body, String category);
	public boolean updateWiki(String id, String username, String title, String body, String category);
	public List<ContentBean> viewWiki();
	public String searchWiki(String key);
}
