package scheduler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Helps create a list of calendar events in ICS format.
 * 
 * @author Marshal Brummel
 * @author Alan Sisouphone
 * @author Jake Walton
 * @version 1.0
 */
public class ICSEventBuilder {

	/** Contains calendar events listed in ICS format. */
	private ArrayList<String> ics = new ArrayList<String>();

	/**
	 * Default constructor for ICS schedule.
	 */
	public ICSEventBuilder() {
		ics.add("BEGIN:VCALENDAR");
		ics.add("VERSION:2.0");
	}

	/**
	 * Constructor to create ICS schedule.
	 * 
	 * @param icsEvent
	 *            An input ICS schedule list
	 */
	public ICSEventBuilder(final ArrayList<String> icsEvent) {
		this.ics = icsEvent;
	}

	/**
	 * Inserts the BEGIN statement of an event. All events must start with this.
	 */
	public void beginEvent() {
		this.ics.add("BEGIN:VEVENT");
	}

	/**
	 * Inserts the BEGIN statement of an event. All events must end with this.
	 */
	public void endEvent() {
		this.ics.add("END:VEVENT");
	}

	/**
	 * Inserts the END statement for the calendar. The ICS calendar must end with
	 * this.
	 */
	public void endCalendar() {
		this.ics.add("END:VCALENDAR");
	}

	/**
	 * Sets the start/end date and time of the event based on a course.
	 * 
	 * @param startDate
	 *            The starting date of a course
	 * @param courseTime
	 *            The start and end time of a course
	 * @param courseDays
	 *            The days the course is held on
	 */
	public void setDateStartEnd(final String startDate, final String courseTime, final String courseDays) {

		String cStartDate = toICSDateFormat(startDate, courseDays);

		String cStartEndTime[] = toICSTimeFormat(courseTime);

		String cStartTime = cStartEndTime[0];
		String cEndTime = cStartEndTime[1];

		this.ics.add("DTSTART;TZID=America/Detroit:" + cStartDate + "T" + cStartTime);
		this.ics.add("DTEND;TZID=America/Detroit:" + cStartDate + "T" + cEndTime);
	}

	/**
	 * Sets the rules of the particular event based on the course. This allows for
	 * the repetition of an event/course
	 * 
	 * @param endDate
	 *            The last day of the semester
	 * @param courseDays
	 *            The days the course is held
	 */
	public void setRule(final String endDate, final String courseDays) {

		String rule = "RRULE:";
		String cEndDate = toICSDateFormat(endDate, "");
		cEndDate = "UNTIL=" + cEndDate + "T000000;";

		if (courseDays.equals("")) {
			rule += cEndDate;
		} else if (courseDays.length() == 1) {
			rule += "FREQ=WEEKLY;" + cEndDate + "INTERVAL=1;";

		} else if (courseDays.length() > 1) {

			String classFreq = findClassFreq(courseDays);

			rule += "FREQ=WEEKLY;" + cEndDate + "INTERVAL=1;" + classFreq;
		}

		ics.add(rule);

	}

	/**
	 * Sets the summary of an event. This is the title of the event.
	 * 
	 * @param summary
	 *            The contents of the event summary/title
	 */
	public void setSummary(final String summary) {
		String tSummary = "SUMMARY:";

		tSummary += summary;

		this.ics.add(tSummary);
	}

	/**
	 * Sets the description of an event. This is additional information for an
	 * event.
	 * 
	 * @param desc
	 *            The contents of the description for the event
	 */
	public void setDescription(final String desc) {
		String tDesc = "DESCRIPTION:";

		tDesc += desc;
		this.ics.add(tDesc);
	}

	/**
	 * Finds the frequency a course occurs for the ICS schedule RULE.
	 * 
	 * @param courseDays
	 *            The days of the week the course is held on
	 * @return Day abbreviations that represent the days of the week
	 */
	private String findClassFreq(final String courseDays) {

		String cFreq = "";

		if (courseDays.contains("M")) {
			cFreq += "MO,";
		}
		if (courseDays.contains("T")) {
			cFreq += "TU,";
		}
		if (courseDays.contains("W")) {
			cFreq += "WE,";
		}
		if (courseDays.contains("R")) {
			cFreq += "TH,";
		}
		if (courseDays.contains("F")) {
			cFreq += "FR,";
		}

		cFreq = "BYDAY=" + cFreq.substring(0, cFreq.length() - 1);

		return cFreq;

	}

