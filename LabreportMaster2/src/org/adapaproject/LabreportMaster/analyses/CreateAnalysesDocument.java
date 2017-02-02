/**
 * 
 */
package org.adapaproject.LabreportMaster.analyses;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import gate.Document;
import static gate.Utils.stringFor;

import org.adapaproject.LabreportMaster.analyses.RunReadability;
import org.adapaproject.LabreportMaster.document.WriteToFile;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;;

/**
 * @author setarosd
 *
 */
public class CreateAnalysesDocument {

	public CreateAnalysesDocument(Document doc, CheckPlagiarism plag) throws SQLException {
		
		StringBuffer string = new StringBuffer();
		RunReadability readability = new RunReadability(doc);
		StringBuffer plagResult = new StringBuffer();
		plagResult = plag.printResult(doc);
		
		string.append("Analysis ");
		string.append("(based on " + StatAnalyses.get_dataSize() + " documents) ");
		string.append("for labreport: ");
		string.append(stringFor(doc, doc.getAnnotations("Original markups").get("ResponseID")) + "\n");
		string.append(date() + "\n\n");
		string.append("Number of citations: ");
		string.append(doc.getAnnotations().get("Citation").size() + " --> ");
		string.append(StatAnalyses.get_citResult() + ".\n");
		string.append("Number of biology terms: ");
		string.append(doc.getAnnotations().get("Biology").size() + " --> ");
		string.append(StatAnalyses.get_bioResult() + ".\n");
		string.append("Number of sentences: ");
		string.append(doc.getAnnotations().get("Sentence").size() + " --> ");
		string.append(StatAnalyses.get_sentResult() + ".\n");
		string.append("Number of words: ");
		string.append(doc.getAnnotations().get("Token").size() + " --> ");
		string.append(StatAnalyses.get_tokenResult() + ".\n\n");
		string.append("Grade level for abstract: " + readability.get_abstract());
		string.append("\n");
		string.append("Grade level for introduction: " + readability.get_introduction());
		string.append("\n");
		string.append("Grade level for materials and methods: " + readability.get_methods());
		string.append("\n");
		string.append("Grade level for results: " + readability.get_results());
		string.append("\n");
		string.append("Grade level for discussion: " + readability.get_discussion());
		string.append("\n\n");
		string.append(plagResult);
		
		try {
			new WriteToFile(doc, WriteToFile.Type.ANALYSIS, string);
		} catch (InvalidFormatException | IOException e) {
			System.out.println("Problem writing analysis file");
			e.printStackTrace();
		}
	}
	
	private String date() {
		//date stamp
		Date myDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy 'at' h:mm a");
		String currentDate = sdf.format(myDate);
		return currentDate;
	}
}
