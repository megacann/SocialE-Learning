package service;

import java.util.List;

import bean.ContentBean;

public interface EventService {
	public String createEvent(String startTime, String endTIme, String place, String username, String gID, String title, String body, String category);
	public String createQuizEvent(String startTime, String endTIme, String username, String gID, String title, String body, String category);
	public String createQuiz(String startTime, String endTIme, String username, String gID, String title, String body);
	public String createQuestion(String eventID, String question, String correct, int point, String A, String B, String C, String D, String E);
	
	public List<ContentBean> eventList(String gID, String username);
	public String confirmEvent(String username, String gID, String cId,String act);
}
