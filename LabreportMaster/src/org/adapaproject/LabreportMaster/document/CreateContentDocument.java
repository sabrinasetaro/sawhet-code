/**
 * 
 */
package org.adapaproject.LabreportMaster.document;

import static gate.Utils.stringFor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.FeatureMap;

/**
 * @author setarosd
 *
 */
public class CreateContentDocument {
	private static Document _doc;
	private static AnnotationSet _original;
	@SuppressWarnings("unused")
	private static AnnotationSet _default;
	private static String _course;
	private static String _tA;
	//needed for database
	private static String _taEmail;
	private static String _id;
	private static String _email;
	private static String _name;
	private static String _date;
	private static String _number;
	private ArrayList<String> _sections;

	public CreateContentDocument(Document doc) throws IOException, InvalidFormatException {
		
		//private static Logger log = Logger.getLogger(Main.class);
		Logger log = Logger.getLogger("LabreportMaster");
		
		_doc = doc;
		_original = _doc.getAnnotations("Original markups");
		_default = _doc.getAnnotations();
		_id = stringFor(_doc, _original.get("ResponseID"));
		try {
			_course = this.checkCourse();
		} catch (Exception e) {
			log.log(Level.SEVERE, "Error with course information for " + _id + ".", e);
		}
		try {
			_tA = this.checkTA();
		} catch (Exception e) {
			log.log(Level.SEVERE, "Error with TA information for " + _id + ".", e);
		}
		_email = stringFor(_doc, _original.get("EmailAddress"));
		_name = stringFor(_doc, _original.get("Name"));
		_date = stringFor(_doc, _original.get("EndDate"));
		_number = (String) stringFor(_doc, _original.get("QID17"));
		
		_sections = new ArrayList<String>();
		_sections.add("Title");
		_sections.add("Abstract");
		_sections.add("Intro");
		_sections.add("MM");
		_sections.add("Results");
		_sections.add("Discussion");
		_sections.add("Literature");
	
	}
	
	public void getInfoDatabase() {
		_id = stringFor(_doc, _original.get("ResponseID"));
		_email = stringFor(_doc, _original.get("EmailAddress"));
		_name = stringFor(_doc, _original.get("Name"));
		_date = stringFor(_doc, _original.get("EndDate"));
		_number = (String) stringFor(_doc, _original.get("QID17"));
	}
	
