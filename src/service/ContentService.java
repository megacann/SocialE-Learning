package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import bean.ContentBean;
import bean.EventBean;

public interface ContentService {
	public String uploadContent(HttpServletRequest request,String finalLocation, String gID, String username);
	public List<ContentBean> viewFileInGroup(String gID);
	public String addPostGroup(String username, String gID, String gTtitle, String gBody);
	public List<ContentBean> viewContentList(String username); 
	public String addCourse(String username, String title, String body, String gID, String category);
	public List<ContentBean> viewCourse(String gID);
	public List<ContentBean> viewContent(String cID); 
	public List<ContentBean> viewQuestionList(String gID, String username);
	//public List<QuestionBean> attemptDoQuiz(String username, String groupID, String eventID);
	public EventBean attemptDoQuiz(String username, String groupID, String eventID);
	public String updateAnswer(String Answer, int fID, String qID);
	public List<ContentBean> showScore(String gID, String username);
	public String showScoreDetail(String eID, String fID);
}
