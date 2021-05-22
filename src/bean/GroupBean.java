package bean;
import java.util.List;

public class GroupBean {
	private int id;
	private String groupName;
	private String birthdate;
	private List<UserBean> member;
	private List<UserBean> admin;
	private List<ContentBean> contents;
	private int status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public List<UserBean> getMember() {
		return member;
	}
	public void setMember(List<UserBean> member) {
		this.member = member;
	}
	public List<UserBean> getAdmin() {
		return admin;
	}
	public void setAdmin(List<UserBean> admin) {
		this.admin = admin;
	}
	public List<ContentBean> getContents() {
		return contents;
	}
	public void setContents(List<ContentBean> contents) {
		this.contents = contents;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
