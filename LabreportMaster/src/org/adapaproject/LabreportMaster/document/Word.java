/**
 * 
 */
package org.adapaproject.LabreportMaster.document;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * @author setarosd
 *
 */
public class Word {
	private XWPFDocument _worddoc;

	public Word() {
		_worddoc = new XWPFDocument();
		
	}

	
	public void addContentWORD(String text, boolean bold) throws InvalidFormatException, IOException {
		
		//create a paragraph
		XWPFParagraph paragraph = _worddoc.createParagraph();
		XWPFRun run = paragraph.createRun();
		
		run.setText(text);
		run.setBold(bold);
		
	}
	
	public void saveWord(String filename) throws IOException {
		String ta = CreateContentDocument.get_tA();
		StringBuffer directory = new StringBuffer();
		directory.append("/home/setarosd/git/gate/outputTAs/");
		directory.append(ta);
		directory.append("/labreport/");
		FileOutputStream output = new FileOutputStream(directory + filename + ".docx");
		_worddoc.write(output);
		output.close();
	}

	public void importImage() throws FileNotFoundException,
			InvalidFormatException, IOException {
		
		//create a paragraph
		XWPFParagraph paragraph = _worddoc.createParagraph();
		XWPFRun run_image = paragraph.createRun();
		
		//add image
		String imgFile = "image.png";
		FileInputStream is = new FileInputStream(imgFile);
		ImageFromURL im = new ImageFromURL();

		run_image.addPicture(is, XWPFDocument.PICTURE_TYPE_PNG, imgFile, Units.toEMU(470.4), Units.toEMU(275.2));

		is.close();
	}
}
