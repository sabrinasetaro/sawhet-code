/**
 * 
 */
package org.adapaproject.LabreportMaster.analyses;

import java.sql.SQLException;
import java.util.ArrayList;

import org.adapaproject.LabreportMaster.database.tables.StatisticsManager;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import gate.Document;

/**
 * @author setarosd
 *
 */
public class StatAnalyses {
	
	private Long [] _sentList;
	private Long [] _bioList;
	private Long [] _citList;
	private Long [] _tokenList;
	private static String _sentResult;
	private static String _bioResult;
	private static String _citResult;
	private static String _tokenResult;
	private static int _dataSize;
	
	public void initiate() throws SQLException {
		_sentList = toList(StatisticsManager.getData("sentences"));
		_bioList = toList(StatisticsManager.getData("biology"));
		_citList = toList(StatisticsManager.getData("citations"));
		_tokenList = toList(StatisticsManager.getData("token"));
	}
	
	public void comparison(Document doc) {
		comparisonHelper(doc, "Biology", _bioList);
		comparisonHelper(doc, "Citation", _citList);
		comparisonHelper(doc, "Sentence", _sentList);
		comparisonHelper(doc, "Token", _tokenList);
				
	}

	private String comparisonHelper(Document doc, String annotation, Long [] list) {
		int datatype = doc.getAnnotations().get(annotation).size();
		
		_dataSize = list.length;
		
		String result = "Something went wrong with the analysis, contact emailaddress";
		
		if (datatype < lowerPercentile(list)) {
			result = "falls below lower percentile :(";
		} else if (datatype > upperPercentile(list)) {
			result = "falls above upper percentile :)";
		} else if (datatype <= upperPercentile(list)) {
			result = "falls in upper percentile :)";
		} else if (datatype >= lowerPercentile(list)) {
			result = "falls in lower percentile :(";
		}
		
		if (annotation.equals("Biology")) {
			_bioResult = result;
		} else if (annotation.equals("Citation")) {
			_citResult = result;
		} else if (annotation.equals("Sentence")) {
			_sentResult = result;
		} else if (annotation.equals("Token")) {
			_tokenResult = result;
		}
		
		return result;
	}
	
	private double upperPercentile(Long [] list) {
		DescriptiveStatistics stats = new DescriptiveStatistics();
		
		for (int i = 0; i < list.length; i++) {
			stats.addValue(list[i]);
		}
		
		double upper = stats.getPercentile(75);
		return upper;
		
	}
	
	private double lowerPercentile(Long [] list) {
		DescriptiveStatistics stats = new DescriptiveStatistics();
		
		for (int i = 0; i < list.length; i++) {
			stats.addValue(list[i]);
		}
		
		double lower = stats.getPercentile(25);
		return lower;
		
	}
	
	private Long [] toList(ArrayList<Long> array) {
		
		Long [] list = array.toArray(new Long[array.size()]);
		return list;
	}

	public static String get_sentResult() {
		return _sentResult;
	}

	public static String get_bioResult() {
		return _bioResult;
	}

	public static String get_citResult() {
		return _citResult;
	}

	public static String get_tokenResult() {
		return _tokenResult;
	}
	
	public static int get_dataSize() {
		return _dataSize;
	}

}
