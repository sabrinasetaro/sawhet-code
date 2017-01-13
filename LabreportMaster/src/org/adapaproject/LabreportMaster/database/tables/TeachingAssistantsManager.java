/**
 * 
 */
package org.adapaproject.LabreportMaster.database.tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.adapaproject.LabreportMaster.database.Database;
import org.adapaproject.LabreportMaster.database.beans.TeachingAssistant;

/**
 * @author setarosd
 *
 */
public class TeachingAssistantsManager {
	
	private static final String USERNAME = Database.getUsername();
	private static final String PASSWORD = Database.getPassword();
	private static final String CONN_STRING = Database.getConnString();
	
	public static int getTaId(String taEmail) throws SQLException {
		
		String sql = "SELECT ta_id FROM teaching_assistants WHERE wfu_email = ?";
		ResultSet rs = null;
		
		try (
				Connection connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql);
				) {
			statement.setString(1, taEmail);
			rs = statement.executeQuery();
			
			if (rs.next()) {
				TeachingAssistant bean = new TeachingAssistant();
				bean.set_wfu_email(taEmail);
				int ta_id = rs.getInt("ta_id");
				return ta_id;
			} else {
				return (Integer) null;
			}
							
		} catch (SQLException e) {
			System.err.println(e);
			return (Integer) null;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		
	}

}
