package org.adapaproject.LabreportMaster.document;

public class FeedbackText {
	
	//Disclaimer
	private static String _disclaimer1 = "This section contains feedback that was automatically created by SAWHET, the lab report submission software of the Biology department.";
	private static String _disclaimer2 = "Please note that SAWHET can give you useful hints on how to improve your lab report. SAWHET is trained to detect the most common problems found in lab reports. So we advise you to read through the comments carefully.";
	private static String _disclaimer3 = "Sometimes, SAWHET makes mistakes and flags something as problematic, when it is not a real problem. In that case, just ignore the comment. Also, please know that SAWHET is only programmed to give you advice for the most problematic errors found in lab reports. These are especially about the organization and format of a scientific paper. All other issues such as flaws and scientific writing problems, will be commented by your TA.";
	
	//Title
	private static String _longTitle = "The title appears to be longer than usual. It should be shorter than 20 words. Please revise and shorten if you can.";
	private static String _informalTitle = "The title contains colloquial language, e.g. ‘this lab report’. Please change your title so it reads like a title of a scientific paper. A good idea is to use the main outcome of your paper and use that as the title.";
	
	//Abstract
	private static String _shortAbstract = "The abstract seems to be a bit short. The abstract is a summary of a scientific paper and contains the most important information of each section. Make sure you included a bit of Introduction, Materials and Methods, Results and Discussion in your abstract.";
	private static String _missOutcomeAbs = "It appears that your abstract does not contain outcomes of your experiment. Make sure you briefly state what the main outcome and interpretation of your paper is.";
	private static String _citAbstract = "SAWHET detected citations in your abstract. An abstract does not contain citations, because everything you write in the abstract, will also occur again later. If you are stating scientific facts in your abstract, you cite that in your introduction section instead.";
	private static String _colqAbstract = "The abstract seems to have some colloquial language, such as ‘my group’ or ‘this lab’. Please revise and write as you would have actually conducted a scientific experiment to be published in a scientific paper. It is ok to write ‘we’ or ‘our results’.";
	private static String _naiveAbstract = "It looks like you are using words like ‘correct’, ‘proven’ or ‘wrong’ to describe the outcome or outlook of your experiment. This is problematic, because it implies that experiments can be either correct or wrong and that hypotheses can be proven. All we can do with experiments is to support or reject a hypothesis. Also, data that oppose a hypothesis are not wrong, they just do not support our thinking.";
	private static String _detailsAbstract = "Make sure the abstract doesn't contain too many details, especially for the Materials and Methods part. For example, information on how much mM will be used is normally too detailed and should only be mentioned in the Materials and Methods section.";
	
	//Introduction
	private static String _shortIntro = "The introduction seems to be a bit short. The introduction section is the longest sections of a scientific paper, together with the Discussion section, because they introduce the reader to the topic. They also give reasoning why the experiment was conducted, what the study organisms are and what outcome the authors expect and why. Make sure you gave enough attention to this section and revise if necessary."; 
	private static String _longIntro = "The introduction seems to be a bit long. The introduction section introduce the reader to the topic, give reasoning why the experiment was conducted, what the study organisms are and what outcome the authors expect and why. However, it gives just enough detail for the reader to understand the purpose of the experiment and leaves unnecessary detail out. Also, make sure you are not adding too much information about the study organisms and experiments that would otherwise go into the Materials and Methods section.";
	private static String _missCitIntro = "It looks like you are missing citations in your introduction. The introduction section contains background information about biological facts that are based on the knowledge of science and need to be cited. You need to have at least one primary literature citation (the lab manual is not a primary resource!) in the introduction, but ideally one for each scientific fact you are stating.";
	private static String _wrongCitIntro = "SAWHET detected some issues with your citation format. The correct citation format is: [LastnameFirstAuthor: Year], for example [Smith: 2002]. If you are citing more than two papers it would be: [LastnameFirstAuthor: Year][LastnameFirstAuthor: Year]. Please revise accordingly.";
	private static String _quotesIntro = "There seem to be quotes in your introduction. We don't quote in biology paper, but rephrase facts stated by other scientist in our own words and add the citation at the end of the senctence.";
	private static String _colqIntro = "The Introduction contains colloquial language, e.g. ‘this lab report’. Please change change so it reads like a scientific paper.";
	private static String _noHypIntro = "It appears that you didn't state your hypothesis in the Introduction. Please state towards the end of the introduction what your hypothesis is.";
	private static String _weakHypIntro = "It appears that you make some predictions about the outcome of your experiment, but it is formulated vaguely. Please rephrase so it is clear what your hypothesis is.";
	private static String _statsIntro = "Make sure you are not mixing your Introduction with Materials and Methods. SAWHET detected language that refers to statistics. This would normally be too detailed for the Introduction. Please check and transfer the information to the Materials and Methods section.";
	private static String _detailsIntro = "Make sure the abstract doesn't contain too many details, especially for the Materials and Methods part. For example, information on how much mM will be used is normally too detailed and should only be mentioned in the Materials and Methods section.";
	private static String _decimalsIntro = "It looks like you are stating decimal numbers in your introduction, which indicates that you might be too detailed in your description. Please check if this information is not more appropriate for the Materials and Methods section.";
	
