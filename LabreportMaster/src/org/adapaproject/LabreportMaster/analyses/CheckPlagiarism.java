/**
 * 
 */
package org.adapaproject.LabreportMaster.analyses;

import static gate.Utils.stringFor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adapaproject.LabreportMaster.database.Database;
import org.adapaproject.LabreportMaster.database.tables.CitationsManager;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;


/**
 * @author setarosd
 *
 */
public class CheckPlagiarism {
	
	private AnnotationSet _annotCit;
	private HashMap<String, String> _citationsDBMap;
	private Database _db;
	private static ArrayList<String> _sanitizedCitations;
	
	public CheckPlagiarism() throws PersistenceException, ResourceInstantiationException, SQLException {
		
		if (_db == null) {
			_db = new Database();
		}
		
		//_sanitizedCitations = new ArrayList<String>();
	}
	
	
	public StringBuffer printResult(Document doc) throws SQLException {
		StringBuffer resultText = new StringBuffer();
		
		ArrayList<String> sameIds = check(doc);
		
		boolean preambleCheck = false;
		
		//create set to eliminate duplicates
		Set<String> fishySet = new HashSet<String>();
		
		//calculate how often id occurs in citations
		for(String id : sameIds) {
			int totalCit = sameIds.size();
			double idsFreq = Collections.frequency(sameIds, id);
			double fraction = 100 * (idsFreq / totalCit);
			if(idsFreq >= 2 && fraction >= 50) {
				fishySet.add(id);
				preambleCheck = true;
			}
		}
		
		if (preambleCheck == true) {
			resultText.append("\n");
			resultText.append("*************************************************************************\n\n");
			resultText.append("The following lab reports have more than 60% of citations in common with this report: " + fishySet);
			resultText.append("\n\nPlease check the respective lab report(s) for potential plagiarism.\n\n");
			resultText.append("*************************************************************************\n");
		} else {
			resultText.append("No significant similarities were found with other lab reports.");
		}
		
		return resultText;
		
	}
	
	
	private ArrayList<String> check(Document doc) throws SQLException {
		//get array of citations for this document
		ArrayList<String> citationArray = getCitationArray(doc);
		
		//get email
		String email = stringFor(doc, doc.getAnnotations("Original markups").get("EmailAddress"));
		//get all citation data
		_citationsDBMap = CitationsManager.getCitationList(email);
				
		ArrayList<String> idSame = new ArrayList<String>();
		
		//iterate over all citation data
		Iterator iterCitations = _citationsDBMap.entrySet().iterator();
		while(iterCitations.hasNext()) {
			Map.Entry pair = (Map.Entry)iterCitations.next();
			String value = (String) pair.getValue();
			if (citationArray.contains(value)) {
				idSame.add(pair.getKey().toString());
			} 
			
		}
		
		return idSame;
		
	}

	private ArrayList<String> getCitationArray(Document doc) {
		
		//citations for this lab report
		_annotCit = doc.getAnnotations().get("Citation");
		List<Annotation> citations = gate.Utils.inDocumentOrder(_annotCit);
		Set<String> citationSet = new HashSet<String>();
		
		_sanitizedCitations = new ArrayList<String>();

		for(Annotation citation : citations) {
			String citationString = stringFor(doc, citation);
			citationString = sanitizeCitation(citationString);
			citationSet.add(citationString);
		}
		
		//change set to array
		ArrayList<String> citationArray = new ArrayList<String>(citationSet);
		
		return citationArray;
	}

	private String sanitizeCitation(String citation) {
		citation = citation.replaceAll("[,;:\\[\\]\\.\\(\\)]", " ");
		citation = citation.replaceAll("[ ]+", " ");
		citation = citation.trim();
		//add this for database insert later
		_sanitizedCitations.add(citation);
		return citation;
	}


	public static ArrayList<String> get_sanitizedCitations() {
		return _sanitizedCitations;
	}
	

}
