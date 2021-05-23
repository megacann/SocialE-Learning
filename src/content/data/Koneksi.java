package content.data;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Koneksi {
	private static Connection mysqlconnect;
	public static Connection koneksiDB() throws SQLException{
		if (mysqlconnect==null) {
			try {
				String DB="jdbc:mysql://localhost:3306/socel";
				String user="root";
				String pass="";
				DriverManager.registerDriver(new com.mysql.jdbc.Driver());
				mysqlconnect = (Connection) DriverManager.getConnection(DB,user,pass);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,"koneksi gagal");
			}
		}
		return mysqlconnect;
		
	}

}
