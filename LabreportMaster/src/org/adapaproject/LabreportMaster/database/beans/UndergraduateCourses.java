/**
 * 
 */
package org.adapaproject.LabreportMaster.database.beans;

/**
 * @author setarosd
 *
 */
public class UndergraduateCourses {
	
	private int _id;
	private int _undergrad_id;
	private int _ta_id;
	private int _course_id;
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public int get_undergrad_id() {
		return _undergrad_id;
	}
	public void set_undergrad_id(int _undergrad_id) {
		this._undergrad_id = _undergrad_id;
	}
	public int get_ta_id() {
		return _ta_id;
	}
	public void set_ta_id(int _ta_id) {
		this._ta_id = _ta_id;
	}
	public int get_course_id() {
		return _course_id;
	}
	public void set_course_id(int _course_id) {
		this._course_id = _course_id;
	}

}
