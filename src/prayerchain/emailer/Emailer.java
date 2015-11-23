package prayerchain.emailer;


import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import prayerchain.parser.*;

public class Emailer 
{
	String sender;
	String username;
	String password;
	String[] recipients;
	String host;

	public Emailer(String host, String senderEmail, String username, String password)
	{
		this.host = host;
		this.sender = senderEmail;
		this.username = username;
		this.password = password;
	}


	public Session getSession()
	{
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication() 
			{
				return new PasswordAuthentication(username, password);
			}
		});
		return session;
	}


	public void setRecipients(String[] recipients)
	{
		this.recipients = recipients;
	}

	
	
	//public String createMessageString(PrayerDay pd);
	
	public void sendMessage(Session session, PrayerDay pd)
	{
		String messageText = "<p>Greetings,</p> <p>This is a simple reminder that you have signed up for " + 
				"the prayer chain tomorrow. Thanks! You should be able to find your time slot below, " + 
				"along with the person who is before and after you.</p> <p>Here's how this is going to " +
				"work: 5 minutes before your time slot begins, the person before you will call you. " +
				"You two will pray with each other for a few minutes, and then at some point, they " +
				"will hang up and you will continue praying. 5 minutes before your time ends, you " + 
				"will call the person after you, and you'll do the same thing as you did with the " + 
				"person before you.</p> <p>There's a chance that there might not be someone signed up " +
				"before or after you. If that's the case, just pray on your own!" +
				"</p> <p>Not sure what to pray about, or how to pray? I've attached a list of prayer " + 
				"requests that you can go through. Another thought is to recall back to some of the " +
				"lessons that Sajjan has preached on prayer, and seek to put those into practice. " +
				"Alternatively, you can try using the method of prayer that " +
				"<a href=\"http://www.jwipn.com/pdf/thehourthatchangestheworld.pdf\">" +
				"this link</a> recommends, courtesy of Tina Macy." +
				"</p> <p>Thanks again!</p> <p>For the King,</p> <p>Tim A.</p>" + 
				pd.printPrayerDayToHTML();
		
		try 
		{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(this.sender));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(strArrayToCommaDelimmitedString(recipients)));

			// Set Subject: header field
			message.setSubject("Prayer Chain Reminder");
			
			//Create the message part, and set the actual message
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(messageText, "utf-8", "html");
			
			//Create a multipart message and add the message part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			
			//create and add an attachment
			messageBodyPart = new MimeBodyPart();
			String filename = "C:/code/prayerChain/res/What_to_Pray_Concerning_Your_Church.pdf";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
			
			//send the complete message parts
			message.setContent(multipart);
			
			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} 
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
	}
	
	public String strArrayToCommaDelimmitedString(String[] array)
	{
		String result = "";
		for(int i=0; i<array.length; i++)
		{
			if(i == array.length-1) 
				result = result + array[i];
			else
				result = result + array[i] + ",";
		}
		return result;
	}
	
	//gets a use-able String[] of email addresses of everyone who is praying
	//on a particular day
	public String[] getRecipients(PrayerDay pd)
	{
		String[] recipients = new String[49]; //48 time slots + 1 confirmation email for developer
		String[] emailsOfTimeSlots = pd.getEmails();
		int i=0;
		int j=0;
		while(i<emailsOfTimeSlots.length && j<recipients.length)
		{
			if(emailsOfTimeSlots[i]==null || emailsOfTimeSlots[i].equals("") || emailsOfTimeSlots[i].equals("TEXT"))
			{
				i++;
				continue;
			}	
			else
			{
				recipients[j] = emailsOfTimeSlots[i];
				i++;
				j++;
			}
		}
		recipients[j] = "tbadams@umass.edu";
		j++;
		
		String[] recipientsWithNoNull = new String[j];
		for(int k=0; k<recipientsWithNoNull.length; k++)
		{
			recipientsWithNoNull[k] = recipients[k];
		}
		
		//ensure we don't send any person multiple emails
		Set<String> uniqueRecipients = new HashSet<String>();
		i=0;
		while(i<recipientsWithNoNull.length)
		{
			uniqueRecipients.add(recipientsWithNoNull[i]);
			i++;
		}
		
		return uniqueRecipients.toArray(new String[0]);
	}

	public static void main(String[] args) 
	{
		Emailer obj = new Emailer("smtp.gmail.com", "tbadams45@gmail.com", "tbadams45", "UntoTheeOLord45");
		String[] to = new String[2];
		to[0] = "tbadams@umass.edu";
		to[1] = "tbadams45@gmail.com";
		
		obj.setRecipients(to);
		Session ses = obj.getSession();
		//obj.sendMessage(ses);
	}
}