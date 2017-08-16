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
import org.adapaproject.LabreportMaster.database.beans.Course;

/**
 * @author setarosd
 *
 */
public class CoursesManager {

	private static final String USERNAME = Database.getUsername();
	private static final String PASSWORD = Database.getPassword();
	private static final String CONN_STRING = Database.getConnString();
	
	public static int getCourseID(String courseName) throws SQLException {
		
		String sql = "SELECT course_id FROM courses WHERE name = ?";
		ResultSet rs = null;
		
		try (
				Connection connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql);
				) {
			statement.setString(1, courseName);
			rs = statement.executeQuery();
			
			if (rs.next()) {
				Course bean = new Course();
				bean.set_name(courseName);
				int course_id = rs.getInt("course_id");
				return course_id;
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
