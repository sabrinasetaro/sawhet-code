/**
 * 
 */
package org.adapaproject.LabreportMaster.analyses;

import java.util.ArrayList;
import java.util.List;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;

/**
 * @author setarosd
 *
 */
public class RunReadability {
	
	private Long _abstract;
	private Long _introduction;
	private Long _methods;
	private Long _results;
	private Long _discussion;
	
	public RunReadability(Document doc) {
		
		String[] paragraphs = {"QID34", "QID2", "QID3", "QID4", "QID5"};
    	for (int i = 0; i < paragraphs.length; i++) {
    		//String[] helper = {"abstract", "introduction", "materials and methods", "results", "discussion"};
			String part = paragraphs[i];
			Long gl = getGradeLevelParagraph(doc, part);
			//String result = gl;
			
			if (i == 0) {
				_abstract = gl;
			} else if (i == 1) {
				_introduction = gl;
			} else if (i == 2) {
				_methods = gl;
			} else if (i == 3) {
				_results = gl;
			} else if (i == 4) {
				_discussion = gl;
			} 
		}
	}
	
	/**
	 * Calculate Bormuth Readability indices for all parts of lab report
	 * Round the value
	 *
	 * @param doc
	 * @param part
	 * @return
	 * @author setarosd
	 */
	private long getGradeLevelParagraph(Document doc, String part) {
		double sentences = getAnnotParagraph(doc, part, "Sentence").size();
    	double simplewords = getAnnotParagraph(doc, part, "simpleword").size();
    	double tokens = getAnnotParagraph(doc, part, "Token").size();
    	double allCharacters = getAllCharacters(doc, part);
    	double awl = allCharacters / tokens;
    	double afw = simplewords / tokens;
    	double asl = tokens / sentences;
    	//formula based on: http://www.readabilityformulas.com/the-bormuth-readability-formula.php
    	double gradeLevel = 0.886593 - (awl * 0.03640) + (afw + 0.161911) + (asl * 0.21401) - (asl * 0.000577) - (asl * 0.000005);
    	    	
    	return Math.round(gradeLevel);
	}

	/**
	 * Get all characters from document
	 * 
	 * @param doc
	 * @param part
	 * @return
	 * @author setarosd
	 */
	private int getAllCharacters(Document doc, String part) {
		int characterSum = 0;
    	for (int i = 0; i < getAnnotParagraph(doc, part, "Token").size(); i++) {
    		Annotation tokenX = getAnnotParagraph(doc, part, "Token").get(i);
    		String tokenStringX = gate.Utils.stringFor(doc, tokenX);			
			characterSum = characterSum + tokenStringX.length();		
		}
    	return characterSum;
	}
	
	/**
	 * Get all instances of a particular annotation in a particular paragraph.
	 * 
	 * @param doc
	 * @param part
	 * @param annotation
	 * @return
	 * @author setarosd
	 */
	private List<Annotation> getAnnotParagraph(Document doc, String part, String annotation) {
		AnnotationSet abs = doc.getAnnotations("Original markups").get(part);
    	AnnotationSet sentences = doc.getAnnotations().get(annotation);
    	Long start = gate.Utils.start(abs);
    	Long end = gate.Utils.end(abs);
    	AnnotationSet overlapping = sentences.get(start, end).get(annotation);
    	List<Annotation> overlappingList = new ArrayList<Annotation>(gate.Utils.inDocumentOrder(overlapping));
    	return overlappingList;
	}

	public Long get_abstract() {
		return _abstract;
	}

	public Long get_introduction() {
		return _introduction;
	}

	public Long get_methods() {
		return _methods;
	}

	public Long get_results() {
		return _results;
	}

	public Long get_discussion() {
		return _discussion;
	}
}
