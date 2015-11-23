package prayerchain.parser;

public class PrayerDay {
	String date;
	TimeSlot[] timeSlots;
	
	public PrayerDay()
	{
		timeSlots = new TimeSlot[48];
		for(int i=0; i<48; i++)
		{
			timeSlots[i] = new TimeSlot();
		}
	}
	
	public TimeSlot getTimeSlot(int index)
	{
		return timeSlots[index];
	}
	
	//if no one has signed up, these may be null
	public String[] getEmails()
	{
		String[] emails = new String[48];
		for(int i=0; i<timeSlots.length; i++)
		{
			emails[i] = timeSlots[i].getEmail();
		}
		return emails;
	}
	
	public void printPrayerDay()
	{
		System.out.println("This is a prayer day: \n\n");
		for(int i=0; i<timeSlots.length; i++)
		{
			timeSlots[i].printTimeSlot();
		}
	}
	
	public String printPrayerDayToHTML()
	{
		String prayerDayHTML = "------------ " + date + " ------------<br>";
		for(int i=0; i<timeSlots.length; i++)
		{
			prayerDayHTML += timeSlots[i].printTimeSlotToHTML();
		}
		prayerDayHTML += "------------------------------------<br>";
		return prayerDayHTML;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public void setDate(String d)
	{
		this.date = d;
	}
	
	protected class TimeSlot 
	{
		protected String beginningTime;
		protected String name;
		protected String phoneNumber;
		protected String email;
		
		public TimeSlot()
		{
			this.beginningTime = "NO TIME SET";
			this.name = "NO NAME SET";
			this.phoneNumber = "NO NUMBER SET";
			this.email = "NO EMAIL SET";
		}
		
		public TimeSlot(String beginTime, String name, String phoneNumber, String email)
		{
			this.beginningTime = beginTime;
			this.name = name;
			this.phoneNumber = phoneNumber;
			this.email = email;
		}
		
		public void printTimeSlot()
		{
			System.out.println("Time: " + this.beginningTime + " Name: " + this.name 
					+ " Phone Number: " + this.phoneNumber + " Email: " + this.email + "<br>");
		}
		
		public String printTimeSlotToHTML()
		{
			String timeSlot = ("Start time: " + this.beginningTime + " || Name: " + this.name
					+ " || Phone Number: " + this.phoneNumber + " || Email: " + this.email + "<br>");
			return timeSlot;
		}
		
		public String getBeginningTime() {
			return beginningTime;
		}

		public void setBeginningTime(String beginningTime) {
			this.beginningTime = beginningTime.trim();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name.trim();
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber.trim();
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email.trim();
		}

	}
}
