/**
 * 
 */
package org.adapaproject.LabreportMaster.database.beans;

/**
 * @author setarosd
 *
 */
public class Statistic {

	private int _statistics_id;
	private int _sentences;
	private int _token;
	private int _biology;
	private int _citations;
	private String _qualtrics_id;
	
	public int get_statistics_id() {
		return _statistics_id;
	}
	public void set_statistics_id(int _statistics_id) {
		this._statistics_id = _statistics_id;
	}
	public int get_sentences() {
		return _sentences;
	}
	public void set_sentences(int _sentences) {
		this._sentences = _sentences;
	}
	public int get_token() {
		return _token;
	}
	public void set_token(int _token) {
		this._token = _token;
	}
	public int get_biology() {
		return _biology;
	}
	public void set_biology(int _biology) {
		this._biology = _biology;
	}
	public int get_citations() {
		return _citations;
	}
	public void set_citations(int _citations) {
		this._citations = _citations;
	}
	public String get_qualtrics_id() {
		return _qualtrics_id;
	}
	public void set_qualtrics_id(String _qualtrics_id) {
		this._qualtrics_id = _qualtrics_id;
	}
	
}
