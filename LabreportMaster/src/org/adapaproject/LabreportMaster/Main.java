/**
 * 
 */
package org.adapaproject.LabreportMaster;

import java.io.IOException;

import org.adapaproject.LabreportMaster.analyses.CheckPlagiarism;
import org.adapaproject.LabreportMaster.analyses.StatAnalyses;
import org.adapaproject.LabreportMaster.analyses.CreateAnalysesDocument;
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
						
						if (!myEmail.equals("setarosd@wfu.edu")) {
							try {
								WritetoDatabase dbInsert = new WritetoDatabase();
								try {
									dbInsert.insertCitations();
									System.out.println("MyEmail: " + myEmail);
								} catch (Exception e) {
									System.err.println("Citations were not saved in database.");
									e.printStackTrace();
								}
								try {
									dbInsert.insertLabreports();
								} catch (Exception e) {
									System.err.println("Labreport info was not saved in database.");
									e.printStackTrace();
								}
							} catch (Exception e1) {
								System.err.println("Database could not be instantiated.");
								e1.printStackTrace();
							} 
						} else {
							System.out.println("Lab report is from Sabrina Setaro and was not saved in database.");
							log.error("this is bad");

						}
						
						//TODO: inactivated for testing only
/*						if (!myEmail.equals("setarosd@wfu.edu")) {
							//add to Mimir
							try {
								gate.addtoMimir(doc, CreateContentDocument.get_id());
								System.out.println("Would like to save to mimir if I may.");
							} catch (Exception e) {
								System.err.println("Saving to mimir failed.");
								e.printStackTrace();
							} 
						} else {
							System.out.println("Lab report is from Sabrina Setaro and was not saved to Mimir.");
						}*/
											
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
			System.out.println();
			System.out.println("***************************************************************************************");
			System.out.println("* PROCESS LAB REPORTS WAITING IN QUALTRICS                                            *");
			System.out.println("*                                                                                     *");
			System.out.println("* INFO: written by Sabrina D. Setaro (setarosd@wfu.edu) in 2017                       *");
			System.out.println("*       published under the GNU General Public License                                *");
			System.out.println("*                                                                                     *");
			System.out.println("* USAGE: java -jar LabreportMaster.java [Qualtrics_Survey_ID] [DataStore_Corpus_name] *");
			System.out.println("***************************************************************************************");
			System.out.println();
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
