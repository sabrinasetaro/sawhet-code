package org.adapaproject.LabreportMaster.document;

public class FeedbackText {

	private static String _disclaimer = "This section contains feedback that was automatically created by SAWHET, the lab report submission software of the Biology department. Please note that SAWHET can give you useful hints on how to improve your lab report. SAWHET is trained to detect the most common problems found in lab reports. SAWHET is correct in xxx out of xxx times, so we advise you to read through the comments carefully. Sometimes, SAWHET makes mistakes and flags something as problematic, when it is not a real problem. In that case, just ignore the comment. Also, please know that SAWHET is only programmed to give you advice for the most problematic errors found in lab reports. These are especially about the organization and format of a scientific paper. All other issues such as flaws and scientific writing problems, will be commented by your TA.";

	
	
	public static String get_disclaimer() {
		return _disclaimer;
	}
	
}