	//Materials and Methods
	private static String _shortMM = "SAWHET detected that the method section is a bit short. This could indicate that necessary information is missing. The Materials and Methods part should contain a detailed description of what has been done during the study, including chemicals, procedures and analyses. The intend is to make the study reproducible and transparent.";
	private static String _longMM = "It looks like this section is longer than usual. This might not be a problem, but could indicate that there is information in the Methods section that belongs somewhere else in the paper. For example, it could be that you are already stating what the outcome of the experiment has been instead of writing that into the Results section. It could also indicate that you are a bit too detailed. Materials and Methods should be detailed enough to reproduce the experiment but you do not have to list every step, e.g. whether you used gloves or not. Please check and revise if necessary.";
	private static String _recipeMM = "SAWHET found indication that you might have written the Materials and Methods section like a recipe in a cookbook or the lab manual instruction. Materials and Methods should be written mainly in past tense as it states what have been done in the experiment not what somebody else needs to do in order to reproduce the experiment. Even though both styles would get the necessary information across, the recipe style is never used in scientific papers.";
	private static String _colqMM = "This section seems to have some colloquial language, such as ‘my group’ or ‘in this lab’. Please revise and write as you would have actually conducted a scientific experiment to be published in a scientific paper. Hint: it is ok to write ‘we used...’.";
	private static String _missStatsMM = "It seems like you did not include statistical analyses in your paper. In case you did, make sure you state which test you used in the Materials and Methods section. Also, please give enough detail about the test, for example whether it was a one- or two-tailed T-test or what your significance cutoff value (e.g. alpha value) was. If you have this information in the result section instead, take it out and put it in Methods. The results should only contain the outcome of the tests, not details about the analysis.";
	
