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
	//private Corpus _all;
	private HashMap<String, String> _citationsDBMap;
	private Database _db;
	private static ArrayList<String> _sanitizedCitations;
	
	public CheckPlagiarism() throws PersistenceException, ResourceInstantiationException, SQLException {
		
		if (_db == null) {
			_db = new Database();
		}
		
		_sanitizedCitations = new ArrayList<String>();
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
			//System.out.println("totalCit: " + totalCit);
			double idsFreq = Collections.frequency(sameIds, id);
			//System.out.println("idsFreq: " + idsFreq);
			double fraction = 100 * (idsFreq / totalCit);
			//System.out.println("fraction: " + fraction);
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
	
/*	*//**
	 * This is just for testing, to be deleted later
	 *//*
	private ArrayList<String> testList() {
		ArrayList<String> test = new ArrayList<String>();
		test.add("R_xz40hVjjuuhxz69");
		test.add("R_26uvrt41GFcIlNj");
		test.add("R_xz40hVjjuuhxz69");
		test.add("R_1Qi6ue4i2UU7I5d");
		test.add("R_xz40hVjjuuhxz69");
		test.add("R_xz40hVjjuuhxz69");
		test.add("R_88GME1VBdJYhOpz");
		test.add("R_xz40hVjjuuhxz69");
		test.add("R_AbKMHsD5s8QRPih");
		test.add("R_xz40hVjjuuhxz69");
		test.add("R_3aWZe420RdRUUPb");
		
		return test;
	}*/
	
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
		
		//System.out.println(_plagResult);
		return idSame;
		
	}

	private ArrayList<String> getCitationArray(Document doc) {
		
		//citations for this lab report
		_annotCit = doc.getAnnotations().get("Citation");
		List<Annotation> citations = gate.Utils.inDocumentOrder(_annotCit);
		Set<String> citationSet = new HashSet<String>();

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
	
/*	private void getCorpusAll() throws PersistenceException, ResourceInstantiationException {
		RunGate gate = new RunGate();
		gate.runAll();
		
		_all = gate.get_corpusAll();
	}*/
	
	/*	*//**
	 * Method to get all citations and put it in a format that can be used for mysql import
	 * Method is commented out, because it is no more needen in here
	 * Consider making a separate class for it, if you want to keep the code.
	 * @author setarosd
	 *//*
	private void writeAllCitations() {
		for (int i = 0; i < _all.size(); i++) {
			Document refDoc = _all.get(i);
			AnnotationSet annotRef = refDoc.getAnnotations().get("Citation");

			
			//create array for citation strings
			Set<String> refList = new HashSet();
			
			List<Annotation> references = gate.Utils.inDocumentOrder(annotRef);
			for(Annotation reference : references) {
				//print citations
				//System.out.println(documentID + ": " + gate.Utils.stringFor(each, ann));
				String refCitation = stringFor(refDoc, reference);
				
				refCitation = sanitizeCitation(refCitation);
				
				refList.add(refCitation);
				
			}
			
			Iterator iter = refList.iterator();
			while (iter.hasNext()) {
				Object object = (Object) iter.next();
				System.out.println(object + "@" + stringFor(refDoc, refDoc.getAnnotations("Original markups").get("ResponseID")));
			}
			
		}
	}*/
	
	/*	*//**
	 * This was code to get all citations for mysql import.
	 * Not needed here anymore
	 * @author setarosd
	 * @param doc
	 * @throws IOException
	 *//*
	public void plagcheckFile(Document doc) throws IOException {
		OutputStream outputstream = null;
		PrintStream printstream = null;
		
		try {
			try {
				outputstream = new FileOutputStream(System.getProperty("user.dir") + "/" + "citations.txt");
			} catch (FileNotFoundException e) {
				System.out.println("Error while creating output file.");
				e.printStackTrace();
			}
			printstream = new PrintStream(outputstream);
			System.setOut(printstream);
			

			check(doc);

			
		} finally {
			printstream.close();
			outputstream.close();
			System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		}
	}*/


}
