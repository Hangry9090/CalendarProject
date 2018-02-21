
/**
 * Name: GVSU Scheduler
 * Description: Takes a schedule from GVSU's banner website,
 * and creates a .ics file to be imported into other
 * calendar apps.
 * 
 * @authors Marshal Brummel, Alan Sisouphone, Jake Walton
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author jacobwalton
 *
 */
public class Scheduler {

	private ArrayList<String> mySchedule = new ArrayList<String>();
	private ArrayList<ArrayList<String>> classes = new ArrayList<ArrayList<String>>();
	private ArrayList<Course> courseList = new ArrayList<Course>();

	/**
	 * Constructor.
	 */
	public Scheduler() {

	}

	/**
	 * This function parses a file.
	 * 
	 * @param fileName
	 *            Name of file to be parsed.
	 * @throws IOException
	 *             When the filename doesn't exist.
	 * @return The arrayList containing parsed HTML.
	 */
	public ArrayList<String> parseFile(final String fileName) throws IOException {
		ArrayList<String> mySchedule = new ArrayList<String>();

		File input = new File(fileName);

		// Creates the schedule html into a Jsoup document object
		Document doc = Jsoup.parse(input, "UTF-8");

		// All elements inside the datadisplaytable are stored in a
		// Jsoup elements object
		Elements schedTable = doc.getElementsByClass("datadisplaytable");

		// It will now separate the rows and columns of the table
		Elements rows = schedTable.select("tr");

		for (Element row : rows) {

			Elements cells = row.select("td");
			for (Element cell : cells) {
				if (!(cell.text().isEmpty())) {
					mySchedule.add(cell.text());

				}
			}

		}
		return mySchedule;

	}

