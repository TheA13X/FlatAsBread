package saul;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLMethod {
	
	static Connection con;
	
	static String	location	= "localhost";
	static String	user		= "samu";
	static String	base		= "samu_saul";
	static String	pass		= "rootpassword123";
	
	public static Connection open() {
		
		try {
                        MySQLMethod.class.getClassLoader().loadClass("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://" + location + "/" + base, user, pass);
//			con = DriverManager.getConnection("jdbc:mysql://localhost/root", "root", "rootpasswort123");
		} catch (SQLException e1) {
			System.out.print("[MySQL] Failed to connect!");
		} catch (ClassNotFoundException ex) {
                        System.out.print("[MySQL] Driver could not be loaded!");
                }
		
		return con;
	}
	
	public static void close() {
		try {
			con.close();
		} catch (SQLException e1) {
			System.out.print("[MySQL] Failed to close connection!");
		}
	}
	
	public static void update(String query) {
		try {
			Statement st = con.createStatement();
			st.executeUpdate(query);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void create(String query) {
		try {
			Statement st = con.createStatement();
			st.execute(query);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void insert(String query) {
		try {
			Statement st = con.createStatement();
			st.execute(query);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet select(String query) {
		ResultSet rs = null;
		try {
			Statement st = con.createStatement();
			rs = st.executeQuery(query);
//	    	st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static void create() {
		MySQLMethod.create("CREATE TABLE IF NOT EXISTS server (port int(5), ram int(1), typ varchar(50), online tinyint(1), player int(3));");
//		MySQLMethod.create("CREATE TABLE IF NOT EXISTS game (typ varchar(50), isFree tinyint(1), isStarting tinyint(1));");
		MySQLMethod.create("CREATE TABLE IF NOT EXISTS warn (id int(5), typ varchar(10), text varchar(255));");
		MySQLMethod.create("CREATE TABLE IF NOT EXISTS typ (typ varchar(255), plugins varchar(255), start int(1));");
		MySQLMethod.create("CREATE TABLE IF NOT EXISTS command (port int(5), command varchar(255));");
		MySQLMethod.create("CREATE TABLE IF NOT EXISTS player (uuid varchar(50), name varchar(16), lang varchar(3), ip varchar(255), rank varchar(255), server varchar(255))");
		MySQLMethod.create("CREATE TABLE IF NOT EXISTS rank (name varchar(50), chat_prefix varchar(255), tab_prefix varchar(255), power varchar(255))");
		MySQLMethod.create("CREATE TABLE IF NOT EXISTS map (name varchar(255), spawn varchar(255), edit tinyint(1), version varchar(10));");
		MySQLMethod.create("CREATE TABLE IF NOT EXISTS game (game varchar(255), maps varchar(255), start int(1));");
		MySQLMethod.create("CREATE TABLE IF NOT EXISTS lang_DE (id varchar(255), message varchar(255));");
		MySQLMethod.create("CREATE TABLE IF NOT EXISTS lang_EN (id varchar(255), message varchar(255));");
		MySQLMethod.create("CREATE TABLE IF NOT EXISTS flatsector (id INT( 11 ) NOT NULL PRIMARY KEY AUTO_INCREMENT , name varchar(255), owner varchar(255), secX int(11), secY int(11), biome varchar(255), generated tinyint(1));");
//		TODO: move to ServerLauncher
	}
	
	public static void st_close() {
		Statement st;
		try {
			st = con.createStatement();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String getLocation() {
		return location;
	}
	
	public static String getBase() {
		return base;
	}
	
	public static String getUser() {
		return user;
	}
	
	public static String getPasswort() {
		return pass;
	}
}
