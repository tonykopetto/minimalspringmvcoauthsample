package com.kopetto.sample.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

/**
 *
 */
public class GlobalDateFormatter implements Formatter<Date> {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");

    private static final SimpleDateFormat sdfa []  = new SimpleDateFormat [] {
        new SimpleDateFormat("MM/dd"),
        new SimpleDateFormat("MM/dd/yyyy"),
        new SimpleDateFormat("MM/yyyy"),
        new SimpleDateFormat("yyyy/MM/dd"),
        new SimpleDateFormat("yyyy/dd/MM")
    };

    public GlobalDateFormatter() {
        super();
    }

    /* (non-Javadoc)
     * @see org.springframework.format.Parser#parse(java.lang.String, java.util.Locale)
     */
    public Date parse(String text, Locale locale) {
        synchronized (SDF) {
            Date date = null;
			try {
				date = SDF.parse(text);
			} catch (ParseException e) {
				//it could be a numeric value
				Long dtLong = new Long (text);
				date=new Date (dtLong);
				
				//hack for calendar dates in data controller
				if (date.getYear() + 1900 <= 1970){
					date=new Date (dtLong * 1000L);
				}
			}
            final Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(date.getTime());
            return cal.getTime();
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.format.Printer#print(java.lang.Object, java.util.Locale)
     */
    public String print(Date date, Locale locale) {
    	return format (date, locale);
    }
    
    public static String format (Date date, Locale locale) {
    	if (date == null)
    		return "";
    	
    	long delta = System.currentTimeMillis() - date.getTime(); //msecs
    	long secs = delta / 1000;
    	
    	String rc = null;
    	if (secs == 0)
    		rc = "Now";
    	else if (secs < 60)
    		rc = "< 1 min ago";
    	else if (secs < 3660)
    		rc = (secs / 60) + "min ago";
    	else if (secs > 3660 && secs < 3660 * 24)
    		rc = (secs / 3660) + "hr ago";
    	else{
            synchronized (SDF) {
            	rc = SDF.format(date);
            }
    	}
    	
    	return rc;
    }
    
	/**
	 * @param date
	 * @return
	 */
	public static Date parseMultiple(String date) {
		Date rc = null;
		for (int i = 0; i < sdfa.length; i++) {
			
			
			try {
				rc = sdfa [i].parse(date);
			} 
			catch (ParseException e) {
			}
			
			if (rc != null)
				break;
		}
		
		return rc;
	}
}