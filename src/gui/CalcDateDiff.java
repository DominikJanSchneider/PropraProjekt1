package gui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalcDateDiff {
	
	// method to calculate the difference between given date and actual date as days count
	public static int date (String dbDate) {
		
		//System.out.println("übergebenes Datum: " + dbDate);
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");		// set date format
		Date dateobj = new Date();
		String bla = df.format(dateobj);
		//System.out.println("aktuelles Datum: " + bla);
		
		Date date1 = null;
		Date date2 = null;
		
		
		// convert date strings to date format
		try {
			date1 = df.parse(dbDate);
			//System.out.println("übergebenes Datum als Date: " + date1);
			date2 = df.parse(bla);
			//System.out.println("aktuelles Datum als Date: " + date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// calculate difference in milliseconds and convert it to days
		long diff = date2.getTime() - date1.getTime();
		//System.out.println(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
		int x = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		
		return x;
	}
	
}
