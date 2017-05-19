/**
 * 
 */
package org.adapaproject.LabreportMaster.database;

/**
 * @author setarosd
 *
 */
public class Database {
	
	private static final String USERNAME = "root";
	private static final String PASSWORD = "Malen2Zahlen587*";
	private static final String CONN_STRING = "jdbc:mysql://localhost/labreports2";


	public static String getUsername() {
		return USERNAME;
	}


	public static String getPassword() {
		return PASSWORD;
	}


	public static String getConnString() {
		return CONN_STRING;
	}


}
