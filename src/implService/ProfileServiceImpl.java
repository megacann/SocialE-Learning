package implService;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import bean.DiscussionBean;
import bean.EducationBean;
import bean.SkillBean;
import bean.UserBean;
import service.DatabaseAkses;
import service.ProfileService;

public class ProfileServiceImpl implements ProfileService {

	private DatabaseAkses database;
	private static ProfileServiceImpl instance;
	private HttpServletResponse response;
	private HttpServletRequest request;

	private ProfileServiceImpl(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		this.database = database;
		this.request = request;
		this.response = response;
	}

	public static ProfileServiceImpl getInstance(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		if (instance == null) {
			instance = new ProfileServiceImpl(database, request, response);
		}
		return instance;
	}

	@Override
	public UserBean viewProfile(String username, String otherUser) {
		UserBean user;
		user = new UserBean();
		try {
			String query = "CALL viewUser('" + username + "');";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if (rs.next()) {
				user.setUsername(username);
				user.setfName(rs.getString(2));
				user.setlName(rs.getString(3));
				user.setBirthPlace(rs.getString(4));
				user.setBirthDate(rs.getString(5));
				user.setGender(rs.getInt(6));
				user.setReligion(rs.getInt(7));
				user.setStatus(rs.getInt(8));
				user.setAddress(rs.getString(9));
				user.setPhone(rs.getString(10));
				user.setEmail(rs.getString(11));
				user.setWebsite(rs.getString(12));
				if (rs.getString(13).equals("")) {
					user.setFoto("sources/images/userpic.gif");
				} else {
					user.setFoto("sources/userContent/" + username + "/"
							+ username + "." + rs.getString(13));
				}

				if (otherUser != "") {
					String query1 = "Call viewFriendship('" + otherUser
							+ "', '" + username + "');";
					ResultSet rs2 = database.getInstance().executeSelectQuery(
							query1);
					if (rs2.next()) {
						if (rs2.getString(1) == null) {
							user.setMembershipStatus(0);
						} else {
							user.setMembershipStatus(rs2.getInt(1));
						}
						if (rs2.getInt(1) == 2) {
							String query2 = "Call viewFriendship2('"
									+ otherUser + "', '" + username + "');";
							ResultSet rs3 = database.getInstance()
									.executeSelectQuery(query2);
							if (rs3.next()) {
								if (rs3.getString(1) != null) {
									user.setMembershipStatus(3);
								}
							}
						}
						System.out.println(user.getfName()
								+ user.getMembershipStatus());

					}

				}
			}

			// Riwayat Pendidikan
			List<EducationBean> education = new ArrayList();
			query = "CALL viewEducation('" + username + "');";
			rs = database.getInstance().executeSelectQuery(query);
			EducationBean edu;
			while (rs.next()) {
				edu = new EducationBean();
				edu.setId(rs.getInt(1));
				edu.setCourse(rs.getString(3));
				edu.setInstitutionName(rs.getString(4));
				edu.setStartYear(rs.getInt(5));
				edu.setEndYear(rs.getInt(6));
				education.add(edu);
			}
			user.setEducationList(education);

			// Daftar Kemampuan
			List<SkillBean> skills = new ArrayList();
			query = "CALL viewSkill('" + username + "');";
			rs = database.getInstance().executeSelectQuery(query);
			SkillBean skill;
			while (rs.next()) {
				skill = new SkillBean();
				skill.setId(rs.getInt(1));
				skill.setSkillName(rs.getString(3));
				skills.add(skill);
			}
			user.setSkillList(skills);
		} catch (SQLException e) {
			System.out.println(e);
		}
		return user;
	}

