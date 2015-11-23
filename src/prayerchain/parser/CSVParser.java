package prayerchain.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import com.opencsv.*;
import java.util.Arrays;

public class CSVParser {
	public static void main(String[] args) throws FileNotFoundException, IOException 
	{
		CSVParser obj = new CSVParser();
		List<String[]> list = obj.readAll("C:/code/emailer/prayerchain.csv");
		
		obj.removeWeekAndEmptyRows(list);
		System.out.println("New file: ");
		//obj.printStringArrayList(list);
		PrayerChain myPrayerChain = obj.parsePrayerChain(list);
		//myPrayerChain.printPrayerChain();
		myPrayerChain.getPrayerDay(0).printPrayerDay();
		System.out.println("\n\n\n\n\n");
		System.out.println(myPrayerChain.getPrayerDay(0).printPrayerDayToHTML());
		
		/*List<String[]> subList = list.subList(0, 4);
		System.out.println(list.size());
		obj.printStringArrayList(subList);
		List<String[]> subList2 = list.subList(80, 84);
		obj.printStringArrayList(subList2);*/
		
	}

	public List<String[]> readAll(String fileName) throws IOException, FileNotFoundException
	{
			CSVReader reader = new CSVReader(new FileReader(fileName));
			List<String[]> myEntries = reader.readAll();
			reader.close();
			return myEntries;	
	}
	
	public void removeWeekAndEmptyRows(List<String[]> list)
	{
		Iterator<String[]> it = list.iterator();
		while(it.hasNext())
		{
			String[] temp = it.next();
			if(temp[0].equals("WEEK 1") || temp[0].equals("WEEK 2") 
					|| temp[0].equals("WEEK 3") || temp[0].isEmpty())
			{
				it.remove(); //isn't removing the "WEEK 2" or "WEEK 3" 
			}
		}
	}
	
	public PrayerChain parsePrayerChain(List<String[]> simpleCSV)
	{
		PrayerChain pc = new PrayerChain(21);
		for(int i=0; i<21; i++)
		{
			List<String[]> listOfOneDay = simpleCSV.subList(i*4, (i*4)+4);
			PrayerDay prayerDay = parsePrayerDay(pc, listOfOneDay);
			pc.addPrayerDay(prayerDay);
		}
		return pc;
	}
	
	//gets a sublist which only contains 4 columns: date/time slot, name, phone, email. 
	//Needs to return a prayer day that has all of these set up correctly in the PrayerDay TimeSlots.
	public PrayerDay parsePrayerDay(PrayerChain pc, List<String[]> subList)
	{
		PrayerDay pd = new PrayerDay();
		Iterator<String[]> it = subList.iterator();
		for(int j=0; j<4; j++) // go through the 4 columns
		{
			if(it.hasNext() == false)
			{
				System.out.println("We're out of rows too early");
				return null;
			}
			String[] row = it.next();
			for(int i=0; i<49; i++) //go through the 49 rows (48 time slots, + 1 header)
			{
				if(i==0 && j==0)
				{
					pd.setDate(row[i]);
					continue;
				}
				else if(i==0)
				{
					continue; //we don't want to store the header row other than the date
				}
								
				if(j==0)
				{
					pd.getTimeSlot(i-1).setBeginningTime(row[i]);
				}
				else if(j==1)
				{
					pd.getTimeSlot(i-1).setName(row[i]);
				}
				else if(j==2)
				{
					pd.getTimeSlot(i-1).setPhoneNumber(row[i]);
				}
				else if(j==3)
				{
					pd.getTimeSlot(i-1).setEmail(row[i]);
				}
			}
		}
		return pd;
	}
	
	public void printStringArrayList(List<String[]> list)
	{
		Iterator<String[]> it = list.iterator();
		while(it.hasNext())
		{
			String[] temp = it.next();
			System.out.println(Arrays.toString(temp));
		}
		System.out.println();
	}
}
