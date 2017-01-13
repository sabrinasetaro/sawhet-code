/**
 * 
 */
package org.adapaproject.LabreportMaster.database.tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.adapaproject.LabreportMaster.database.Database;

/**
 * @author setarosd
 *
 */
public class StatisticsManager {
	
	private static final String USERNAME = Database.getUsername();
	private static final String PASSWORD = Database.getPassword();
	private static final String CONN_STRING = Database.getConnString();
	
	/**
	 * @throws SQLException 
	 * 
	 */
	public static ArrayList<Long> getData(String column) throws SQLException {
		
		ResultSet result = null;
		ArrayList<Long> list = null;
		try (
				Connection connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
				Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				) {						
			
			if (column.equals("biology")) {
				result = statement.executeQuery("SELECT biology FROM statistics");
			} else if (column.equals("citations")) {
				result = statement.executeQuery("SELECT citations FROM statistics");
			} else if (column.equals("sentences")) {
				result = statement.executeQuery("SELECT sentences FROM statistics");
			} else if (column.equals("token")) {
				result = statement.executeQuery("SELECT token FROM statistics");
			} else {
				result = statement.executeQuery("SELECT * FROM statistics");
			}
			
			list = get_list(result, column);
			
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (result != null) {
				result.close();
			}
		}
		
		return list;
	}
	
	private static ArrayList<Long> get_list(ResultSet result, String column) throws SQLException {
		
		ArrayList<Long> list = new ArrayList<Long>();
		while (result.next()) {
			list.add(result.getLong(column));
		}
		return list;
	}

}