	/**
	 * Open and read a file, and return the lines in the file as a list of Strings.
	 * (Demonstrates Java FileReader, BufferedReader, and Java5.)
	 * 
	 * @param filename
	 *            The name of the file to be parsed.
	 * @return records The arrayList containing the parsed schedule.
	 */
	private ArrayList<String> readFile(final String filename) {
		ArrayList<String> records = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				records.add(line);
			}
			reader.close();
			return records;
		} catch (Exception e) {
			System.err.format("Exception occurred " + "trying to read '%s'.", filename);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This function extracts class information from a schedule string list and
	 * separates the classes into a list of lists.
	 * 
	 * @param schedule
	 *            List of strings extracted from the banner schedule HTML
	 * @return classes An arraylist of arraylists consisting of classes from the
	 *         schedule.
	 */
	private ArrayList<ArrayList<String>> extractClasses(final ArrayList<String> schedule) {

		ArrayList<ArrayList<String>> classes = new ArrayList<ArrayList<String>>();

		int classCount = 0;
		boolean start = false;
		for (int i = 0; i < schedule.size(); i++) {

			// Find a string that's of length 5 "32636" and where the line after is of
			// length 10 or 11 "CIS 290 02"
			if (schedule.get(i).length() == 5 && schedule.get(i + 1).length() > 9
					&& schedule.get(i + 1).length() < 12) {

				// Start adding classes from this line
				classes.add(new ArrayList<String>());

				// Add to class count (This is not keeping track of the number of classes)
				classCount++;

				// Add the line from the schedule to the class arrayList
				classes.get(classCount - 1).add(schedule.get(i));

				// Start adding classes
				start = true;
			} else if (schedule.get(i).contains("Total Credits:")) {
				// Stop adding classes once it finds "Total Credits"
				start = false;
			} else if (start) {
				// Keep adding lines to the class list as long as start is true
				classes.get(classCount - 1).add(schedule.get(i));
			}

		}
		start = false;

		return classes;

	}


	/** Finds the year portion of a date e.g. "Jan 08, 2018"
	 * @param date
	 * 			A date formatted as "MMM DD, YYYY"
	 * 
	 * @return The year portion of a date e.g "2018"
	 */
	public String getYear(final String date) {
		String tdate = date;

		tdate = tdate.replace(",", "");

		String dateSplit[] = tdate.split(" ");

		return dateSplit[2];
	}

	/** Finds the month portion of a date e.g. "Jan 08, 2018"
	 *  and converts it into a number format e.g. "01"
	 * @param date
	 * 			A date formatted as "MMM DD, YYYY"
	 * 
	 * @return The month portion of a date e.g "01"
	 */
	public String getMonth(final String s) throws ParseException {
		String date = s;

		date = date.replace(",", "");

		String dateSplit[] = date.split(" ");

		String month = dateSplit[0];

		// Converts "Jan" to "01" for example
		Date d = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int mon = cal.get(Calendar.MONTH) + 1;

		if (mon < 10) {
			return "0" + Integer.toString(mon);
		}

		return Integer.toString(mon);

	}

	/** Finds the day portion of a date e.g. "Jan 08, 2018"
	 * @param date
	 * 			A date formatted as "MMM DD, YYYY"
	 * 
	 * @return The days portion of a date e.g "08"
	 */
	public String getDay(final String s) {
		String date = s;

		date = date.replace(",", "");

		String dateSplit[] = date.split(" ");

		return dateSplit[1];
	}

	/** Finds the starting date for class in a semester.
	 *  A course with a Jan 8 start date whose course days are
	 *  TR will need to start on Jan 9, not Jan 8.
	 * @param startDate
	 * 			The starting date of a course e.g. "Jan 8, 2018"
	 * @param courseDay
	 * 			The days the course will be held on "MWF"
	 * @return The starting day of a course e.g. "09"
	 */
	public String startDate(final String startDate, final String courseDay) {

		String input = startDate;

		input = input.replace(",", "");

		String inputSplit[] = input.split(" ");

		int startDay = Integer.parseInt(inputSplit[1]);

		if (courseDay.contains("M")) {
			return Integer.toString(startDay);
		}
		if (courseDay.contains("T")) {
			return Integer.toString(startDay + 1);
		}
		if (courseDay.contains("W")) {
			return Integer.toString(startDay + 2);
		}
		if (courseDay.contains("R")) {
			return Integer.toString(startDay + 3);
		}
		if (courseDay.contains("F")) {
			return Integer.toString(startDay + 4);
		}

		return "Error";
	}
	
	

	/**
	 * Finds the hour portion of a course start or end time.
	 * 
	 * @param courseTime
	 * 			The start and end time of a course "11:00 pm - 11:50 pm"
	 * @param index
	 * 			0 indicates to return the hour of the startTime and 1
	 * indicates to return the hour of the endTime
	 * 
	 * @return The hour portion of start or end time of a course
	 */
	public String courseHour(final String courseTime, final int index) {
		String startAndEnd[];
		String splitHourMins[];
		int currHour;

		startAndEnd = courseTime.split(" - ");

		if (startAndEnd[index].contains("am")) {
			startAndEnd[index] = startAndEnd[index].replace(" am", "");
			splitHourMins = startAndEnd[index].split(":");

			if (splitHourMins[0].length() == 1) {
				return ("0" + splitHourMins[0]);
			}

			return (splitHourMins[0]);
		} else if (startAndEnd[index].contains("pm")) {
			startAndEnd[index] = startAndEnd[index].replace(" pm", "");

			splitHourMins = startAndEnd[index].split(":");

			currHour = Integer.parseInt(splitHourMins[0]);

			if (Integer.parseInt(splitHourMins[0]) != 12) {
				currHour += 12;
			}

			return Integer.toString(currHour);

		}

		return "";

	}
	
	
	/**
	 * Finds the minutes portion of a course start or end time
	 * 
	 * @param couseTime
	 *           The start and end time of a course "11:00 pm - 11:50 pm"
	 * @param index
	 * 			0 indicates to return the hour of the startTime and 1
	 * indicates to return the hour of the endTime
	 * @return The minutes portion of start or end time of a course
	 */
	public String courseMin(final String courseTime, final int index) {
		String startAndEnd[];
		String splitHourMins[];

		startAndEnd = courseTime.split(" - ");

		if (startAndEnd[index].contains("am")) {
			startAndEnd[index] = startAndEnd[index].replace(" am", "");
			splitHourMins = startAndEnd[index].split(":");

			return splitHourMins[1];
		} else if (startAndEnd[index].contains("pm")) {
			startAndEnd[index] = startAndEnd[index].replace(" pm", "");

			splitHourMins = startAndEnd[index].split(":");

			return splitHourMins[1];
		}

		return "";

	}
	

	/**
	 * Creates ICS formatted ArrayList based on input course schedule.
	 * @param courses
	 * 			The list of courses from the a student's schedule
	 * @return The students schedule formatted in ICS format in an ArrayList
	 * @throws ParseException
	 */
	public ArrayList<String> printICS(final ArrayList<Course> courses) throws ParseException {

		ArrayList<String> ics = new ArrayList<String>();

		ics.add("BEGIN:VCALENDAR");
		ics.add("VERSION:2.0");

		for (Course c : courses) {

			for (int i = 0; i < c.getDays().size(); i++) {

				String currMeetTimes = c.getMeetTimes().get(i);
				String currCourseDays = c.getDays().get(i);
				String startDate = c.getStartDays().get(i);

				String year = getYear(startDate);
				String month = getMonth(startDate);

				String startTime = courseHour(currMeetTimes, 0) + courseMin(currMeetTimes, 0) + "00";
				String endTime = courseHour(currMeetTimes, 1) + courseMin(currMeetTimes, 1) + "00";

				String day;

				// Hybrid classes often have different starting dates
				if (c.getStartDays().size() > 1 && !c.getStartDays().get(0).equals(c.getStartDays().get(1))) {
					day = getDay(startDate);
				} else {
					day = startDate(startDate, currCourseDays);
					if (day.length() == 1) {
						day = "0" + day;
					}
				}

				// Debug print statements to ensure values are correct
				System.out.println("===============================");
				System.out.println("Current Course: " + c.getCName());
				System.out.println("Course Time: " + c.getMeetTimes().get(i));
				System.out.println("Start Time: " + startTime);
				System.out.println("End Time: " + endTime);
				System.out.println("Course Days: " + currCourseDays);
				System.out.println("Starting Month: " + month);
				System.out.println("Starting Day: " + day);
				System.out.println("Starting Year: " + year);
				System.out.println("===============================");

				ics.add("BEGIN:VEVENT");
				ics.add("DTSTART;TZID=America/Detroit:" + year + month + day + "T" + startTime);
				ics.add("DTEND;TZID=America/Detroit:" + year + month + day + "T" + endTime);

				String repeated = "RRULE:FREQ=WEEKLY;UNTIL=20180422T035959Z;INTERVAL=1;";
				String classFreq = "BYDAY=";

				// This is to repeat classes on certain days based on "MWF"
				if (c.getDays().get(i).length() > 1) {

					if (c.getDays().get(i).contains("M")) {
						classFreq += "MO,";
					}
					if (c.getDays().get(i).contains("T")) {
						classFreq += "TU,";
					}
					if (c.getDays().get(i).contains("W")) {
						classFreq += "WE,";
					}
					if (c.getDays().get(i).contains("R")) {
						classFreq += "TH,";
					}
					if (c.getDays().get(i).contains("F")) {
						classFreq += "FR,";
					}

					classFreq = classFreq.substring(0, classFreq.length() - 1);

					ics.add(repeated + classFreq);

				} else if (c.getStartDays().size() == 1) {
					ics.add(repeated);
				} else if (c.getStartDays().get(0).equals(c.getStartDays().get(1))) {
					ics.add(repeated);
				} else {
					ics.add("RRULE:UNTIL=20180422T035959Z");
				}

				ics.add("SUMMARY:" + c.getCName());
				ics.add("DESCRIPTION:" + c.getCNum() + "\\n" + c.getLocation().get(i));
				ics.add("END:VEVENT");

			}

		}

		ics.add("END:VCALENDAR");

		for (String course : ics) {
			System.out.println(course);
		}

		return ics;

	}

	/**
	 * Main function for testing.
	 * 
	 * @param args
	 *            Command Line input. Unused.
	 * @throws IOException
	 *             If wrong file?
	 * @throws ParseException
	 */
	public static void main(final String[] args) {

	}

	/**
	 * Function that loads a file and creates course objects for each class.
	 * 
	 * @param fileName
	 *            The name of the file (absolute path) to be loaded.
	 */
	public void inputFile(final String fileName) throws IOException {

		mySchedule = parseFile(fileName);

		classes = extractClasses(mySchedule);

		for (String s : mySchedule) {
			System.out.println(s);
		}

		System.out.println();

		for (ArrayList<String> str : classes) {
			Course course = new Course();
			course.loadCourse(str);
			courseList.add(course);
			System.out.println(course.toString());
		}

	}

	/**
	 * Function that creates the .ics file containing the loaded data from the HTML
	 * file.
	 * 
	 * @param fileName
	 *            The name of the file (absolute path) to be created.
	 * @throws IOException
	 * @throws ParseException
	 * @throws NoSuchFieldException
	 * @return Used for error handling. 0 is success.
	 */
	public int outputFile(final String fileName) throws IOException, ParseException, NoSuchFieldException {
		ArrayList<String> ics = printICS(courseList);

		if (courseList.size() < 6) {
			System.out.println("No file currenly loaded.");
			throw new NoSuchFieldException();
		} else {

			FileWriter writer = new FileWriter(fileName);
			for (String str : ics) {
				writer.write(str + "\n");
			}
			writer.close();
			return 0;
		}

	}

}
