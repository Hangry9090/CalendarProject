import java.util.ArrayList;

/**
 * 
 */

/**
 * @author jacobwalton
 *
 */
public class Course {

	// class variables
	private int cid;
	private String cNum;
	private String cName;
	private String campus;
	private double credits;
	private String level;
	private ArrayList<String> days = new ArrayList<String>();
	private ArrayList<String> meetTime = new ArrayList<String>();
	private ArrayList<String> location = new ArrayList<String>();
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
		professor = "";
	}

	/**
	 * Constructor to fill all data values.
	 * 
	 */
	public Course(int cid, String cNum, String cName, String campus, double credits, String level, String days[],
			String meetTime[], String location[], String professor) {
		this.cid = cid;
		this.cNum = cNum;
		this.cName = cName;
		this.campus = campus;
		this.credits = credits;
		this.level = level;
		this.days.set(0, days[0]);
		this.meetTime.set(0, meetTime[0]);
		this.location.set(0, location[0]);
		this.professor = professor;
	}

	/**
	 * loadCourse Parses the ArrayList input list into the class variables.
	 * 
	 * @param list
	 *            ArrayList of Strings of course info to be parsed.
	 */
	public void loadCourse(ArrayList<String> list) {
		this.cid = Integer.parseInt(list.get(0));
		this.cNum = list.get(1);
		this.cName = list.get(2);
		this.campus = list.get(3);
		this.credits = Double.parseDouble(list.get(4));
		this.level = list.get(5);
		this.days.set(0, list.get(8));
		this.meetTime.set(0, list.get(9));
		this.location.set(0, list.get(10));
		this.professor = list.get(11);

		// try to add a second meet time/location/day
		try {
			this.days.add(list.get(14));
			this.meetTime.add(list.get(15));
			this.location.add(list.get(16));
		}

		// if there is no second day, do nothing
		catch (Exception e) {
			// System.out.println(e);
		}

		// try to add a third meet time/location/day
		try {
			this.days.add(list.get(20));
			this.meetTime.add(list.get(21));
			this.location.add(list.get(22));
		}

		// if there is no third meet time, do nothing
		catch (Exception e) {
			// System.out.println(e);
		}
	}

	/**
	 * Overrides the built in toString method to print the class variables.
	 */
	public String toString() {
		return "Class: " + this.cid + "\ncNum: " + cNum + "\ncName: " + cName + "\nCampus: " + campus + "\nCredits: "
				+ credits + "\nLevel: " + level + "\nTime: " + meetTime + "\nDays: " + days + "\nLocation: " + location
				+ "\nProf: " + professor + "\n---------------------------\n";
	}

	public static void main(String[] args) {

	}

}
