package prayerchain.parser;

import java.util.ArrayList;
import java.util.Iterator;

public class PrayerChain 
{
	private ArrayList<PrayerDay> prayerDays;
	public PrayerChain(int numPrayerDays)
	{
		prayerDays = new ArrayList<PrayerDay>();
	}
	
	public void addPrayerDay(PrayerDay pd)
	{
		prayerDays.add(pd);
	}
	
	public PrayerDay getPrayerDay(int index)
	{
		return prayerDays.get(index);
	}
	
	public void printPrayerChain()
	{
		Iterator<PrayerDay> it = prayerDays.iterator();
		while(it.hasNext())
		{
			it.next().printPrayerDay();
		}
	}
	
	
	
}

