package service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class Configuration {
	private String config;
	public String getConfig(){
		String sCurrentLine = "0";
		BufferedInputStream bis = null;
		DataInputStream dis = null;
		InputStream is = Configuration.class.getResourceAsStream("config");
		bis = new BufferedInputStream(is);
		dis = new DataInputStream(bis);
		try {
			while (dis.available() != 0) {
				this.config=dis.readLine();
			}
		} catch (IOException e) {
			System.out.println("pembacaan file konfigurasi gagal");
		}
		return this.config;
	}
}