/**
 * 
 */
package org.adapaproject.LabreportMaster.database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.adapaproject.LabreportMaster.analyses.CheckPlagiarism;
import org.adapaproject.LabreportMaster.analyses.StatAnalyses;
import org.adapaproject.LabreportMaster.database.beans.Citation;
import org.adapaproject.LabreportMaster.database.beans.Labreport;
import org.adapaproject.LabreportMaster.database.beans.Statistic;
import org.adapaproject.LabreportMaster.database.tables.CitationsManager;
import org.adapaproject.LabreportMaster.database.tables.CoursesManager;
import org.adapaproject.LabreportMaster.database.tables.LabreportsManager;
import org.adapaproject.LabreportMaster.database.tables.StatisticsManager;
import org.adapaproject.LabreportMaster.database.tables.TeachingAssistantsManager;
import org.adapaproject.LabreportMaster.database.tables.UndergraduatesManager;
import org.adapaproject.LabreportMaster.document.CreateContentDocument;
import org.adapaproject.LabreportMaster.gate.RunGate;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import gate.Document;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;
import gate.AnnotationSet;

import static gate.Utils.stringFor;


/**
 * @author setarosd
 *
 */
public class WritetoDatabase {
	
	private static String _id;
	private static String _date;
	private static String _number;
	private static String _course ;
	private static String _email;
	private static String _taEmail;
	private static int _sentences;
	private static int _token;
	private static int _biology;
	private static AnnotationSet _original;
	private ArrayList<String> _citations;
	private static String _datastoreID;
	
	
	public WritetoDatabase(Document doc, String datastoreId) throws InvalidFormatException, IOException {
		
		CreateContentDocument create = new CreateContentDocument(doc);
		
		_id = CreateContentDocument.get_id();
		System.out.println("ID in WritetoDatabase: " + _id);
		_date = CreateContentDocument.get_date();
		System.out.println("date in WritetoDatabase: " + _date);
		_number = CreateContentDocument.get_number();
		_course = CreateContentDocument.get_course();
		_email = CreateContentDocument.get_email();
		_taEmail = CreateContentDocument.get_tA_email();
		_sentences = doc.getAnnotations().get("Sentence").size();
		_token = doc.getAnnotations().get("Token").size();
		_biology = doc.getAnnotations().get("Biology").size();
		_citations = CheckPlagiarism.get_sanitizedCitations();
		_datastoreID = datastoreId;
		System.out.println("Datastore Id in WritetoDatabase: " + _datastoreID);

		
	}

	public void insertLabreports() throws SQLException, PersistenceException, ResourceInstantiationException {
		
		int course_id = CoursesManager.getCourseID(_course);
		int student_id = UndergraduatesManager.getStudentId(_email);
		int ta_id = TeachingAssistantsManager.getTaId(_taEmail);
								
		Labreport bean = new Labreport();
		bean.set_qualtrics_id(_id);
		bean.set_datastore_id(_datastoreID);
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
				
		for (int i = 0; i < _citations.size(); i++) {
			
			System.out.println("citation size: " + _citations.size());
			String value = _citations.get(i);
			
			Citation bean = new Citation();
			bean.set_citations_value(value);
			bean.set_qualtrics_id(_id);
			
			boolean result = CitationsManager.insert(bean);
			
			if (result) {
				System.out.println("New row in citations was inserted!");
			}
		}
		
	}
	
	//just copied so far need to change
	public void insertStatistics() throws SQLException, PersistenceException, ResourceInstantiationException {
										
		Statistic bean = new Statistic();
		bean.set_qualtrics_id(_id);
		bean.set_sentences(_sentences);
		bean.set_token(_token);
		bean.set_biology(_biology);
		bean.set_citations(_citations.size());
		
		
		boolean result = StatisticsManager.insert(bean);
		
		if (result) {
			System.out.println("New row in statistics was inserted!");
		}
		
	}

}
