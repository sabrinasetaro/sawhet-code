/**
 * 
 */
package org.adapaproject.LabreportMaster.database;

/**
 * @author setarosd
 *
 */
public class Database {
	
	//TODO for biolabs server
	private static final String USERNAME = "labreports";
	private static final String PASSWORD = "schreibGut#1999";
	private static final String CONN_STRING = "jdbc:mysql://localhost/labreport_info";
	//for semiramisia
/*	private static final String USERNAME = "root";
	private static final String PASSWORD = "Malen2Zahlen587*";
	private static final String CONN_STRING = "jdbc:mysql://localhost/labreport_info";*/


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
