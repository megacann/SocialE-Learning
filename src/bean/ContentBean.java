package bean;
import java.util.List;

public class ContentBean {
	private int id;	
	private UserBean user;
	private String title;
	private String body;
	private String postedDate;
	private int type;
	private List<FileBean> file;
	private FileBean singleFile;
	private List<EventBean> event;
	private EventBean singleEvent;
	private List<DiscussionBean> comment;
	private GroupBean group;
	private String category;
	
	
	public FileBean getSingleFile() {
		return singleFile;
	}
	public void setSingleFile(FileBean singleFile) {
		this.singleFile = singleFile;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public List<EventBean> getEvent() {
		return event;
	}
	public void setEvent(List<EventBean> event) {
		this.event = event;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(String date) {
		this.postedDate = date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<FileBean> getFile() {
		return file;
	}
	public void setFile(List<FileBean> file) {
		this.file = file;
	}
	public List<DiscussionBean> getComment() {
		return comment;
	}
	public void setComment(List<DiscussionBean> comment) {
		this.comment = comment;
	}
	
	public GroupBean getGroup() {
		return group;
	}
	public void setGroup(GroupBean group) {
		this.group = group;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public EventBean getSingleEvent() {
		return singleEvent;
	}
	public void setSingleEvent(EventBean singleEvent) {
		this.singleEvent = singleEvent;
	}
}
