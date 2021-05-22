package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import bean.ContentBean;

public interface NewsService {
	public String addNews(HttpServletRequest request,String finalLocation, String tempLocation, String gID, String username);
	public List<ContentBean> viewNews();
}
