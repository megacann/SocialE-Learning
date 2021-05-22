package bean;

import java.util.List;

public class EventBean extends ContentBean {
	private int id;
	private String startTime;
	private String endTime;
	private String place;
	private int status;
	private List<QuestionBean> question;
	private List<ParticipantBean> participantList;
	private ParticipantBean participant;
	private int time;

	public List<ParticipantBean> getParticipantList() {
		return participantList;
	}
	public void setParticipantList(List<ParticipantBean> participantList) {
		this.participantList = participantList;
	}
	public ParticipantBean getParticipant() {
		return participant;
	}
	public void setParticipant(ParticipantBean participant) {
		this.participant = participant;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public List<QuestionBean> getQuestion() {
		return question;
	}
	public void setQuestion(List<QuestionBean> question) {
		this.question = question;
	}
	
	
}
