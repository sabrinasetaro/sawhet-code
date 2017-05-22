/**
 * 
 */
package org.adapaproject.LabreportMaster.database.tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.adapaproject.LabreportMaster.database.Database;
import org.adapaproject.LabreportMaster.database.beans.Citation;
import org.adapaproject.LabreportMaster.database.beans.Statistic;

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
	
	public static boolean insert(Statistic bean) throws SQLException {
		String sql = "INSERT into statistics (sentences, token, biology, citations, qualtrics_id) VALUES (?, ?, ?, ?, ?)";

		try (
				Connection connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql);
				) {
			
			statement.setInt(1, bean.get_sentences());
			statement.setInt(2, bean.get_token());
			statement.setInt(3,  bean.get_biology());
			statement.setInt(4, bean.get_citations());
			statement.setString(5,  bean.get_qualtrics_id());
			statement.executeUpdate();	
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	private static ArrayList<Long> get_list(ResultSet result, String column) throws SQLException {
		
		ArrayList<Long> list = new ArrayList<Long>();
		while (result.next()) {
			list.add(result.getLong(column));
		}
		return list;
	}

}