	@Override
	public UserBean editPassword(String username, String Password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addEducation(String username, String institution,
			String course, String start, String end) {
		String id = "0";
		try {
			String queryID = "Select addEducation('" + username + "', '"
					+ institution + "', '" + course + "', " + start + ", "
					+ end + ");";
			ResultSet rs = database.getInstance().executeSelectQuery(queryID);
			if (rs.next()) {
				id = (rs.getString(1));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return id;
	}

	@Override
	public String addSkill(String username, String skill) {
		String id = "0";
		try {
			String queryID = "Select addSkill('" + username + "','" + skill
					+ "');";
			ResultSet rs = database.getInstance().executeSelectQuery(queryID);
			if (rs.next()) {
				id = rs.getString(1);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return id;
	}

	@Override
	public List<UserBean> searchUser(String key, String username) {
		List<UserBean> userList = new ArrayList();
		try {
			String query = "Call searchUser('%" + key + "%','" + username
					+ "')";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			UserBean user;
			while (rs.next()) {
				user = new UserBean();
				user.setfName(rs.getString(1));
				user.setlName(rs.getString(2));
				user.setUsername(rs.getString(3));
				if (rs.getString(4).equals("")) {
					user.setFoto("sources/images/userpic.gif");
				} else {
					user.setFoto("sources/userContent/" + rs.getString(3) + "/"
							+ rs.getString(3) + "." + rs.getString(4));
				}

				String query1 = "Call viewFriendship('" + rs.getString(3)
						+ "', '" + username + "');";
				ResultSet rs2 = database.getInstance().executeSelectQuery(
						query1);
				if (rs2.next()) {
					if (rs2.getString(1) == null) {
						user.setMembershipStatus(0);
					} else {
						user.setMembershipStatus(rs2.getInt(1));
					}
					if (rs2.getInt(1) == 2) {
						String query2 = "Call viewFriendship2('" + username
								+ "', '" + rs.getString(3) + "');";
						ResultSet rs3 = database.getInstance()
								.executeSelectQuery(query2);
						if (rs3.next()) {
							if (rs3.getString(1) != null) {
								user.setMembershipStatus(3);
							}
						}
					}

				}
				System.out
						.println(user.getfName() + user.getMembershipStatus());
				userList.add(user);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return userList;
	}

	@Override
	public String viewAllUser(String username, String key) {
		String list = "";
		int i = 1;
		try {
			String queryID = "CALL selectAllUser('" + username + "','%" + key
					+ "%');";
			ResultSet rs = database.getInstance().executeSelectQuery(queryID);
			while (rs.next()) {
				list += "<li><a onclick='selectUser(" + i
						+ ");'><input type='hidden' id='username" + i
						+ "' value='" + rs.getString(1) + "'><span id='name"
						+ i + "'>" + rs.getString(2) + "<span></a></li>";
				i++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}

	@Override
	public String deleteSkill(String skillID) {
		String status = "0";
		try {
			String[] query = { "Call deleteSkill(" + skillID + ")" };
			boolean stat = database.getInstance().executeUpdateQuery(query);
			if (stat == true) {
				status = "1";
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	@Override
	public String deleteEducation(String eduID) {
		String status = "0";
		try {
			String[] query = { "Call deleteEducation(" + eduID + ")" };
			boolean stat = database.getInstance().executeUpdateQuery(query);
			if (stat == true) {
				status = "1";
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	@Override
	public String updateFoto(HttpServletRequest request, String username,
			String finalLocation) {
		int a = 0;
		boolean status = true;
		String nameandExt = "";
		String ext = "";
		String fileName = "";
		String currentName = "";
		String id = "", time = "";
		String title = "", desc = "", cat = "";
		String fullname = "";
		String contentLocation="";
		FileItem item = null;
		String location="", category="";
		int pageSize = 0;
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
			status = false;
		}
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
			item = (FileItem) itr.next();
			if (item.isFormField()) {
			}
		}
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
				System.out.println("until here");
				// simpan file
				if (ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg")
						|| ext.equalsIgnoreCase("bmp")
						|| ext.equalsIgnoreCase("gif")
						|| ext.equalsIgnoreCase("tiff")|| ext.equalsIgnoreCase("jpeg")) {
					try {
						if (a == 0) {
							try {
								String query = "call UpdateFoto('" + username + "','" + ext
										+ "')";
								System.out.println(query);
								ResultSet rs = database.getInstance().executeSelectQuery(query);
							} catch (Exception e) {
								status = false;
								System.out.println(e);
							}
						}
						a++;
						contentLocation=finalLocation;
						finalLocation = finalLocation
								+ username + "." + ext;
						File savedFile = new File(finalLocation);
						item.write(savedFile);						
					} catch (Exception e) {
						System.out.println(e);
						status = false;
					}
				} else {
					status = false;
				}
			}
		}
		if (status)
			return "sources/userContent/" + username + "/" + username + "."
					+ ext;
		else
			return "hayoo";
	}

	@Override
	public String updateProfile(String username, String fName, String lName,
			String address, String gender, String status, String bPlace,
			String bDate, String religion, String phone, String email,
			String website) {
		String success = "1";
		try {
			String query = "call updateProfile('" + username + "','" + fName
					+ "','" + lName + "','" + address + "'," + gender + ","
					+ status + ",'" + bPlace + "','" + bDate + "'," + religion
					+ ",'" + phone + "','" + email + "','" + website + "')";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			/*
			 * if(rs.next()){ currentName=rs.getString(1); }
			 */
		} catch (Exception e) {
			success = "0";
			System.out.println(e);
		}
		return success;
	}

	@Override
	public String updatePassword(String username, String passO, String passN) {
		String success = "1";
		try {
			String query[] = {"UPDATE t_user SET `PASSWORD` = MD5('" + passN + "') WHERE USERNAME = '" + username + "' AND PASSWORD=MD5('" + passO + "')"};
			boolean status = database.getInstance().executeUpdateQuery(query);
			String query1="CALL cekLogin('"+username+"','"+passN+"')";
			ResultSet rs = database.getInstance().executeSelectQuery(query1);
			if(rs.next()){
				success = "1";
			} else {
				success = "0";
			}
			/*
			 * if(rs.next()){ currentName=rs.getString(1); }
			 */
		} catch (Exception e) {
			success = "0";
			System.out.println(e);
		}
		return success;
	}

	@Override
	public UserBean viewMyProfile(String username) {
		UserBean info = null;
		try {
			String query = "CALL viewUser('" + username + "');";
			// System.out.println(query); //cek query
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if (rs.next()) {
				info = new UserBean();
				info.setfName(rs.getString(2));
				// System.out.println("info : "+rs.getString(1)+rs.getString(2));
				info.setlName(rs.getString(3));
				if (rs.getString(13).equals("")) {
					info.setFoto("sources/images/userpic.gif");
				} else {
					info.setFoto("sources/userContent/" + username + "/"
							+ username + "." + rs.getString(13));
				}

				info.setUsername(username);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return info;
	}
}
