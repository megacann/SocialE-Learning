package bean;
import java.util.List;

public class MessagesBean {
	private String sender;
	private List<MessageBean> messages;
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public List<MessageBean> getMessages() {
		return messages;
	}
	public void setMessages(List<MessageBean> messages) {
		this.messages = messages;
	}

	
}
