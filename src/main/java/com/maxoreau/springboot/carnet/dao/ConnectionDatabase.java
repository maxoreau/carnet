package com.maxoreau.springboot.carnet.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {
	
	private static ConnectionDatabase connectionDatabase;
	private Connection connection;
	
	private ConnectionDatabase() {
		String url = "jdbc:mysql://localhost/carnetscontacts";
		String user = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 			
	}

	public static ConnectionDatabase getConnectionDatabase() {
		if (connectionDatabase == null) {
			connectionDatabase = new ConnectionDatabase();			
		}
		return connectionDatabase;
	}

	public Connection getConnection() {
		if (connection == null) {
			String url = "jdbc:mysql://localhost/carnetscontacts";
			String user = "root";
			String password = "root";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				this.connection = DriverManager.getConnection(url, user, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} 			
		}
		return connection;
	}
	

}
