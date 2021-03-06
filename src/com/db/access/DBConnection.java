package com.db.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	private final String driver = "com.hxtt.sql.access.AccessDriver";
	String path = "";
	private String url = "";
	private final String user = "";
    private final String password = "";
    private Connection conn = null;

    public DBConnection() {
    	init();
		try {
			Class.forName(driver);	//JDBC-ODBC�Ž���
			this.conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return conn;
	}

	private Statement createStatement() throws SQLException {
		return conn.createStatement();
	}

	private void init() {
		String path = this.getClass().getResource("/").getPath();
		this.path = path.substring(1, path.indexOf("classes"));
		this.url = "jdbc:Access:///" + this.path + "db/gxbdb.accdb";
		//System.out.println("���ݿ�url:" + this.url);
	}
	
	public void close() {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
