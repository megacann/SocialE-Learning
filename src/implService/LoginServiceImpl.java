package implService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.GroupBean;
import bean.UserBean;
import service.DatabaseAkses;
import service.LoginService;

public class LoginServiceImpl implements LoginService {
	
	private DatabaseAkses database;
	private static LoginServiceImpl instance;
	private HttpServletResponse response;
	private HttpServletRequest request;

	private LoginServiceImpl(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		this.database = database;
		this.request = request;
		this.response = response;
	}

	public static LoginServiceImpl getInstance(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		if (instance == null) {
			instance = new LoginServiceImpl(database, request, response);
		}
		return instance;
	}
	
	@Override
	public UserBean checkLogin(String username, String password) {
		UserBean info = null;
		try{
			String query="CALL cekLogin('"+username+"','"+password+"')";
			//System.out.println(query); //cek query
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if(rs.next()){
				info = new UserBean();
				info.setfName(rs.getString(1));
				//System.out.println("info : "+rs.getString(1)+rs.getString(2));
				info.setlName(rs.getString(2));
				if (rs.getString(3).equals("")) {
					info.setFoto("sources/images/userpic.gif");
				} else {
					info.setFoto("sources/userContent/"+username+"/"+username+"."+rs.getString(3));
				}
				
				info.setUsername(username);
			}
		}catch(SQLException e){
			System.out.println(e);
		}
		return info;
	}

	@Override
	public List<GroupBean> groupList(String username) {
		List<GroupBean> userGroupList = new ArrayList();
		try{
			String query="CALL viewGroupList('"+username+"')";
			System.out.println(query); //cek query
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			GroupBean groupLists;
			while(rs.next()){
				groupLists = new GroupBean();
				groupLists.setGroupName(rs.getString(1));
				groupLists.setStatus(rs.getInt(2));
				groupLists.setId(rs.getInt(3));
				userGroupList.add(groupLists);
			}
		}catch(SQLException e){
			System.out.println(e);
		}
		return userGroupList;
	}

	@Override
	public UserBean signUp(String username, String password,
			String fname, String lname) {
		UserBean info = null;
		try{
			info = new UserBean();
			String[] query = {"CALL addUser('"+username+"','"+password+"','"+fname+"','"+lname+"');"};
			System.out.println(query[0]);
			database.getInstance().executeUpdateQuery(query);
			
				info.setfName(fname);
				info.setlName(lname);
				info.setFoto("sources/images/userpic.gif");
				info.setUsername(username);
		}catch(Exception e){
			System.out.println(e);
		}
		return info;
	}

}
