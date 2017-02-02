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
import org.adapaproject.LabreportMaster.database.beans.Undergraduate;

/**
 * @author setarosd
 *
 */
public class UndergraduatesManager {

	private static final String USERNAME = Database.getUsername();
	private static final String PASSWORD = Database.getPassword();
	private static final String CONN_STRING = Database.getConnString();
	
	public static int getStudentId(String email) throws SQLException {
		
		String sql = "SELECT id FROM undergraduates WHERE student_email = ?";
		ResultSet rs = null;
		
		try (
				Connection connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql);
				) {
			statement.setString(1, email);
			rs = statement.executeQuery();
			
			if (rs.next()) {
				Undergraduate bean = new Undergraduate();
				bean.set_student_email(email);
				int student_id = rs.getInt("id");
				return student_id;
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
