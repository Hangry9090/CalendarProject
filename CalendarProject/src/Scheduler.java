
/**
 * Name: GVSU Scheduler
 * Description: Takes a schedule from GVSU's banner website,
 * and creates a .ics file to be imported into other
 * calendar apps.
 * 
 * @authors Marshal Brummel, Alan Sisouphone, Jake Walton
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

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
	private ICSEventBuilder ics = new ICSEventBuilder();

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

	/**
	 * Checks whether the current course is a hybrid class or not.
	 * 
	 * @param c
	 *            A course to be checked
	 * @return True or False depending on whether the course is hybrid or not
	 */
	private boolean isHybridCourse(final Course c) {

		if (c.getStartDays().size() > 1 && !c.getStartDays().get(0).equals(c.getStartDays().get(1))) {
			return true;
		}
		return false;
	}

	/**
	 * Creates ICS formatted ArrayList based on input course schedule.
	 * 
	 * @param courses
	 *            The list of courses from the a student's schedule
	 * @throws ParseException
	 */
	public void createICS(final ArrayList<Course> courses) throws ParseException {

		for (Course c : courses) {

			for (int i = 0; i < c.getDays().size(); i++) {

				String currMeetTimes = c.getMeetTimes().get(i);
				String currCourseDays = c.getDays().get(i);
				String startDate = c.getStartDays().get(i);
				String endDate = c.getEndDays().get(i);

				ics.beginEvent();
				if (isHybridCourse(c)) {
					ics.setDateStartEnd(startDate, currMeetTimes, "");
					ics.setRule(endDate, "");
				} else {
					ics.setDateStartEnd(startDate, currMeetTimes, currCourseDays);
					ics.setRule(endDate, currCourseDays);
				}

				ics.setSummary(c.getCName());
				ics.setDescription(c.getCNum() + "\\n" + c.getLocation().get(i));
				ics.endEvent();

			}
		}

		ics.endCalendar();
		// System.out.println(ics.toString());
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

		// for (String s : mySchedule) {
		// System.out.println(s);
		// }

		// System.out.println();

		for (ArrayList<String> str : classes) {
			Course course = new Course();
			course.loadCourse(str);
			courseList.add(course);
			// System.out.println(course.toString());
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
		createICS(courseList);

		if (courseList.size() < 1) {
			System.out.println("No file currenly loaded.");
			throw new NoSuchFieldException();
		} else {

			FileWriter writer = new FileWriter(fileName);
			for (String str : ics.toList()) {
				writer.write(str + "\n");
			}
			writer.close();
			return 0;
		}

	}

	/**
	 * Allows the ICS file to be printed.
	 * 
	 * @return The contents of the ICS file
	 */
	public String toStringICS() {

		return this.ics.toString();
	}

	/**
	 * Allows the schedule of courses to be printed.
	 * 
	 * @return The list of courses in the schedule
	 */
	public String toString() {

		String tString = "";

		for (Course c : this.courseList) {
			tString += c.toString() + "\n";
		}

		return tString;
	}

}