	//Results
	private static String _shortResults = "It looks like this section is a bit short. The result section contains the outcome of the study without interpretation and refers to graphs and figures. It is not a repetition of the figure legends and should not just refer to the figures. Please check whether you described the outcome in your results section with enough detail.";
	private static String _longResults = "It looks like this section is a bit long. This could be an indication that there is information in this section that belongs to either Materials and Methods or to Discussion. The results section should only contain the outcome of the study without interpretation of the results or how the outcome was obtained. It could also be that your text is a bit repetitive. If so, please revise accordingly.";
	private static String _colqResults = "This section seems to have some colloquial language, such as ‘my group and I found’ or ‘in this lab’. Please revise and write as you would have actually conducted a scientific experiment to be published in a scientific paper.";
	private static String _citResults = "SAWHET detected citations in your results section. Citations should not be in this section because Results only states the outcome of the study without interpretation and references to other papers. It should also not contain citations that refer to specific methods that has been used and published elsewhere. Please take these citations and respective information out of the Result section and put them in the adequat sections instead (e.g. Methods or Discussion).";
	private static String _missStatResults = "It seems like you might miss the outcome of your statistical tests in the Results section. If you have done statistical tests, please state what your effect sizes and p-values were.";
	private static String _missDecResults = "Your Result section looks like it is missing decimal numbers. Normally, the outcome of a statistical test contains numbers that have at least two digits after the decimal point. Please revise and make sure you are not missing any important information, such as effect size and p-values.";
	private static String _missRefResults = "SAWHET could not detect any reference to figures or tables in your Result section. If you do not have graphs or figures that display your results, please think carefully if there is a good reason why not. Normally, all scientific papers have outcomes graphically represented in graphs or figures. If you have figures and graphs, make sure you point to them in your Results section.";
	private static String _intResults = "It looks like your Result section contains interpretation such as ‘...this could be explained by…’. The goal of Results is to state your outcomes in a meaningful but objective way so that a reader can think about your outcomes independently of what your interpretation is. For example, you can say ‘...our measurements show that caterpillars with treatment X were significantly larger than with treatment Y..’, but do not put this outcome into broader context or find explanation of why that is. Please check your Results section for interpretation and make sure to take all interpretation out and put it into Discussion instead.";
	private static String _hypResults = "It seems you are referring to your hypothesis in the Results section. We consider this as interpretation, so please check if this is the case and if so, please move the respective statement to Discussion. The goal of Results is to state your outcomes in a meaningful but objective way so that a reader can think about your outcomes independently of what your interpretation is.";
	
	//Discussion
	private static String _shortDiscussion = "Your discussion seems too short. A discussion is the place where you interpret your results and put the outcomes of the study into broader context. Normally, it is much longer than the Result section. A short discussion can be an indicator that you have not interpreted the data in enough detail. Please revise and change accordingly.";
	private static String _longDiscussion = "Your discussion seems a bit long. A discussion is the place where you interpret your results and put the outcomes of the study into broader context. It normally is quite extensive. However, a very long Discussion could be an indicator that you added content that would rather belong into the results section. Please check and revise accordingly.";
	private static String _missCitDiscussion = "SAWHET could not detect any citations. The Discussion section is where you compare your results with what others have found, so there need to be citations. You are required to cite at least one primary resource (the lab manual is not a primary resource), but more is normally better because it shows that you are evaluating your results from different angles.";
	private static String _wrongCitDiscussion = "SAWHET detected some issues with your citation format. The correct citation format is: [FirstAuthor: Year]. If you are citing more than two papers it would be: [FirstAuthor: Year][FirstAuthor: Year]. Please revise accordingly.";
	private static String _quotesDiscussion = "There seem to be quotes in your Discussion. We do not quote in biology papers but rephrase facts stated by other scientist in our own words and add the citation at the end of the sentence.";
	private static String _colqDiscussion = "It looks like you have some colloquial language, such as ‘my group and I found’ or ‘in this lab’. Please revise and write as you would actually write a scientific paper. You can use language like ‘we found’ or ‘I found’.";
	private static String _naiveDiscussion = "It looks like you are using words like ‘correct’, ‘proven’ or ‘wrong’ to describe the outcome or outlook of your experiment. This is problematic, because it implies that experiments can be either correct or wrong and that hypotheses can be proven. All we can do with experiments is to support or reject a hypothesis. Also, data that oppose a hypothesis are not wrong, they just do not support our thinking.";
	private static String _missHypDiscussion = "It appears you missed referring back to your hypothesis. The Discussion is the place where you interpret your results and state how they relate to your initial hypothesis. Please check if you are indeed missing this and revise accordingly.";
	private static String _missIntDiscussion = "The Discussion seems to lack interpretation. The Discussion is the place where you interpret your results and put the outcomes of the study into broader context. Please make sure you were not just stating the outcomes, but giving your suggestions on what your results mean and how they relate to other studies.";
	private static String _detailsDiscussion = "SAWHET found detailed information in your Discussion. This could be an indication that you are putting too much information about your results (e.g. effect sizes or p-values, mM values) in this section. Ideally, these details should be stated in the Results section and only generally referred to in the Discussion. For example, instead of saying ‘the p-value was 0.000345’, you can just write ‘the statistically significant result…’";
	
