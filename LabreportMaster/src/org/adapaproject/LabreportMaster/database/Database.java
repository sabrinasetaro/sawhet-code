/**
 * 
 */
package org.adapaproject.LabreportMaster.database;

/**
 * @author setarosd
 *
 */
public class Database {
	
	private static final String USERNAME = "labreports";
	private static final String PASSWORD = "schreibGut#1999";
	private static final String CONN_STRING = "jdbc:mysql://localhost/labreport_info";


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
