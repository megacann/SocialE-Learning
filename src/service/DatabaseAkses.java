package service;

import service.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseAkses {
	
	private Connection con = null;
	private boolean conIsFree = true;
	private static DatabaseAkses instance = null;

	public static DatabaseAkses getInstance(){
		if (instance == null) {
			try {
				instance = new DatabaseAkses();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return instance;
	}
	
	private DatabaseAkses() throws Exception{
		try{
			//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
			//Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
			//Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			Class.forName("com.mysql.jdbc.Driver").newInstance();;
		}catch (Exception e){
				System.out.println("exc 2");
				System.out.println(e);
				throw new Exception();
		}
		try{
			connect();
		}catch(SQLException e ){
			System.out.println("exc 1!");
			throw new Exception();
		}
	}
	
	private synchronized Connection getConnection () {
		while(!conIsFree) {
			try{
				wait();
			}catch(InterruptedException e){}
		}
		conIsFree = false;
		notify();
		return con;
	}
	
	private synchronized void releaseConnection() {
		while(conIsFree){
			try{
				wait();
			}catch(InterruptedException e){}
		}
		conIsFree = true;
		notify();
	}
	
	public void connect() throws SQLException{
		Configuration config = new Configuration();
		String configSource[]=config.getConfig().split(";");
		final String username=configSource[0];
		final String password=configSource[1];
		final String ipAddress=configSource[2];
		final String port=configSource[3];
		final String databaseName=configSource[4];
		final String databaseurl="jdbc:mysql://"+ipAddress+":"+port+"/"+databaseName;
		//System.out.println(username+" - "+password+" - "+databaseurl);
		con=DriverManager.getConnection(databaseurl,username,password);
	}
	
	public void terminateConnection(){
		try{
			if(con!=null) con.close();
		}catch(SQLException e){}
	}
	
	public boolean executeUpdateQuery(String[] sqls){
		boolean returnValue = true;
		getConnection();
		boolean oldCommitMode = false;
		boolean commitModeChecked = false;
		try{
			oldCommitMode = con.getAutoCommit();
			commitModeChecked = true;
			con.setAutoCommit(false);
			Statement statment = con.createStatement();
			for(int i=0; i<sqls.length ;i++)
				statment.addBatch(sqls[i]);
			
				int[] batchState = statment.executeBatch();
				
				boolean batchSuccessful = true;
			for(int i=0; i<batchState.length;i++)
				if(batchState[i]==statment.EXECUTE_FAILED){
					batchSuccessful = false;
				}
			
			statment.close();
			
			if(!batchSuccessful){
				returnValue = false;
				con.rollback();
			}else {
				con.commit();
			}
			System.out.println("akhir");
		}catch (Exception e ){
			System.out.println("gagal input");
			e.printStackTrace();
			returnValue = false;
		}finally{
			if(commitModeChecked){
				try{
					con.setAutoCommit(oldCommitMode);					
				}catch(SQLException e){}
			}
			releaseConnection();
		}
		
		return returnValue;
	}
	
	public ResultSet executeSelectQuery(String query) throws SQLException{
		getConnection();
		boolean ok = true;
		ResultSet rs = null;
		try{
			//boolean oldCommitMode = con.getAutoCommit();
			//con.setAutoCommit(false);
			//System.out.println(query);
			Statement statement = con.createStatement();
			rs = statement.executeQuery(query);
			con.setAutoCommit(false);
			con.commit();
		}catch(Exception e){
			e.printStackTrace();
			ok = false;
		}finally{
			releaseConnection();
		}
		if(!ok){
			throw new SQLException();
		}
		return rs;
	}
	
}