	//Literature Cited
	private static String _emptyLit = "Make sure you have references in here that point to the literature you cited in your paper.";
	private static String _onlyLMLit = "It appears you are only citing the lab manual. You are required to have at least one reference to primary literature in your Introduction and Discussion section. The lab manual is not a primary source.";
	
	
	//Getters
	public static String get_disclaimer1() {
		return _disclaimer1;
	}
	
	public static String get_disclaimer2() {
		return _disclaimer2;
	}
	
	public static String get_disclaimer3() {
		return _disclaimer3;
	}

	public static String get_longTitle() {
		return _longTitle;
	}

	public static String get_informalTitle() {
		return _informalTitle;
	}

	public static String get_shortAbstract() {
		return _shortAbstract;
	}

	public static String get_missOutcomeAbs() {
		return _missOutcomeAbs;
	}

	public static String get_citAbstract() {
		return _citAbstract;
	}

	public static String get_colqAbstract() {
		return _colqAbstract;
	}

	public static String get_naiveAbstract() {
		return _naiveAbstract;
	}

	public static String get_detailsAbstract() {
		return _detailsAbstract;
	}

	public static String get_shortIntro() {
		return _shortIntro;
	}

	public static String get_longIntro() {
		return _longIntro;
	}

	public static String get_missCitIntro() {
		return _missCitIntro;
	}

	public static String get_wrongCitIntro() {
		return _wrongCitIntro;
	}

	public static String get_quotesIntro() {
		return _quotesIntro;
	}

	public static String get_colqIntro() {
		return _colqIntro;
	}

	public static String get_noHypIntro() {
		return _noHypIntro;
	}

	public static String get_weakHypIntro() {
		return _weakHypIntro;
	}

	public static String get_statsIntro() {
		return _statsIntro;
	}

	public static String get_decimalsIntro() {
		return _decimalsIntro;
	}

	public static String get_detailsIntro() {
		return _detailsIntro;
	}

	public static String get_shortMM() {
		return _shortMM;
	}

	public static String get_longMM() {
		return _longMM;
	}

	public static String get_recipeMM() {
		return _recipeMM;
	}

	public static String get_colqMM() {
		return _colqMM;
	}

	public static String get_missStatsMM() {
		return _missStatsMM;
	}

	public static String get_shortResults() {
		return _shortResults;
	}

	public static String get_longResults() {
		return _longResults;
	}

	public static String get_colqResults() {
		return _colqResults;
	}

	public static String get_citResults() {
		return _citResults;
	}

	public static String get_missStatResults() {
		return _missStatResults;
	}

	public static String get_missDecResults() {
		return _missDecResults;
	}

	public static String get_missRefResults() {
		return _missRefResults;
	}

	public static String get_intResults() {
		return _intResults;
	}

	public static String get_hypResults() {
		return _hypResults;
	}

	public static String get_shortDiscussion() {
		return _shortDiscussion;
	}

	public static String get_longDiscussion() {
		return _longDiscussion;
	}

	public static String get_missCitDiscussion() {
		return _missCitDiscussion;
	}

	public static String get_wrongCitDiscussion() {
		return _wrongCitDiscussion;
	}

	public static String get_quotesDiscussion() {
		return _quotesDiscussion;
	}

	public static String get_colqDiscussion() {
		return _colqDiscussion;
	}

	public static String get_naiveDiscussion() {
		return _naiveDiscussion;
	}

	public static String get_missHypDiscussion() {
		return _missHypDiscussion;
	}

	public static String get_missIntDiscussion() {
		return _missIntDiscussion;
	}

	public static String get_detailsDiscussion() {
		return _detailsDiscussion;
	}

	public static String get_emptyLit() {
		return _emptyLit;
	}

	public static String get_onlyLMLit() {
		return _onlyLMLit;
	}
	
}
