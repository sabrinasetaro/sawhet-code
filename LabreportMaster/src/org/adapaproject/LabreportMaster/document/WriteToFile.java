/**
 * 
 */
package org.adapaproject.LabreportMaster.document;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.adapaproject.LabreportMaster.gate.RunGate;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import gate.Document;

/**
 * @author  setarosd
 */
public class WriteToFile {

	private static String _textDirectory;
	private static String _analysisDirectory;
	
	/**
	 * @throws IOException 
	 * 
	 */
	public WriteToFile(Document doc, Type typeOfDoc, StringBuffer text) throws IOException, InvalidFormatException {
		OutputStream outputstream = null;
		PrintStream printstream = null;
		
		String id = CreateContentDocument.get_id();
		String ta = CreateContentDocument.get_tA();
		StringBuffer directory = new StringBuffer();
		directory.append(RunGate.get_home());
		directory.append("/gate/outputTAs/");
		directory.append(ta);
		directory.append("/");
		
		try {
			try {
				switch (typeOfDoc) {
				case DOCUMENT:
					directory.append("labreport/");
					directory.append(id);
					//need this to attach file from correct directory later
					directory.append("_text");
					_textDirectory = directory.toString();
					directory.append(".txt");
					
					break;
					
				case ANALYSIS:
					directory.append("analyses/");
					directory.append(id);
					directory.append("_analysis.txt");
					
					//need this to attache file from correct directory later
					_analysisDirectory = directory.toString();
				}
				
				outputstream = new FileOutputStream(directory.toString());
			} catch (FileNotFoundException e) {
				System.out.println("Error while creating output file.");
				e.printStackTrace();
			}
			printstream = new PrintStream(outputstream);
			System.setOut(printstream);
			
			System.out.println(text);
			
			
		} finally {
			printstream.close();
			outputstream.close();
			System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		}
		
	}
	
	public enum Type {
		DOCUMENT, ANALYSIS
	}

	public static String get_textDirectory() {
		return _textDirectory;
	}

	public static String get_analysisDirectory() {
		return _analysisDirectory;
	}
}