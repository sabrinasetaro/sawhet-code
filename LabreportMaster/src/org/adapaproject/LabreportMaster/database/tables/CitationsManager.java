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
import java.util.HashMap;

import org.adapaproject.LabreportMaster.database.Database;
import org.adapaproject.LabreportMaster.database.beans.Citation;

/**
 * @author setarosd
 *
 */
public class CitationsManager {
	
		private static final String USERNAME = Database.getUsername();
		private static final String PASSWORD = Database.getPassword();
		private static final String CONN_STRING = Database.getConnString();
		
		public static Citation getRow(int citations_id) throws SQLException {
			
			String sql = "SELECT * FROM citations WHERE citations_id = ?";
			ResultSet rs = null;
			
			try (
					Connection connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
					PreparedStatement statement = connection.prepareStatement(sql);
					) {
				statement.setInt(1, citations_id);
				rs = statement.executeQuery();
				
				if (rs.next()) {
					Citation bean = new Citation();
					bean.set_citations_id(citations_id);
					bean.set_citations_value(rs.getString("citations_value"));
					bean.set_qualtrics_id(rs.getString("qualtrics_id"));
					return bean;
				} else {
					return null;
				}
								
			} catch (SQLException e) {
				System.err.println(e);
				return null;
			} finally {
				if (rs != null) {
					rs.close();
				}
			}
			
		}

		public static boolean insert(Citation bean) throws SQLException {
			String sql = "INSERT into citations (citations_value, qualtrics_id) VALUES (?, ?)";

			try (
					Connection connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
					PreparedStatement statement = connection.prepareStatement(sql);
					) {
				
				statement.setString(1, bean.get_citations_value());
				statement.setString(2, bean.get_qualtrics_id());
				statement.executeUpdate();				
				
			} catch (SQLException e) {
				System.err.println(e);
				return false;
			}
			return true;
		}
		
		public static HashMap<String, String> getCitationList(String email) throws SQLException {
			ResultSet result = null;
			HashMap<String, String> map = null;
			try (
					Connection connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
					Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					) {						
				
				result = statement.executeQuery(
						
						"Select ug.student_email, c.qualtrics_id, c.citations_value FROM undergraduates as ug JOIN labreports as lr on lr.undergrad_id = ug.id JOIN citations as c on c.qualtrics_id = lr.qualtrics_id where ug.student_email != 'setarosd@wfu.edu' and ug.student_email != 'sabrina.setaro@gmail.com' and c.citations_value not like 'Johnson 2015' and c.citations_value not like 'Johnson 2016'"
						
						);
				
				map = get_list(result, email);
				
			} catch (SQLException e) {
				System.err.println(e);
			} finally {
				if (result != null) {
					result.close();
				}
			}
			
			return map;
		}
		
		private static HashMap<String, String> get_list(ResultSet result, String email) throws SQLException {
			
			HashMap<String, String> map = new HashMap<String, String>();
			while (result.next()) {
				//this avoids that earlier submissions of this student appear in list
				if (result.getString("student_email").equals(email)) {
				} else {
					map.put(result.getString("qualtrics_id"), result.getString("citations_value"));
				}
			}
			return map;
		}
}
