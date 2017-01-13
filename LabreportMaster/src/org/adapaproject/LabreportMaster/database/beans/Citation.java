/**
 * 
 */
package org.adapaproject.LabreportMaster.database.beans;

/**
 * @author setarosd
 *
 */
public class Citation {

	private int _citations_id;
	private String _citations_value;
	private String _qualtrics_id;
	
	public int get_citations_id() {
		return _citations_id;
	}
	public void set_citations_id(int _citations_id) {
		this._citations_id = _citations_id;
	}
	public String get_citations_value() {
		return _citations_value;
	}
	public void set_citations_value(String _citations_value) {
		this._citations_value = _citations_value;
	}
	public String get_qualtrics_id() {
		return _qualtrics_id;
	}
	public void set_qualtrics_id(String _qualtrics_id) {
		this._qualtrics_id = _qualtrics_id;
	}
	
	
	
}
