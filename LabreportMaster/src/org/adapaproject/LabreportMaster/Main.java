/**
 * 
 */
package org.adapaproject.LabreportMaster;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.adapaproject.LabreportMaster.analyses.CheckPlagiarism;
import org.adapaproject.LabreportMaster.analyses.StatAnalyses;
import org.adapaproject.LabreportMaster.analyses.CreateAnalysesDocument;
import org.adapaproject.LabreportMaster.database.WritetoDatabase;
import org.adapaproject.LabreportMaster.database.tables.LabreportsManager;
import org.adapaproject.LabreportMaster.document.CreateContentDocument;
import org.adapaproject.LabreportMaster.email.Email;
import org.adapaproject.LabreportMaster.gate.RunGate;
import org.apache.log4j.BasicConfigurator;
//import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.util.GateException;

/**
 * @author setarosd
 *
 */
public class Main {
	

	//private static Logger log = Logger.getLogger(Main.class);
	private static Logger log = Logger.getLogger("LabreportMaster");
	
	/**
	 * @param args
	 * @throws GateException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws GateException, IOException {
		
		FileHandler handler = null;
		try {
			handler = new FileHandler("LabreportLog.xml");
			log.addHandler(handler);
		} catch (Exception e) {
			log.log(Level.WARNING, "Error creating file", e);
		}
		
		//needed as logger
    	BasicConfigurator.configure();
    	
    	//start tracking time
    	long startTime = System.currentTimeMillis();
		
		if (args.length == 2) {
			
			//new Database();
			String lastID=null;
			try {
				lastID = LabreportsManager.getLastID();
			} catch (Exception e3) {
				log.log(Level.SEVERE, "Error getting last ID from database", e3);
			}
			
				if (lastID != null) {
					System.out.println(lastID);
					
					RunGate gate = new RunGate();
					gate.run(args[0], args[1], lastID);
					
					Corpus corpus = gate.get_corpus();
					
					//store in datastore
					try {
						gate.addtoDatastore();
					} catch (Exception e2) {
						log.log(Level.SEVERE, "Error saving labreports in datastore", e2);
					}
					
					//Load data from all data once, before iterating through all documents
					StatAnalyses analyses = new StatAnalyses();
					try {
						analyses.initiate();
					} catch (SQLException e5) {
						log.log(Level.SEVERE, "Error calling the database and initiate analyses", e5);
					}
					CheckPlagiarism plagcheck = null;
					try {
						plagcheck = new CheckPlagiarism();
					} catch (SQLException e5) {
						log.log(Level.SEVERE, "Error accessing citations to do plagiarism check", e5);
					}
										
					for (int i = 0; i < corpus.size(); i++) {
						
						ArrayList<String> datastoreIds;
						String datastoreId = null;
						try {
							datastoreIds = gate.get_datastoreIds();
							datastoreId = datastoreIds.get(i);
						} catch (Exception e2) {
							log.log(Level.SEVERE, "Failed to get datastore ID", e2);
						}
						
						Document doc = corpus.get(i);
						//save .txt and .doc files
						CreateContentDocument content = null;
						try {
							content = new CreateContentDocument(doc);
						} catch (InvalidFormatException e4) {
							log.log(Level.SEVERE, "Failed to initiate GATE", e4);
						}
						
						String id = null;
						try {
							id = content.get_id();
						} catch (Exception e3) {
							log.log(Level.WARNING, "Failed to get qualtrics ID", e3);
						}

						try {
							content.getInfoDatabase();
						} catch (Exception e2) {
							log.log(Level.SEVERE, "Failed to get info from database for labreport " + id + ".", e2);
						}
						
						try {
							content.printLabreport();
						} catch (Exception e2) {
							log.log(Level.SEVERE, "Failed to print labreport " + id + ".", e2);
						}
						
						//save analysis file
						try {
							analyses.comparison(doc);
							new CreateAnalysesDocument(doc, plagcheck);
						} catch (Exception e2) {
							log.log(Level.SEVERE, "Failed to do analyses " + id + ".", e2);
						}
						
						//send email
						try {
							Email labreport = new Email();
							labreport.generateEmail();
							System.out.println("Email was sent.");
						} catch (Exception e) {
							log.log(Level.SEVERE, "Email was not sent for " + id + ".", e);
						}
						
						//added to avoid saving testing data
						String myEmail=null;
						try {
							myEmail = CreateContentDocument.get_email();
						} catch (Exception e2) {
							log.log(Level.SEVERE, "Could not get email information for " + id + "." + id + ".", e2);
						}
						

						if (myEmail.equals("....")) {
						//TODO: change back after testing
							try {
								boolean success = false;
								WritetoDatabase dbInsert = new WritetoDatabase(doc, datastoreId);
								try {
									dbInsert.insertLabreports();
									success = true;
								} catch (Exception e) {
									//System.err.println("Labreport info was not saved in database.");
									log.log(Level.SEVERE, "Labreport info was not saved in database for " + id + ".", e);
								}
								try {
									if (success) {
										dbInsert.insertCitations();
									}
								} catch (Exception e) {
									//System.err.println("Citations were not saved in database.");
									log.log(Level.SEVERE, "Citations were not saved in database " + id + ".", e);
									
								}
								try {
									if (success) {
										dbInsert.insertStatistics();
									}
								} catch (Exception e) {
									//System.err.println("Statistics info was not saved in database.");
									log.log(Level.SEVERE, "Statistics info was not saved in database for " + id + ".", e);
								}
							} catch (Exception e1) {
								//System.err.println("Database could not be instantiated.");
								log.log(Level.SEVERE, "Database could not be instantiated.", e1);
							} 
						} else {
							//System.out.println("Lab report is from Sabrina Setaro and was not saved in database.");
							log.log(Level.WARNING, "Lab report is from Sabrina Setaro and was not saved in database for " + id + ".");

						}
						
											
					}
					
					Factory.deleteResource(corpus);
										
					gate.get_ds().close();
					
					timeCalculator(startTime);
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
