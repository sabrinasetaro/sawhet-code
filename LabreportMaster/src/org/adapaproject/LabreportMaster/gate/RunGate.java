/**
 * 
 */
package org.adapaproject.LabreportMaster.gate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.utils.IOUtils;

import com.sun.org.apache.bcel.internal.util.SyntheticRepository;

import gate.Corpus;
import gate.CorpusController;
import gate.creole.ConditionalController;
import gate.DataStore;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.mimir.index.MimirConnector;
import gate.mimir.tool.WebUtils;
import gate.persist.PersistenceException;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;

/**
 * @author setarosd
 *
 */
public class RunGate {
	
	private CorpusController _controller;
	private static String _home = "...";

	private Corpus _corpus;
	private Corpus _corpusAll;
	private String _surveyID;
	private String _lastLabreportID;
	private String _dsCorpusName;
	private static DataStore _ds2;
	private ArrayList<String> _datastoreIds = new ArrayList<String>();
	private static DataStore _ds;

	public void run(String surveyID, String dsCorpusName, String lastLabreportID) throws GateException, IOException {
		
		_surveyID = surveyID;
		_lastLabreportID = lastLabreportID;
		_dsCorpusName = dsCorpusName;
		
		initialize();
		loadController();
		createSmallCorpus();
		execute();
		checkforLastID();
	}
	
	public void runAll() throws PersistenceException, ResourceInstantiationException {
		createCorpusAll();
	}
	
	
	/**
	 * @throws ResourceInstantiationException 
	 * @throws IOException 
	 * 
	 */
	private void createSmallCorpus() throws ResourceInstantiationException, IOException {
		_corpus = Factory.newCorpus("newCorpus");
		URL qualtrics = null;
		
		qualtrics = new URL(
				"https://...&SurveyID="
						+ _surveyID + "&LastResponseID=" + _lastLabreportID);
		
		
		//populate corpus
		try {
			gate.corpora.CorpusImpl.populate(_corpus, qualtrics, "Response", "utf-8", 0, "LabReport", "text/xml", true);
			if (_corpus.size() != 0) {
				System.out.println("Corpus size: " + _corpus.size());
			} else {
				System.out.println("Database is up to date - no new lab reports were submitted.");
			}
		} catch (Exception e) {
			System.err.println("There was some problem with downloading data from qualtrics. Try again later.");
			System.out.println(qualtrics);
		}
	}
	
	//this shall make sure that lab report executions are not repeated on lab reports that have been already processed.
	private void checkforLastID() {
		
		for (int i = 0; i < _corpus.size(); i++) {
			if(_corpus.get(i).getAnnotations("Original markups").get("ResponseID").equals(_lastLabreportID)) {
				System.err.println("Program exited, because last reported id present in corpus. Check if Qualtrics Survey ID is correct!");
				System.exit(0);
			} else {
				System.out.println("Everything clear: Last labreport from data base not present in corpus.");
			}
		}
				
	}
	
	private boolean checkfor400Error(URL qualtrics) throws IOException {
		boolean error = false;
		URLConnection connection = qualtrics.openConnection();
		Map<String, List<String>> map = connection.getHeaderFields();
		System.out.println("Printing All Response Header for URL: " + qualtrics.toString() + "\n");

		if (map.get("Status").get(0).equals("400")) {
			System.err.println("400 Error");
			error = true;
		}
		return error;
	}
	
