/**
 * 
 */
package org.adapaproject.LabreportMaster.document;

import static gate.Utils.stringFor;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import gate.AnnotationSet;
import gate.Document;

/**
 * @author setarosd
 *
 */
public class CreateContentDocument {
	private static Document _doc;
	private static AnnotationSet _original;
	private static AnnotationSet _results;
	private static String _course;
	private static String _tA;
	//needed for database
	private static String _taEmail;
	private static String _id;
	private static String _email;
	private static String _name;
	private static String _date;
	private static String _number;

	public CreateContentDocument(Document doc) throws IOException, InvalidFormatException {
		_doc = doc;
		_original = _doc.getAnnotations("Original markups");
		_results = _doc.getAnnotations();
		_course = this.checkCourse();
		_tA = this.checkTA();
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
			if (type.equals("normal")) {
				text.append("This is a regular lab report.\n");
			} else if (type.equals("rewrite")) {
				text.append("This is a rewrite of a previous lab report.\n");
			}
		} catch (Exception e) {
			//do nothing
		}
		
		text.append("Date submitted: ");
		text.append(_date);
		text.append("\n");
		
		//add text to worddoc
		worddoc.addContentWORD("Date submitted: " + stringFor(_doc, _original.get("EndDate")), false);
		
		text.append("Author of this lab report is: ");
		text.append(stringFor(_doc, _original.get("QID16_1_TEXT")));
		text.append(" ");
		text.append(stringFor(_doc, _original.get("QID16_2_TEXT")));
		text.append("\n");
		
		//add text to worddoc
		worddoc.addContentWORD("Author of this lab report is: " + stringFor(_doc, _original.get("QID16_1_TEXT")) + " " + stringFor(_doc, _original.get("QID16_2_TEXT")), false);
				
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
		String[] tableList = {"QID85", "QID84", "QID83", "QID82", "QID75"};
		
		//loop over tables and graphs and print link
		for (int i = 0; i < tableList.length; i++) {
			int tableNo = i + 1;
			
			//if it is not a joint table and entry for table is not empty:
			if (i != 4 && !stringFor(_doc, _original.get(tableList[i])).isEmpty()){
				text.append("\nTable ");
				text.append(tableNo + ": ");
				text.append(stringFor(_doc, _original.get(tableList[i])));
				text.append("\n");

				image.getImage(stringFor(_doc, _original.get(tableList[i])));
				
				//add header
				worddoc.addContentWORD("Table: " + tableNo, false);
				//add image
				worddoc.importImage();
				
			//if it is a joint table and entry for table is not empty:
			} else if (i == 4 && !stringFor(_doc, _original.get(tableList[i])).isEmpty()){
				text.append("\nJoint table file: ");
				text.append(stringFor(_doc, _original.get(tableList[i])));
				text.append("\n");
				
				image.getImage(stringFor(_doc, _original.get(tableList[i])));
				//add header
				worddoc.addContentWORD("Table: " + tableNo, false);
				
				//add image
				worddoc.importImage();
				
			} else {
				text.append("\nTable ");
				text.append(tableNo);
				text.append(": no files uploaded\n");
			}
		}
		
		//create array of figures
		String[] figureList = {"QID89", "QID88", "QID87", "QID86", "QID80"};
		
		
		//loop over figures and print link
		for (int i = 0; i < figureList.length; i++) {
			int figureNo = i + 1;
			
			//if it is not a joint figure and entry for figure is not empty:
			if (i != 4 && !stringFor(_doc, _original.get(figureList[i])).isEmpty()){
				text.append("\nFigure ");
				text.append(figureNo + ": ");
				text.append(stringFor(_doc, _original.get(figureList[i])));
				text.append("\n");
				
				image.getImage(stringFor(_doc, _original.get(figureList[i])));
				
				//add header
				worddoc.addContentWORD("Figure: " + figureNo, false);
				
				//add image
				worddoc.importImage();
				
			//if it is a joint figure and entry for figure is not empty:
			} else if (i == 4 && !stringFor(_doc, _original.get(figureList[i])).isEmpty()){
				text.append("\nJoint figure file: ");
				text.append(stringFor(_doc, _original.get(figureList[i])));
				text.append("\n");
				
				image.getImage(stringFor(_doc, _original.get(figureList[i])));
				
				//add header
				worddoc.addContentWORD("Figure: " + figureNo, false);
				
				//add image
				worddoc.importImage();
				
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
		String[] helpquestions = {"QID29", "QID35", "QID37", "QID42", "QID39", "QID41", "QID44", "QID47", "QID49", "QID51", "title", "abstract", "introduction", "materials and methods", "results", "discussion", "literature cited", "tables", "figures", "legends"};
		ArrayList<String> info = new ArrayList<String>();
		//loop to get information and print the right info in place
		for (int i = 0; i < 10; i++) {
			if(stringFor(_doc, _original.get(helpquestions[i])).isEmpty()) {
				//do nothing
			} else {
				int topic = i+10;
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
		
		text.append("\n\n*************************************************************************\n");
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
		}
		text.append("*************************************************************************\n");
		
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
	
	private String checkCourse() {
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

	private String checkTA() {
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