	public void printLabreport() throws IOException, InvalidFormatException {
		
		StringBuffer text = new StringBuffer();
		
		//create a word document
		Word worddoc = new Word();
		//create image
		ImageFromURL image = new ImageFromURL();
		
		text.append("*************************************************************************\n");
		
		if (_number.equals("First")) {
			text.append("Number of lab report in semester: first\n");
			
			//add text to worddoc
			worddoc.addContentWORD("Number of lab report in semester: first", false);

			
		} else if (_number.equals("Second")) {
			text.append("Number of lab report in semester: second\n");
			
			//add text to worddoc
			worddoc.addContentWORD("Number of lab report in semester: second", false);

		}
		
		try {
			String type = stringFor(_doc, _original.get("QID90"));
			if (type.equals("submission")) {
				text.append("This is a submission.\n");
				
				//add text to worddoc
				worddoc.addContentWORD("This is a submission.\n", false);
				
			} else if (type.equals("revision")) {
				text.append("This is a revision of a previous lab report.\n");
				
				//add text to worddoc
				worddoc.addContentWORD("This is a revision.\n", false);
			}
		} catch (Exception e) {
			//do nothing
		}
		
		text.append("Date submitted: ");
		text.append(_date);
		text.append("\n");
		
		//add text to worddoc
		worddoc.addContentWORD("Date submitted: " + stringFor(_doc, _original.get("EndDate")), false);
		
		//Author
		
		text.append("Author of this lab report is: ");
		text.append(stringFor(_doc, _original.get("QID16_1_TEXT")));
		text.append(" ");
		text.append(stringFor(_doc, _original.get("QID16_2_TEXT")));
		text.append("\n");
		
		//add text to worddoc
		worddoc.addContentWORD("Author of this lab report is: " + stringFor(_doc, _original.get("QID16_1_TEXT")) + " " + stringFor(_doc, _original.get("QID16_2_TEXT")), false);
				
		//ID
		text.append("ID: ");
		text.append(_id);
		text.append("\n");
		
		//add text to worddoc
		worddoc.addContentWORD("ID: " + _id, false);
		
		//add course
		text.append("Course number: ");
		text.append(_course);
		text.append("\n");
		
		//add text to worddoc
		worddoc.addContentWORD("Course number: " + _course, false);
		
		//add TA
		text.append("TA: ");
		text.append(_tA);
		text.append("\n");
		
		worddoc.addContentWORD("TA: " + _tA, false);
				
		text.append("*************************************************************************");

		//Title
		text.append("\nTitle: ");
		text.append(stringFor(_doc, _original.get("QID1")));
		text.append("\n");
		
		worddoc.addContentWORD("\n", false);
		worddoc.addContentWORD(stringFor(_doc, _original.get("QID1")), true);
		worddoc.addContentWORD("\n", false);
		
		//Abstract
		text.append("\nAbstract:\n");
		text.append(stringFor(_doc, _original.get("QID34")));
		text.append("\n");
		
		worddoc.addContentWORD("Abstract", false);
		worddoc.addContentWORD(stringFor(_doc, _original.get("QID34")), false);
		worddoc.addContentWORD("\n", false);
		
		//Introduction
		text.append("\nIntroduction:\n");
		text.append(stringFor(_doc, _original.get("QID2")));
		text.append("\n");
		
		worddoc.addContentWORD("Introduction", false);
		worddoc.addContentWORD(stringFor(_doc, _original.get("QID2")), false);
		worddoc.addContentWORD("\n", false);
		
		//Materials and Methods
		text.append("\nMaterials and Methods:\n");
		text.append(stringFor(_doc, _original.get("QID3")));
		text.append("\n");
		
		worddoc.addContentWORD("Materials and Methods", false);
		worddoc.addContentWORD(stringFor(_doc, _original.get("QID3")), false);
		worddoc.addContentWORD("\n", false);
		
		//Results
		text.append("\nResults:\n");
		text.append(stringFor(_doc, _original.get("QID4")));
		text.append("\n");
		
		worddoc.addContentWORD("Results", false);
		worddoc.addContentWORD(stringFor(_doc, _original.get("QID4")), false);
		worddoc.addContentWORD("\n", false);
		
		//Discussion
		text.append("\nDiscussion:\n");
		text.append(stringFor(_doc, _original.get("QID5")));
		text.append("\n");
		
		worddoc.addContentWORD("Discussion", false);
		worddoc.addContentWORD(stringFor(_doc, _original.get("QID5")), false);
		worddoc.addContentWORD("\n", false);
		
		//Literature cited
		text.append("\nLiterature Cited:\n");
		text.append(stringFor(_doc, _original.get("QID6")));
		text.append("\n");
		
		worddoc.addContentWORD("Literature Cited", false);
		worddoc.addContentWORD(stringFor(_doc, _original.get("QID6")), false);
		worddoc.addContentWORD("\n", false);
		
		//create array of tables and graphs
		String[] figureList = {"QID85__1", "QID85__2", "QID85__3", "QID85__4", "QID85__5", "QID85__6" ,"QID85__7", "QID85__8", "QID85__9", "QID85__10", "QID85__11", "QID85__12", "QID85__13", "QID85__14", "QID85__15", "QID85__16", "QID85__17", "QID85__18" ,"QID85__19", "QID85__20"};

		
		//loop over figures and print link
		for (int i = 0; i < figureList.length; i++) {
			int figureNo = i + 1;
			
			//if it is not empty:
			if (!stringFor(_doc, _original.get(figureList[i])).isEmpty()){
				text.append("\nFigure ");
				text.append(figureNo + ": ");
				text.append(stringFor(_doc, _original.get(figureList[i])));
				text.append("\n");

				image.getImage(stringFor(_doc, _original.get(figureList[i])));
				
				//add header
				worddoc.addContentWORD("Figure: " + figureNo, false);
				//add image
				worddoc.importImage();
				
			//if figure is empty:
			} else {
				text.append("\nFigure ");
				text.append(figureNo);
				text.append(": no files uploaded\n");
			}
		}
		
		
		//Figure Legends
		text.append(getWordNo("QID8", "\nFigure Legends: \n"));
		
		worddoc.addContentWORD(getWordNo("QID8", "Figure Legends: "), false);
		worddoc.addContentWORD("\n", false);
		
		//Test if help was requested
		//help questions
		String[] helpquestions = {"QID29", "QID35", "QID37", "QID42", "QID39", "QID51", "QID47", "QID41", "QID93", "title", "abstract", "introduction", "materials and methods", "results", "legends", "figures", "discussion", "formatting"};
		ArrayList<String> info = new ArrayList<String>();
		//loop to get information and print the right info in place
		for (int i = 0; i < 9; i++) {
			if(stringFor(_doc, _original.get(helpquestions[i])).isEmpty()) {
				//do nothing
			} else {
				int topic = i+9;
				info.add(helpquestions[topic]);
			};
		}
		//this is just for cosmetics, so that the list has no square brackets around.
		StringBuilder builder = new StringBuilder();
		for (String value : info) {
		    builder.append(value + ", ");
		}
		String stringText = builder.toString();
		//clear trailing comma
		stringText = stringText.replaceAll(", $", "");
		
/*		text.append("\n\n*************************************************************************\n");
		//check if help was asked for
		if (info.isEmpty()) {
			text.append("Other Information:\n");
			text.append(stringFor(_doc, _original.get("QID16_1_TEXT")));
			text.append(" did not use help options.\n");
			
			worddoc.addContentWORD("Other Information", false);
			worddoc.addContentWORD(stringFor(_doc, _original.get("QID16_1_TEXT")) + " did not use help options.", false);
			
		} else {
			text.append("Other Information:\n");
			text.append(stringFor(_doc, _original.get("QID16_1_TEXT")));
			text.append(" used the help option for info on how to write:\n--> ");
			text.append(stringText);
			text.append("\n");
		
			worddoc.addContentWORD("Other Information", false);
			worddoc.addContentWORD(stringFor(_doc, _original.get("QID16_1_TEXT")) + " used the help option for info on how to write: --> " + stringText, false);
		}*/
		text.append("*************************************************************************\n");
		
		//Feedback
		text.append("\n");
		text.append("\n");
		text.append("\nFeedback from SAWHET: ");
		text.append("\n");
				
		worddoc.addContentWORD("\n", false);
		worddoc.addContentWORD("\n", false);
		worddoc.addContentWORD("***************************************************************************************************************", true);
		worddoc.addContentWORD("\n", false);
		worddoc.addContentWORD("Feedback from SAWHET", true);
		worddoc.addContentWORD("\n", false);
		worddoc.addContentWORD("Disclaimer:", false);
		worddoc.addContentWORD("\n", false);
		worddoc.addContentWORD(FeedbackText.get_disclaimer(), false);
		worddoc.addContentWORD("\n", false);
				
		//go throuth all annotations
		for (int i = 0; i < _sections.size(); i++) {
			String section = _sections.get(i);
			AnnotationSet annotationSet = _default.get(section);
			Annotation annotation = annotationSet.iterator().next();  
			
			FeatureMap features = annotation.getFeatures();
			Iterator it = features.entrySet().iterator();
			
			while (it.hasNext()) {
				Entry pair = (Entry) it.next();
				
				//iterate through flags and give feedback
				if (section == "Title" && pair.getKey() == "informal") {
					worddoc.addContentWORD(section + " contains informal language.", false);
				} else if (section == "Title" && pair.getKey() == "tooLong") {
					worddoc.addContentWORD(section + " is too long.", false);
				} else if (section == "Abstract" && pair.getKey() == "outcomeMiss") {
					worddoc.addContentWORD(section + " is missing outcomes.", false);
				} else if (section == "Abstract" && pair.getKey() == "tooShort") {
					worddoc.addContentWORD(section + " is too short.", false);
				} else if (section == "Abstract" && pair.getKey() == "citation") {
					worddoc.addContentWORD(section + " contains citations.", false);
				} else if (section == "Abstract" && pair.getKey() == "informal") {
					worddoc.addContentWORD(section + " contains informal language.", false);
				} else if (section == "Abstract" && pair.getKey() == "naive") {
					worddoc.addContentWORD(section + " contains naive language.", false);
				} else if (section == "Abstract" && pair.getKey() == "details") {
					worddoc.addContentWORD(section + " has too many details from Materials and Methods and/or Results.", false);	
				} else if (section == "Intro" && pair.getKey() == "tooShort") {
					worddoc.addContentWORD(section + " is too short.", false);
				} else if (section == "Intro" && pair.getKey() == "tooLong") {
					worddoc.addContentWORD(section + " is too long.", false);
				} else if (section == "Intro" && pair.getKey() == "citation" && pair.getValue() == "no") {
					worddoc.addContentWORD(section + " has no citations.", false);
				} else if (section == "Intro" && pair.getKey() == "citation" && pair.getValue() == "wrong") {
					worddoc.addContentWORD(section + " has incorrect citation format.", false);
				} else if (section == "Intro" && pair.getKey() == "informal") {
					worddoc.addContentWORD(section + " contains informal language.", false);
				} else if (section == "Intro" && pair.getKey() == "noHypothesis") {
					worddoc.addContentWORD(section + " lacks a hypothesis.", false);
				} else if (section == "Intro" && pair.getKey() == "weakHypothesis") {
					worddoc.addContentWORD(section + " has a hypothesis but it is formulated rather weak.", false);
				} else if (section == "Intro" && pair.getKey() == "quotes") {
					worddoc.addContentWORD(section + " contains quotes.", false);
				} else if (section == "Intro" && pair.getKey() == "stats") {
					worddoc.addContentWORD(section + " contains too many statistical details that should go into Materials and Methods.", false);
				} else if (section == "Intro" && pair.getKey() == "decimals") {
					worddoc.addContentWORD(section + " contains detailed numbers that probably should go into Material and Methods.", false);
				} else if (section == "Intro" && pair.getKey() == "details") {
					worddoc.addContentWORD(section + " contains details that should go into Materials and Methods.", false);
				} else if (section == "MM" && pair.getKey() == "recipeStyle") {
					worddoc.addContentWORD(section + " is written as a recipe rather than as a report on what has been done.", false);
				} else if (section == "MM" && pair.getKey() == "tooShort") {
					worddoc.addContentWORD(section + " is too short.", false);
				} else if (section == "MM" && pair.getKey() == "tooLong") {
					worddoc.addContentWORD(section + " is too long.", false);
				} else if (section == "MM" && pair.getKey() == "stats" && pair.getValue() == "no") {
					worddoc.addContentWORD(section + " has no reference to statistics.", false);
				} else if (section == "MM" && pair.getKey() == "informal") {
					worddoc.addContentWORD(section + " contains informal language.", false);
				} else if (section == "Results" && pair.getKey() == "tooShort") {
					worddoc.addContentWORD(section + " is too short.", false);
				} else if (section == "Results" && pair.getKey() == "tooLong") {
					worddoc.addContentWORD(section + " is too long.", false);
				} else if (section == "Results" && pair.getKey() == "citations") {
					worddoc.addContentWORD(section + " has citations.", false);
				} else if (section == "Results" && pair.getKey() == "informal") {
					worddoc.addContentWORD(section + " contains informal language.", false);
				} else if (section == "Results" && pair.getKey() == "hypothesis") {
					worddoc.addContentWORD(section + " refers to hypothesis.", false);
				} else if (section == "Results" && pair.getKey() == "interpretation") {
					worddoc.addContentWORD(section + " contains interpretation.", false);
				} else if (section == "Results" && pair.getKey() == "stats") {
					worddoc.addContentWORD(section + " lacks statistical outcome.", false);
				} else if (section == "Results" && pair.getKey() == "decimals") {
					worddoc.addContentWORD(section + " lacks specific numbers.", false);
				} else if (section == "Results" && pair.getKey() == "figReference") {
					worddoc.addContentWORD(section + " does not refers to figures and tables.", false);
				} else if (section == "Discussion" && pair.getKey() == "tooShort") {
					worddoc.addContentWORD(section + " is too short.", false);
				} else if (section == "Discussion" && pair.getKey() == "tooLong") {
					worddoc.addContentWORD(section + " is too long.", false);
				} else if (section == "Discussion" && pair.getKey() == "citation" && pair.getValue() == "no") {
					worddoc.addContentWORD(section + " has no citations.", false);
				} else if (section == "Discussion" && pair.getKey() == "citation" && pair.getValue() == "wrong") {
					worddoc.addContentWORD(section + " has incorrect citation format.", false);
				} else if (section == "Discussion" && pair.getKey() == "informal") {
					worddoc.addContentWORD(section + " contains informal language.", false);
				} else if (section == "Discussion" && pair.getKey() == "noHypothesis") {
					worddoc.addContentWORD(section + " does not refer to hypothesis.", false);
				} else if (section == "Discussion" && pair.getKey() == "quotes") {
					worddoc.addContentWORD(section + " contains quotes.", false);
				} else if (section == "Discussion" && pair.getKey() == "interpretation" && pair.getValue() == "no") {
					worddoc.addContentWORD(section + " lacks interpretation.", false);	
				} else if (section == "Discussion" && pair.getKey() == "stats") {
					worddoc.addContentWORD(section + " contains too many statistical details.", false);
				} else if (section == "Discussion" && pair.getKey() == "decimals") {
					worddoc.addContentWORD(section + " contains detailed numbers that should go into Results.", false);
				} else if (section == "Discussion" && pair.getKey() == "naive") {
					worddoc.addContentWORD(section + " contains naive language.", false);
				} else if (section == "Literature" && pair.getKey() == "literature") {
					worddoc.addContentWORD(section + " Citations are empty.", false);
				} else if (section == "Literature" && pair.getKey() == "onlyLabman") {
					worddoc.addContentWORD(section + " contains only the laboratory manual.", false);
				} 
				it.remove();;
				
			}

				
		}
		
		
		//save text file
		try {
			new WriteToFile(_doc, WriteToFile.Type.DOCUMENT, text);
		} catch (InvalidFormatException | IOException e) {
			System.out.println("Problem writing analysis file");
			e.printStackTrace();
		}
		
		//save word document
		worddoc.saveWord(_id + "_text");
		
		//delete image file
		image.deleteImage();
	}
	