	private void createCorpusAll() throws PersistenceException, ResourceInstantiationException {
		_ds2 = this.getDatastore("file:" + _home + "/...");

		_corpusAll = Factory.newCorpus("corpusAll");
		
		Integer corpora = _ds2.getLrIds("gate.corpora.SerialCorpusImpl").size();
		
		for (int i = 0; i < corpora; i++) {
			Object corpusName = _ds2.getLrIds("gate.corpora.SerialCorpusImpl").get(i);
			FeatureMap features = Factory.newFeatureMap();
			features.put(DataStore.LR_ID_FEATURE_NAME, corpusName);
			features.put(DataStore.DATASTORE_FEATURE_NAME, _ds2);
			_corpus = (Corpus) Factory.createResource("gate.corpora.SerialCorpusImpl", features);
			for (int j = 0; j < _corpus.size(); j++) {
				Document doc = _corpus.get(j);
				String email = gate.Utils.stringFor(doc, doc.getAnnotations("Original markups").get("EmailAddress"));
				String course = gate.Utils.stringFor(doc, doc.getAnnotations("Original markups").get("Course1"));
				//Suppress test lab reports
				if (email.equals("...")) {
					//do nothing
				} else if (course.equals("101")) {
					//do nothing
				} else {
					_corpusAll.add(doc);
				}
				
			}
			
		}
		
		System.out.println("Corpus with " + _corpusAll.size() + " labreports loaded.");
		_ds2.close();
		System.out.println("datastore closed");
	}
	
	private DataStore getDatastore(String datastore) throws PersistenceException {
		
		_ds2 = Factory.openDataStore("gate.persist.LuceneDataStoreImpl", datastore);
		return _ds2;
	}

	/**
	 * Initialize Gate, set Gate Home and config files
	 * @throws GateException
	 * @author setarosd
	 */
	private void initialize() throws GateException {

		//for exporting only
		//_home = System.getProperty("user.dir");
		Gate.setGateHome(new File(_home));
		Gate.setUserConfigFile(new File(_home + "/gate/userConfig.xml"));
		Gate.setPluginsHome(new File(_home + "/gate/plugins"));
		Gate.setSiteConfigFile(new File(_home + "/gate/gate.xml"));	
		Gate.init();
		System.out.println("Gate is initialized.");
	}
	
	/**
	 * Load gate controller so that analysis can run later
	 * @throws PersistenceException
	 * @throws ResourceInstantiationException
	 * @throws IOException
	 * @author setarosd
	 */
	private void loadController() {
		//load xgapp file
		try {
			_controller = (CorpusController)
									PersistenceManager.loadObjectFromFile(new File(_home + "/gate/..."));
			
			System.out.println("Controller loaded.");
		} catch (PersistenceException | ResourceInstantiationException | IOException e) {
			System.out.println("Controller could not be instantiated.");
			e.printStackTrace();
		}	
		
	}
	
	private void execute() throws ExecutionException {
		try {
			_controller.setCorpus(_corpus);
			_controller.execute();
		} finally {
			Factory.deleteResource(_controller);
			System.out.println("Controller deleted.");
		}
	}
	
	public void addtoDatastore() throws PersistenceException, ResourceInstantiationException {
		
		_ds = Factory.openDataStore("gate.persist.LuceneDataStoreImpl", "file:" + _home + "/gate/datastoreLabreports");
		//create a feature map to create corpus from datastore
		FeatureMap features = Factory.newFeatureMap();
		features.put(DataStore.LR_ID_FEATURE_NAME, _dsCorpusName);
		features.put(DataStore.DATASTORE_FEATURE_NAME, _ds);
		
		//create corpus from datastore
		Corpus dsCorpus = null;

		dsCorpus = (Corpus) Factory.createResource("gate.corpora.SerialCorpusImpl", features);
		System.out.println("Size of corpus in datastore: " + dsCorpus.size());
		
		
		//add new documents to datastore corpus; uncomment this, if you need to stop adding new documents to database
		for (int i = 0; i < _corpus.size(); i++) {
			dsCorpus.add(_corpus.get(i));
			_ds.sync(dsCorpus);
			String datastoreId = _corpus.get(i).getLRPersistenceId().toString();
			_datastoreIds.add(datastoreId);
		}
		
		System.out.println("Size of corpus in datastore is now: " + dsCorpus.size());
		Factory.deleteResource(dsCorpus);
		
	}

	
	public Corpus get_corpus() {
		return _corpus;
	}

	public Corpus get_corpusAll() {
		return _corpusAll;
	}
	
	public static String get_home() {
		return _home;
	}

	public ArrayList<String> get_datastoreIds() {
		return _datastoreIds;
	}

	public static DataStore get_ds() {
		return _ds;
	}
}
