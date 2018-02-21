import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * 
 */

/**
 * @author jacobwalton
 *
 */
public class Course {

	// class variables
	/**
	 * cid The course ID.
	 */
	private int cid;

	/**
	 * cNum The course number.
	 */
	private String cNum;

	/**
	 * cName The course name.
	 */
	private String cName;

	/**
	 * campus The campus on which the course is located.
	 */
	private String campus;

	/**
	 * credits The # of credits the course is.
	 */
	private double credits;

	/**
	 * level Undergrad or Graduate.
	 */
	private String level;

	/**
	 * days The ArrayList of all the days the course occurs.
	 */
	private ArrayList<String> days = new ArrayList<String>();

	/**
	 * meetTime The ArrayList of all the times the course occurs.
	 */
	private ArrayList<String> meetTime = new ArrayList<String>();

	/**
	 * location The ArrayList of all the locations the course occurs.
	 */
	private ArrayList<String> location = new ArrayList<String>();

	/**
	 * startDate The ArrayList of all the start dates.
	 */
	private ArrayList<String> startDate = new ArrayList<String>();

	/**
	 * endDate The ArrayList of all the end dates.
	 */
	private ArrayList<String> endDate = new ArrayList<String>();

	/**
	 * professor The professor for the course.
	 */
	private String professor;

	/**
	 * Empty Constructor.
	 */
	public Course() {
		cid = 0;
		cNum = "";
		cName = "";
		campus = "";
		credits = 0.0;
		level = "";
		days.add("");
		meetTime.add("");
		location.add("");
		startDate.add("");
		endDate.add("");
		professor = "";
	}

	/**
	 * Constructor to fill all data values.
	 * 
	 */
	/**
	 * @param cid Course ID.
	 * @param cNum Course Number.
	 * @param cName Course Name.
	 * @param campus Campus.
	 * @param credits Credits.
	 * @param level Grad/Undergrad.
	 * @param days Meet days.
	 * @param meetTime Meet times.
	 * @param location Meet location.
	 * @param startDate Start dates.
	 * @param endDate End dates.
	 * @param professor Professor.
	 */
	public Course(final int cid, final String cNum, final String cName, final String campus, final double credits,
			final String level, final ArrayList<String> days, final ArrayList<String> meetTime,
			final ArrayList<String> location, final ArrayList<String> startDate, final ArrayList<String> endDate,
			final String professor) {
		this.cid = cid;
		this.cNum = cNum;
		this.cName = cName;
		this.campus = campus;
		this.credits = credits;
		this.level = level;
		this.days = days;
		this.meetTime = meetTime;
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
		this.professor = professor;
	}

	/**
	 * loadCourse Parses the ArrayList input list into the class variables.
	 * 
	 * @param list
	 *            ArrayList of Strings of course info to be parsed.
	 */
	public void loadCourse(final ArrayList<String> list) {
		this.cid = Integer.parseInt(list.get(0));
		this.cNum = list.get(1);
		this.cName = list.get(2);
		this.campus = list.get(3);
		this.credits = Double.parseDouble(list.get(4));
		this.level = list.get(5);

		for (int i = 0; i < list.size(); i++) {

			if (isMeetingDay(list.get(i))) {

				if (this.days.get(0).equals("")) {
					this.days.set(0, list.get(i));
					this.meetTime.set(0, list.get(i + 1));
					this.location.set(0, list.get(i + 2));
					this.professor = list.get(i + 3);
					this.startDate.set(0, list.get(i - 2));
					this.endDate.set(0, list.get(i - 1));
				} else {

					this.days.add(list.get(i));
					this.meetTime.add(list.get(i + 1));
					this.location.add(list.get(i + 2));
					this.professor = list.get(i + 3);
					this.startDate.add(list.get(i - 2));
					this.endDate.add(list.get(i - 1));
				}
			}

		}

	}

	/**
	 * @param s The string to check if it is a meeting day.
	 * @return whether or not it is a meeting day
	 */
	private boolean isMeetingDay(final String s) {

		Pattern p = Pattern.compile("\\b[MTWRF]{1,5}\\b");

		if (p.matcher(s).matches()) {
			return true;
		}
		return false;
	}

	/**
	 * Getter for cNum.
	 * @return cNum The course Number.
	 */
	public String getCNum() {
		return this.cNum;
	}

	/**
	 * Getter for cName.
	 * @return cName The course name.
	 */
	public String getCName() {
		return this.cName;
	}

	/**
	 * Getter for location.
	 * @return location The course location.
	 */
	public ArrayList<String> getLocation() {
		return this.location;
	}

	/**
	 * Getter for days.
	 * @return days Days the course meets.
	 */
	public ArrayList<String> getDays() {
		return this.days;
	}

	/**
	 * Getter for meetTime.
	 * @return meetTime The times the course meets.
	 */
	public ArrayList<String> getMeetTimes() {
		return this.meetTime;
	}

	/**
	 * Getter for startDate.
	 * @return startDate The day the course starts.
	 */
	public ArrayList<String> getStartDays() {
		return this.startDate;
	}
	
	public ArrayList<String>	getEndDays() {
		return this.endDate;
	}

	/**
	 * Overrides the built in toString method to print the class variables.
	 * 
	 * @see java.lang.Object#toString()
	 * @return The string combination of all class values.
	 */
	public String toString() {
		return "Class: " + this.cid + "\ncNum: " + cNum + "\ncName: " + cName + "\nCampus: " + campus + "\nCredits: "
				+ credits + "\nLevel: " + level + "\nTime: " + meetTime + "\nDays: " + days + "\nLocation: " + location
				+ "\nStart Date: " + startDate + "\nEnd Date: " + endDate + "\nProf: " + professor
				+ "\n---------------------------\n";
	}

}
