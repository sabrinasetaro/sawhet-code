/**
 * 
 */
package org.adapaproject.LabreportMaster.email;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.adapaproject.LabreportMaster.document.CreateContentDocument;
import org.adapaproject.LabreportMaster.document.WriteToFile;

/**
 * @author setarosd
 *
 */
public class Email {
	
	private static String _toTA;
	private static String _subject;
	private static String _textDirectory;
	private static String _analysisDirectory;
	private static String _taName;
	private static String _bcc = "setarosd@wfu.edu";
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	
	public Email() throws AddressException, MessagingException, IOException {
		
		//String studentEmail = CreateContentDocument.get_email();
		_taName = CreateContentDocument.get_tA();

		//TODO: switch back to Dan's email during production
		//_toTA = "johnsoad@wfu.edu";
		_toTA = "sabrina.setaro@gmail.com";
		_subject = CreateContentDocument.get_name();

		//this is the directory to the file previously generated
		_textDirectory = WriteToFile.get_textDirectory();
		_analysisDirectory = WriteToFile.get_analysisDirectory();

		//this sends out an email to students, with only lab report
		this.generateEmail();
		
	}
	
	
	private void generateEmail() throws AddressException, MessagingException, IOException {        

		//set SMTP server properties 
		final String username = "biolab@wfu.edu";
		final String token = "axpcptuhubjnqnuh";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth.mechanism", "XOAUTH2");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, token);
			}
		  });

		try {
			//set recipients, sender and subject
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("from-email@gmail.com"));
			msg.addRecipients(Message.RecipientType.TO,
				InternetAddress.parse(_toTA));
			msg.addRecipients(Message.RecipientType.BCC,
					InternetAddress.parse(_bcc));
			msg.setSubject("Lab Report of " + _subject + " (TA: " + _taName + ")");
			
			//create message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			String message = "Lab report and analyses are attached.\n\nRegards,\nLab Report Submission System\n\nFor Questions, please contact Sabrina Setaro, setarosd@wfu.edu";
			messageBodyPart.setContent(message, "text/plain");
			
			//create multi part
			Multipart multipart = new MimeMultipart();
			
			//add message
			multipart.addBodyPart(messageBodyPart);
			
			//add attachment 1
			MimeBodyPart attachPart1 = new MimeBodyPart();
			String attachFile1 = _analysisDirectory;
			attachPart1.attachFile(attachFile1);
			multipart.addBodyPart(attachPart1);
			
			//add attachment 2
			MimeBodyPart attachPart2 = new MimeBodyPart();
			String attachFile2 = _textDirectory + ".docx";
			attachPart2.attachFile(attachFile2);
			multipart.addBodyPart(attachPart2);
		
			
			//set multi-part as email content
			msg.setContent(multipart);
			
			//send message
			Transport.send(msg);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	
	}

}
