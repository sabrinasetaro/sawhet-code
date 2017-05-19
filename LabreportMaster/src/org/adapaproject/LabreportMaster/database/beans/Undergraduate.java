/**
 * 
 */
package org.adapaproject.LabreportMaster.database.beans;

/**
 * @author setarosd
 *
 */
public class Undergraduate {
	
	private int _id;
	private String _student_email;
	private String _firstname;
	private String _lastname;
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String get_student_email() {
		return _student_email;
	}
	public void set_student_email(String _student_email) {
		this._student_email = _student_email;
	}
	public String get_firstname() {
		return _firstname;
	}
	public void set_firstname(String _firstname) {
		this._firstname = _firstname;
	}
	public String get_lastname() {
		return _lastname;
	}
	public void set_lastname(String _lastname) {
		this._lastname = _lastname;
	}

}
