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

import org.adapaproject.LabreportMaster.database.Database;
import org.adapaproject.LabreportMaster.database.beans.Labreport;

/**
 * @author setarosd
 *
 */
public class LabreportsManager {
	
	private static final String USERNAME = Database.getUsername();
	private static final String PASSWORD = Database.getPassword();
	private static final String CONN_STRING = Database.getConnString();
	
	/**
	 * 
	 */
	public static String getLastID() {
		
		String lastID = null;
		try (
				Connection connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
				Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet result = statement.executeQuery("SELECT qualtrics_id FROM labreports ORDER BY date asc");
				
				) {						
						
			lastID = getLastLabreport(result);
			
		} catch (SQLException e) {
			System.err.println(e);
		} 
		
		return lastID;
	}
	
	private static String getLastLabreport(ResultSet result) throws SQLException {
		
		result.last();
		String last = result.getString("qualtrics_id");
		return last;
		
	}
	
	public static boolean insert(Labreport bean) throws SQLException {
		String sql = "INSERT into labreports (qualtrics_id, datastore_id, number, date, course_id, ta_id, undergrad_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (
				Connection connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql);
				) {
			
			statement.setString(1, bean.get_qualtrics_id());
			statement.setString(2, bean.get_datastore_id());
			statement.setString(3, bean.get_number());
			statement.setString(4, bean.get_date());
			statement.setInt(5, bean.get_course_id());
			statement.setInt(6, bean.get_ta_id());
			statement.setInt(7, bean.get_undergrad_id());
			statement.executeUpdate();	
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}

}
