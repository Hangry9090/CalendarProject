
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
import java.io.IOException;
//import java.net.SocketException;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoField;
import java.util.ArrayList;
//import java.util.GregorianCalendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import net.fortuna.ical4j.model.*;
//import net.fortuna.ical4j.model.component.VEvent;
//import net.fortuna.ical4j.model.component.VTimeZone;
//import net.fortuna.ical4j.model.parameter.Cn;
//import net.fortuna.ical4j.model.parameter.Role;
//import net.fortuna.ical4j.model.property.Attendee;
//import net.fortuna.ical4j.model.property.CalScale;
//import net.fortuna.ical4j.model.property.ProdId;
//import net.fortuna.ical4j.model.property.Uid;
//import net.fortuna.ical4j.model.property.Version;
//import net.fortuna.ical4j.util.UidGenerator;

/**
 * @author jacobwalton
 *
 */
public class Scheduler {

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
	public ArrayList<String> parseFile(String fileName) throws IOException {
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
	 * separates the classes into a list of lists
	 * 
	 * @param schedule
	 *            List of strings extracted from the banner schedule HTML
	 * @return classes An arraylist of arraylists consisting of classes from the
	 *         schedule.
	 */
	private ArrayList<ArrayList<String>> extractClasses(ArrayList<String> schedule) {

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
			}

			// Stop adding classes once it finds "Total Credits"
			else if (schedule.get(i).contains("Total Credits:")) {
				start = false;
			}

			// Keep adding lines to the class list as long as start is true
			else if (start) {
				classes.get(classCount - 1).add(schedule.get(i));
			}

		}
		start = false;

		return classes;

	}

	public String courseMin(String s, int index) {
		String startAndEnd[];
		String splitHourMins[];

		startAndEnd = s.split(" - ");

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


	public String startDate(String courseDays) {
		int startDay = 8;
		
		
		

		if (courseDays.contains("M")) {
			return "08";
		}
		if (courseDays.contains("T")) {
			return "09";
		}
		if (courseDays.contains("W")) {
			return "10";
		}
		if (courseDays.contains("R")) {
			return "11";
		}
		if (courseDays.contains("F")) {
			return "12";
		}
		
		return "";
	}


	
	
	public String courseHour(String s, int index) {
		String startAndEnd[];
		String splitHourMins[];
		int currHour;

		startAndEnd = s.split(" - ");

		if (startAndEnd[index].contains("am")) {
			startAndEnd[index] = startAndEnd[index].replace(" am", "");
			splitHourMins = startAndEnd[index].split(":");
			
			if (splitHourMins[0].length()==1) {
				return ("0" + splitHourMins[0]);
			}
			
			return (splitHourMins[0]);
		}

		else if (startAndEnd[index].contains("pm")) {
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
	
	
	public void printICS(ArrayList<Course> courses) {
		
		ArrayList<String> ics = new ArrayList<String>();
		
		ics.add("BEGIN:VCALENDAR");
		ics.add("VERSION:2.0");
		
		String year = "2018";
		String month = "01";
		
		for (Course c: courses) {
			
			

			
			for (int i = 0; i < c.getDays().size(); i++) {
				
				String currMeetTimes = c.getMeetTimes().get(i);
				String currCourseDays = c.getDays().get(i);
				
				
				
				String startTime = courseHour(currMeetTimes, 0)
						+ courseMin(currMeetTimes, 0)
						+ "00";
				String endTime = courseHour(currMeetTimes, 1)
						+ courseMin(currMeetTimes, 1)
						+ "00";
				
				String day = startDate(currCourseDays);
				
				
				
				
				
//				System.out.println("===============================");
//				System.out.println("Current Course: " + c.getCName());
//				System.out.println("Course Time: " + c.getMeetTimes().get(i));
//				System.out.println("Start Time: " + startTime);
//				System.out.println("End Time: " + endTime);
//				System.out.println("Starting Day: " + day);
//				System.out.println("===============================");
				
				ics.add("BEGIN:VEVENT");
				ics.add("DTSTART;TZID=America/Detroit:" + year + month + day + "T" + startTime);
				ics.add("DTEND;TZID=America/Detroit:" + year + month + day + "T" + endTime);
				
				String repeated = "RRULE:FREQ=WEEKLY;UNTIL=20180422T035959Z;INTERVAL=1;";
				String classFreq = "BYDAY=";
				
				
				
				
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
					
					classFreq  = classFreq.substring(0, classFreq.length()-1);
					
					
					
					
					
					ics.add(repeated + classFreq);
					
					
				}
				else {
					ics.add(repeated);
				}
				
				
				
				ics.add("SUMMARY:" + c.getCName());
				ics.add("DESCRIPTION:" + c.getCNum() + "\\n" + c.getLocation().get(i));
				ics.add("END:VEVENT");
			}
			
			
			
		}
		
		ics.add("END:VCALENDAR");
		
		
		for (String course: ics) {
			System.out.println(course);
		}
		
		
		
		
		
	}
	
	
	

	/**
	 * Main function to parse file.
	 * 
	 * @param args
	 *            Command Line input. Unused.
	 * @throws IOException
	 *             If wrong file?
	 */
	public static void main(final String[] args) throws IOException {
		Scheduler schedule = new Scheduler();
		ArrayList<String> mySchedule = new ArrayList<String>();

		ArrayList<ArrayList<String>> classes = new ArrayList<ArrayList<String>>();

		mySchedule = schedule.parseFile("CSS-Marshal.html");

		classes = schedule.extractClasses(mySchedule);

		// for (String s: mySchedule) {
		// System.out.println(s);
		// }

		ArrayList<Course> courseList = new ArrayList<Course>();

		for (ArrayList<String> str : classes) {
			Course course = new Course();
			course.loadCourse(str);
			courseList.add(course);
			System.out.println(course.toString());
		}

		schedule.printICS(courseList);
		
		

	}

}