	public String checkCourse() {
		String course = null;
		if (stringFor(_doc, _original.get("Course2")).isEmpty()) {
			course = stringFor(_doc, _original.get("Course1"));

		} else {
			if (stringFor(_doc, _original.get("QID19")).equals(stringFor(_doc, _original.get("Course1")))) {
				course = stringFor(_doc, _original.get("Course1"));

			} else if (stringFor(_doc, _original.get("QID19")).equals(stringFor(_doc, _original.get("Course2")))) {
				course = stringFor(_doc, _original.get("Course2"));

			} 
		}
		return course;
	}

	public String checkTA() {
		String tA = null;
		if (stringFor(_doc, _original.get("TA2Name")).isEmpty()) {
			tA = stringFor(_doc, _original.get("TA1Name"));
			_taEmail = stringFor(_doc, _original.get("TA1Email"));

		} else {
			if (_course.equals(stringFor(_doc, _original.get("Course1")))) {
				tA = stringFor(_doc, _original.get("TA1Name"));
				_taEmail = stringFor(_doc, _original.get("TA1Email"));

			} else if (_course.equals(stringFor(_doc, _original.get("Course2")))) {
				tA = stringFor(_doc, _original.get("TA2Name"));
				_taEmail = stringFor(_doc, _original.get("TA2Email"));
			} 
			
		} 
		return tA;
		
	}

	private static String getWordNo(String question, String info) {

		return info + stringFor(_doc, _original.get(question));
	}


	public static String get_tA() {
		return _tA;
	}


	public static String get_course() {
		return _course;
	}


	public static String get_id() {
		return _id;
	}


	public static String get_email() {
		return _email;
	}


	public static String get_name() {
		return _name;
	}


	public static String get_date() {
		return _date;
	}


	public static String get_number() {
		return _number;
	}


	public static String get_tA_email() {
		return _taEmail;
	}
}
