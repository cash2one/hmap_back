package hmap.core.util;

import java.util.Calendar;
import java.util.Date;

public class CountDateDiff {
	//日期相差1
	public static int daysOfTwo(Date fDate, Date oDate) {

	       Calendar aCalendar = Calendar.getInstance();

	       aCalendar.setTime(fDate);

	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

	       aCalendar.setTime(oDate);

	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

	       return day2 - day1;

	    }
	//严格的24小时
	public static int daysOfTwo2(Date fDate, Date oDate) {

		Calendar aCalendar = Calendar.getInstance();

		aCalendar.setTime(fDate);

		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

		aCalendar.setTime(oDate);

		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

		return day2 - day1;

	}
	public static int weeksOfTwo(Date fDate, Date oDate) {

		Calendar aCalendar = Calendar.getInstance();

		aCalendar.setTime(fDate);

		int day1 = aCalendar.get(Calendar.WEEK_OF_YEAR);

		aCalendar.setTime(oDate);

		int day2 = aCalendar.get(Calendar.WEEK_OF_YEAR);

		return day2 - day1;

	}
	public static int monthsOfTwo(Date fDate, Date oDate) {

		Calendar aCalendar = Calendar.getInstance();

		aCalendar.setTime(fDate);

		int day1 = aCalendar.get(Calendar.MONTH);

		aCalendar.setTime(oDate);

		int day2 = aCalendar.get(Calendar.MONTH);

		return day2 - day1;

	}

}
