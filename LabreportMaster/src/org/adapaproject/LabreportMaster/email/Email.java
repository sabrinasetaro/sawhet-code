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
	
	private static String _toME;
	private static String _toStudent;
	private static String _subject;
	private static String _textDirectory;
	private static String _taName;
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	
	public Email() throws AddressException, MessagingException, IOException {
		
		_toStudent = CreateContentDocument.get_email();
		_taName = CreateContentDocument.get_tA();

		_toME = "sabrina.setaro@gmail.com";
		_subject = CreateContentDocument.get_name();

		//this is the directory to the file previously generated
		_textDirectory = WriteToFile.get_textDirectory();

		//this.generateEmail();
		
	}
	
	
	public void generateEmail() throws AddressException, MessagingException, IOException {        

		//set SMTP server properties 
		final String username = "...";
		final String token = "...";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "...");
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
			msg.setFrom(new InternetAddress("..."));
			msg.addRecipients(Message.RecipientType.CC,
				InternetAddress.parse(_toStudent));
			msg.addRecipients(Message.RecipientType.CC,
					InternetAddress.parse(_toME));
			msg.setSubject("Lab Report of " + _subject + " (TA: " + _taName + ")");
			
			//create message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			String message = "Your lab report is attached.\n\nPlease go through the document and make sure the figures and tables are not too small or too big.\nAlso, check out the feedback part to see how you can improve your submission. In case you want to resubmit, just start a new submission in SAWHET.\n\nRegards,\nSabrina Setaro\n\nFor questions, please contact me at setarosd@wfu.edu";
			messageBodyPart.setContent(message, "text/plain");
			
			//create multi part
			Multipart multipart = new MimeMultipart();
			
			//add message
			multipart.addBodyPart(messageBodyPart);
			
			//add attachment 1
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
	
	public void generateErrorMail(String content) throws AddressException, MessagingException, IOException {        

		//set SMTP server properties 
		final String username = "...";
		final String token = "...";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "...");
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
			msg.setFrom(new InternetAddress("..."));
			msg.addRecipients(Message.RecipientType.TO,
				InternetAddress.parse("..."));
			//msg.addRecipients(Message.RecipientType.BCC,
					//InternetAddress.parse(_bcc));
			msg.setSubject("Sumbission Error");
			
			//create message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			String message = content;
			messageBodyPart.setContent(message, "text/plain");
			
			//send message
			Transport.send(msg);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	
	}

}
