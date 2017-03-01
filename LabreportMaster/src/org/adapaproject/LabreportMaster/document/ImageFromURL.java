/**
 * 
 */
package org.adapaproject.LabreportMaster.document;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * @author setarosd
 *
 */
public class ImageFromURL {
	
	public void getImage(String linktext) throws IOException {
		
		BufferedImage image = null;
		URL url = new URL(linktext);
		image = ImageIO.read(url);

		File outputfile = new File("image.png");
		if (image != null) {
			ImageIO.write(image, "png", outputfile);
		}
	}
	
	public void deleteImage() {
		try {
			File imageFile = new File("image.png");
			//imageFile.delete();
		} catch (Exception e) {
			System.out.println("Image file could not be deleted, because it is not present.");
		}
	}
}
