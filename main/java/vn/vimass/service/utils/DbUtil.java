package vn.vimass.service.utils;

import vn.vimass.service.log.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {
	public final static String URL = "jdbc:mysql://localhost:3306/dbdonvi?useUnicode=yes&characterEncoding=UTF-8";
	public final static String USER = "root";
	public final static String PASS = "admin";

	public static Connection getConnect() {
		try {
			Connection connection = null;
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USER, PASS);
			return connection;
		} catch (Exception e) {
			Log.logServices("DbUtil Exception" + e);
			return null;
		}
	}
}