	/**
	 * Formats a given date into an ICS compatible date.
	 * 
	 * @param date
	 *            A given date formatted as "MMM DD, YYYY" e.g. Jan 08, 2018
	 * @param courseDays
	 *            The days of the week the course is held on
	 * @return A date converted an ICS compatible date format
	 */
	private String toICSDateFormat(final String date, final String courseDays) {

		String tempDate = date.replace(",", "");

		String separatedDate[] = tempDate.split(" ");
		String month = null;
		String day = null;
		String year = null;

		if (separatedDate.length > 0) {

			month = toMonthNum(separatedDate[0]);
			day = separatedDate[1];
			year = separatedDate[2];
			if (!courseDays.equals("")) {
				day = findStartDate(month + "-" + day + "-" + year, courseDays);
			}

		}

		tempDate = year + month + day;

		return tempDate;

	}

	/**
	 * Separates the start and end time of a given course time and formats it into
	 * an ICS compatible time.
	 * 
	 * @param time
	 *            The start and end time of a course
	 * @return An array in which [0] contains the start time of the course and [1]
	 *         contains the end time of the course
	 */
	private String[] toICSTimeFormat(final String time) {

		String startEnd[] = time.split(" - ");

		SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");

		SimpleDateFormat date24Format = new SimpleDateFormat("HHmm");

		try {

			startEnd[0] = date24Format.format(date12Format.parse(startEnd[0])) + "00";
			startEnd[1] = date24Format.format(date12Format.parse(startEnd[1])) + "00";

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return startEnd;
	}

	/**
	 * Courses start on different days depending on their course days. This function
	 * finds the appropriate starting date based on the course days.
	 * 
	 * @param startDate
	 *            The start date of the semester for a course
	 * @param courseDays
	 *            The days of the week the course is held on
	 * @return The date formatted as "DD" e.g. 08 the course will start on
	 */
	private String findStartDate(final String startDate, final String courseDays) {

		String tStartDate;

		SimpleDateFormat format = new SimpleDateFormat("mm-dd-yyyy");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(startDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (courseDays.contains("M")) {
			c.add(Calendar.DATE, 0);
		} else if (courseDays.contains("T")) {
			c.add(Calendar.DATE, 1);
		} else if (courseDays.contains("W")) {
			c.add(Calendar.DATE, 2);
		} else if (courseDays.contains("R")) {
			c.add(Calendar.DATE, 3);
		} else if (courseDays.contains("F")) {
			c.add(Calendar.DATE, 4);
		}

		tStartDate = format.format(c.getTime());
		tStartDate = tStartDate.substring(3, 5);

		return tStartDate;

	}

	/**
	 * Converts a letter formatted month into its appropriate month number.
	 * 
	 * @param month
	 *            A month formatted as "MMM" e.g. Jan
	 * @return A the input month formatted as a number
	 */
	private String toMonthNum(final String month) {

		String numMonth;

		Date date = new Date();
		try {
			Date tempDate;
			tempDate = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
			date = tempDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int mon = cal.get(Calendar.MONTH) + 1;

		numMonth = Integer.toString(mon);

		if (mon < 10) {
			return "0" + numMonth;
		}

		return numMonth;

	}

	/**
	 * Makes an ArrayList of the ICS schedule contents.
	 * 
	 * @return A list of the ICS schedule
	 */
	public ArrayList<String> toList() {
		return this.ics;
	}

	/**
	 * Makes a string of the ICS schedule contents.
	 * 
	 * @return A string of the ICS Schedule
	 */
	public String toString() {

		String output = "";

		StringBuffer buf = new StringBuffer();
		for (String s : this.ics) {
			buf.append(s);
			//output += s + "\n";
		}
		
		output = buf.toString();

		return output;

	}

}
