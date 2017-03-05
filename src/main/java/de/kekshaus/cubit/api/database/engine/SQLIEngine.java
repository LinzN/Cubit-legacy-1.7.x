package de.kekshaus.cubit.api.database.engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLIEngine {
	private boolean useSQL;
	private Connection connection;
	private String url;
	private String username;
	private String password;
	
	public SQLIEngine(String url, String username, String password, boolean useSQL){
		this.useSQL = useSQL;
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public boolean setupSQLI(String[] tableArray){
		for (String table : tableArray){
			runUpdateTask(table);
			System.out.println("SQLIEngine: " + table);
		}
		
		return true;
	}
	
	public boolean useSQL(){
		return this.useSQL;
	}
	
	
	private Connection refreshConnection(){
		try {
			if (this.connection == null || this.connection.isClosed()){
				if (this.useSQL){
					this.connection = DriverManager.getConnection(this.url, this.username, this.password);
				} else {
					this.connection = DriverManager.getConnection(this.url);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.connection;
	}
	
	public void runUpdateTask(String command){
		refreshConnection();
		try {
			Statement state = connection.createStatement();
			state.executeUpdate(command);
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet runQueryResult(String command){
		refreshConnection();
		try {

			Statement state = connection.createStatement();
			return state.executeQuery(command);
 
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
