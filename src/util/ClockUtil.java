package util;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ClockUtil
{
	private static MutableDateTime LOCAL = MutableDateTime.now();
	public static final DateTimeFormatter FULL = DateTimeFormat.forPattern("dd-MM-YY|HH:mm:ss");
	
	
	
	
	public static final long SECOND = 1000;
	public static final long MINUTE = SECOND * 60;
	public static final long HOUR = MINUTE * 60;
	public static final long HALF_DAY = HOUR * 12;
	public static final long DAY = HOUR * 24;
	public static final long WEEK = DAY * 7;
	
	
	public static long getMinutes(int minutes) {return MINUTE * minutes;}
	public static long getHours(int hours) {return HOUR * hours;}
	public static long getDays(int days) {return DAY * days;}
	
	public MutableDateTime getNextDay(int hour, int min, int second, int millis) {
	
		MutableDateTime time = MutableDateTime.now();
		long now = time.getMillis();
		time.setTime(hour, min, second, millis);
		if(time.getMillis() < now)
			time.addDays(1);
		return time;
	}
	public static MutableDateTime getNextMinute() {
		MutableDateTime time = MutableDateTime.now();
		long now = time.getMillis();
		time.setTime(time.getHourOfDay(), time.getMinuteOfHour(), 0, 0);
		if(time.getMillis() < now)
			time.addMinutes(1);
		return time;
	}
	
	
	public static String getTimestamp() {
		String stamp = null;
		synchronized (LOCAL) {
			LOCAL.setMillis(System.currentTimeMillis());
			stamp = LOCAL.toString(FULL);
		}
		return stamp;
	}
	public static long getSeconds(int seconds) {
		return SECOND * seconds;
	}
}
