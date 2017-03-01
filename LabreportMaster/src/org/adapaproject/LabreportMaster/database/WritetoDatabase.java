/**
 * 
 */
package org.adapaproject.LabreportMaster.database;

import java.sql.SQLException;
import java.util.ArrayList;

import org.adapaproject.LabreportMaster.analyses.CheckPlagiarism;
import org.adapaproject.LabreportMaster.database.beans.Citation;
import org.adapaproject.LabreportMaster.database.beans.Labreport;
import org.adapaproject.LabreportMaster.database.tables.CitationsManager;
import org.adapaproject.LabreportMaster.database.tables.CoursesManager;
import org.adapaproject.LabreportMaster.database.tables.LabreportsManager;
import org.adapaproject.LabreportMaster.database.tables.TeachingAssistantsManager;
import org.adapaproject.LabreportMaster.database.tables.UndergraduatesManager;
import org.adapaproject.LabreportMaster.document.CreateContentDocument;

import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;

/**
 * @author setarosd
 *
 */
public class WritetoDatabase {
	
	private static String _id = CreateContentDocument.get_id();
	private static String _date = CreateContentDocument.get_date();
	private static String _number = CreateContentDocument.get_number();
	private static String _course = CreateContentDocument.get_course();
	private static String _email = CreateContentDocument.get_email();
	private static String _taEmail = CreateContentDocument.get_tA_email();

	public void insertLabreports() throws SQLException, PersistenceException, ResourceInstantiationException {
		
		int course_id = CoursesManager.getCourseID(_course);
		int student_id = UndergraduatesManager.getStudentId(_email);
		int ta_id = TeachingAssistantsManager.getTaId(_taEmail);
								
		Labreport bean = new Labreport();
		bean.set_qualtrics_id(_id);
		bean.set_number(_number);
		bean.set_date(_date);
		bean.set_course_id(course_id);
		bean.set_ta_id(ta_id);
		bean.set_undergrad_id(student_id);
		
		
		boolean result = LabreportsManager.insert(bean);
		
		if (result) {
			System.out.println("New row in labreports was inserted!");
		}
		
	}

	public void insertCitations() throws SQLException, PersistenceException, ResourceInstantiationException {
				
		ArrayList<String> citations = CheckPlagiarism.get_sanitizedCitations();
				
/*		for (int i = 0; i < citations.size(); i++) {
			
			String value = citations.get(i);
			
			Citation bean = new Citation();
			bean.set_citations_value(value);
			bean.set_qualtrics_id(_id);
			
			boolean result = CitationsManager.insert(bean);
			
			if (result) {
				System.out.println("New row in citations was inserted!");
			}
		}*/
		
	}

}
