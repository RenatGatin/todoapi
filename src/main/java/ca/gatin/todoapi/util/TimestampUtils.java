package ca.gatin.todoapi.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author RGatin
 * @since 10-Oct-2015
 * 
 *        Methods for dealing with timestamps
 */
public class TimestampUtils {

	/**
	 * Return the combined date and time string for specified date/time
	 * 
	 * @param date
	 *            Date
	 * @return String with format "yyyy-MM-dd'T'HH:mm:ss'Z'"
	 */
	public static String getDateAndTimeStringForDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * Return the combined date and time string for current date/time
	 * 
	 * @return String with format "yyyy-MM-dd'T'HH:mm:ss'Z'"
	 */
	public static String getDateAndTimeStringForCurrentDate() {
		Date now = new Date();
		return getDateAndTimeStringForDate(now);
	}

	/**
	 * Adds time offset to current date
	 * 
	 * @param days
	 * @return
	 */
	public static String createTokenExpireDate(int days) {
		Calendar calendar = new GregorianCalendar(/* remember about timezone! */);
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, days);
		return getDateAndTimeStringForDate(calendar.getTime());
	}

	/**
	 * Private constructor: class cannot be instantiated
	 */
	private TimestampUtils() {
	}
}
