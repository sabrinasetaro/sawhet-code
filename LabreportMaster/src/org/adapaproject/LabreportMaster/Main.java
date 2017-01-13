/**
 * 
 */
package org.adapaproject.LabreportMaster;

import java.io.IOException;

import org.adapaproject.LabreportMaster.analyses.CheckPlagiarism;
import org.adapaproject.LabreportMaster.analyses.StatAnalyses;
import org.adapaproject.LabreportMaster.analyses.CreateAnalysesDocument;
import org.adapaproject.LabreportMaster.database.Database;
import org.adapaproject.LabreportMaster.database.WritetoDatabase;
import org.adapaproject.LabreportMaster.database.tables.LabreportsManager;
import org.adapaproject.LabreportMaster.document.CreateContentDocument;
import org.adapaproject.LabreportMaster.email.Email;
import org.adapaproject.LabreportMaster.gate.RunGate;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.util.GateException;

/**
 * @author setarosd
 *
 */
public class Main {

	private static Logger log = Logger.getLogger(Main.class);
	
	/**
	 * @param args
	 * @throws GateException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws GateException, IOException {
		
		//needed as logger
    	BasicConfigurator.configure();
    	
    	//start tracking time
    	long startTime = System.currentTimeMillis();
		
		if (args.length == 2) {
			
			//new Database();
			String lastID = LabreportsManager.getLastID();
			try {
				if (lastID != null) {
					System.out.println(lastID);
					
					RunGate gate = new RunGate();
					gate.run(args[0], args[1], lastID);
					
					Corpus corpus = gate.get_corpus();
					
					//Load data from all data once, before iterating through all documents
					StatAnalyses analyses = new StatAnalyses();
					analyses.initiate();
					CheckPlagiarism plagcheck = new CheckPlagiarism();
										
					for (int i = 0; i < corpus.size(); i++) {
						
						Document doc = corpus.get(i);
						//save .txt and .doc files
						CreateContentDocument content = new CreateContentDocument(doc);
						content.printLabreport();
						
						//save analysis file
						analyses.comparison(doc);
						new CreateAnalysesDocument(doc, plagcheck);
						
						//send email
						try {
							new Email();
							System.out.println("Email was sent.");
						} catch (Exception e) {
							System.err.println("Email was not sent, there was some problem.");
						}
						
						//added to avoid saving testing data
						String myEmail = CreateContentDocument.get_email();
						
						WritetoDatabase dbInsert = new WritetoDatabase();
						//dbInsert.insertCitations();
						dbInsert.insertLabreports();
						
						if (!myEmail.equals("setarosd@wfu.edu")) {
							//add to Mimir
							try {
								gate.addtoMimir(doc, CreateContentDocument.get_id());
							} catch (Exception e) {
								System.err.println("Saving to mimir failed.");
								e.printStackTrace();
							} 
						}
											
					}
					//TODO:inactivated for testing only
					//gate.addtoDatastore();
					
					Factory.deleteResource(corpus);
					
					timeCalculator(startTime);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			System.out.println("USAGE: java -jar LabreportMaster.java [Qualtrics Survey ID] [DataStore Corpus name]");
		}

	}
	
	private static void timeCalculator(long startTime) {
		//calculate how long it took to run
		long endTime = System.currentTimeMillis();
		long totalTime = (endTime - startTime) / 1000;
		System.out.println();
		System.out.println("Finished after " + totalTime + " seconds.");
	}

}
