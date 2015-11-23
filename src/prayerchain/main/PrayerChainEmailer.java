package prayerchain.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import javax.mail.Session;

import prayerchain.emailer.*;
import prayerchain.parser.*;

public class PrayerChainEmailer {
	public static void main(String args[]) throws FileNotFoundException, IOException
	{
		CSVParser parser = new CSVParser();
		List<String[]> list = parser.readAll("C:/code/prayerChain/res/prayerchain.csv");
		int day = 7; //use 7 on 11/22/15
		
		parser.removeWeekAndEmptyRows(list);
		PrayerChain myPrayerChain = parser.parsePrayerChain(list);
	
		
		ArrayList<String[]> configResults = TextFileScanner.processFile("C:/code/prayerChain/res/config.txt");
		//emailer needs server, sender emailer, sender username, sender password
		Emailer emailer = new Emailer(configResults.get(0)[1], configResults.get(1)[1], configResults.get(2)[1], configResults.get(3)[1]);
		
		String[] to = {"tbadams@umass.edu"};
		//String[] to = emailer.getRecipients(myPrayerChain.getPrayerDay(day));
		System.out.println("Recipients: " + Arrays.toString(to));
		myPrayerChain.getPrayerDay(day).printPrayerDay();
		
		char input = (char)System.in.read();
		if(input == 'n') return;
		emailer.setRecipients(to);
		Session ses = emailer.getSession();
		emailer.sendMessage(ses, myPrayerChain.getPrayerDay(day));
		System.out.println("Sent messsage successfuly to: " + Arrays.toString(to));
	}
}
