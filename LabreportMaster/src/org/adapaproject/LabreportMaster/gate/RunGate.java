/**
 * 
 */
package org.adapaproject.LabreportMaster.gate;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import gate.Corpus;
import gate.CorpusController;
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
	private String _home = "/home/setarosd";
	private Corpus _corpus;
	private Corpus _corpusAll;
	private String _surveyID;
	private String _lastLabreportID;
	private String _dsCorpusName;
	private static DataStore _ds;
	
	public void run(String surveyID, String dsCorpusName, String lastLabreportID) throws GateException, IOException {
		_surveyID = surveyID;
		_lastLabreportID = lastLabreportID;
		_dsCorpusName = dsCorpusName;
		
		initialize();
		loadController();
		createSmallCorpus();
		execute();
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
		URL qualtrics;

		qualtrics = new URL("https://wakeforest.qualtrics.com/WRAPI/ControlPanel/api.php?Request=getLegacyResponseData&Token=UPjscdFr4VsGKElNEfeJSKRdXsey9fRlr1WDYy9P&Version=2.5&User=setarosd%23wakeforest&Format=XML&Labels=1&ExportTags=1&SurveyID=" + _surveyID + "&LastResponseID=" + _lastLabreportID);
		
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
		}
	}
	
	private void createCorpusAll() throws PersistenceException, ResourceInstantiationException {
		_ds = this.getDatastore("file:" + _home + "/gate/datastoreLabreports");

		_corpusAll = Factory.newCorpus("corpusAll");
		
		Integer corpora = _ds.getLrIds("gate.corpora.SerialCorpusImpl").size();
		
		for (int i = 0; i < corpora; i++) {
			Object corpusName = _ds.getLrIds("gate.corpora.SerialCorpusImpl").get(i);
			FeatureMap features = Factory.newFeatureMap();
			features.put(DataStore.LR_ID_FEATURE_NAME, corpusName);
			features.put(DataStore.DATASTORE_FEATURE_NAME, _ds);
			_corpus = (Corpus) Factory.createResource("gate.corpora.SerialCorpusImpl", features);
			for (int j = 0; j < _corpus.size(); j++) {
				Document doc = _corpus.get(j);
				String email = gate.Utils.stringFor(doc, doc.getAnnotations("Original markups").get("EmailAddress"));
				String course = gate.Utils.stringFor(doc, doc.getAnnotations("Original markups").get("Course1"));
				//Suppress test lab reports
				if (email.equals("setarosd@wfu.edu")) {
					//do nothing
				} else if (course.equals("101")) {
					//do nothing
				} else {
					_corpusAll.add(doc);
				}
				
			}
			
		}
		
		System.out.println("Corpus with " + _corpusAll.size() + " labreports loaded.");
	}
	
	private DataStore getDatastore(String datastore) throws PersistenceException {
		
		_ds = Factory.openDataStore("gate.persist.LuceneDataStoreImpl", datastore);
		return _ds;
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
									PersistenceManager.loadObjectFromFile(new File(_home + "/gate/LabReports_caseInsensitive.gapp"));
			
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
		
		DataStore ds = Factory.openDataStore("gate.persist.LuceneDataStoreImpl", "file:" + _home + "/gate/datastoreLabreports");
		//create a feature map to create corpus from datastore
		FeatureMap features = Factory.newFeatureMap();
		features.put(DataStore.LR_ID_FEATURE_NAME, _dsCorpusName);
		features.put(DataStore.DATASTORE_FEATURE_NAME, ds);
		
		//create corpus from datastore
		Corpus dsCorpus = null;

		dsCorpus = (Corpus) Factory.createResource("gate.corpora.SerialCorpusImpl", features);
		System.out.println("Size of corpus in datastore: " + dsCorpus.size());
		
		//add new documents to datastore corpus; uncomment this, if you need to stop adding new documents to database
		for (int i = 0; i < _corpus.size(); i++) {
			dsCorpus.add(_corpus.get(i));
		}

		ds.sync(dsCorpus);
		System.out.println("Size of corpus in datastore is now: " + dsCorpus.size());
		Factory.deleteResource(dsCorpus);
		ds.close();
		
	}
	
	public void addtoMimir(Document doc, String labreportID) throws MalformedURLException, IOException, InterruptedException {
		MimirConnector index = null;
		try {
			index = new MimirConnector(
					new URL("http://biolabs.wfu.edu/mimir-cloud-5.2/454a9100-199e-4a09-b758-fa4c72f7683c"),
					new WebUtils("manager", "addPilz343$"));
			index.sendToMimir(doc, labreportID);
			System.out.println("Document added to Mimir (http://biolabs.wfu.edu/mimir-cloud-5.2/454a9100-199e-4a09-b758-fa4c72f7683c).");
		} finally {
			index.close();
		}
		
	}
	
	public Corpus get_corpus() {
		return _corpus;
	}

	public Corpus get_corpusAll() {
		return _corpusAll;
	}

}
