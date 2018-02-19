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
	private int cid;
	private String cNum;
	private String cName;
	private String campus;
	private double credits;
	private String level;
	private ArrayList<String> days = new ArrayList<String>();
	private ArrayList<String> meetTime = new ArrayList<String>();
	private ArrayList<String> location = new ArrayList<String>();
	private ArrayList<String> startDate = new ArrayList<String>();
	private ArrayList<String> endDate = new ArrayList<String>();
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
	public Course(int cid, String cNum, String cName, String campus, double credits, String level, ArrayList<String> days,
			ArrayList<String> meetTime, ArrayList<String> location, ArrayList<String> startDate, ArrayList<String> endDate,
			String professor) {
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
	public void loadCourse(ArrayList<String> list) {
		this.cid = Integer.parseInt(list.get(0));
		this.cNum = list.get(1);
		this.cName = list.get(2);
		this.campus = list.get(3);
		this.credits = Double.parseDouble(list.get(4));
		this.level = list.get(5);
		
		for (int i = 0; i < list.size(); i++) {
			
			if (isMeetingDay(list.get(i))) {
				
				if (this.days.get(0)=="") {
					this.days.set(0, list.get(i));
					this.meetTime.set(0, list.get(i+1));
					this.location.set(0, list.get(i+2));
					this.professor = list.get(i+3);
					this.startDate.set(0, list.get(i-2));
					this.endDate.set(0, list.get(i-1));
				}
				
				else {
					
					this.days.add(list.get(i));
					this.meetTime.add(list.get(i+1));
					this.location.add(list.get(i+2));
					this.professor = list.get(i+3);
					this.startDate.add(list.get(i-2));
					this.endDate.add(list.get(i-1));
				}
			}
			
			
		}
		
	}
	
	private boolean isMeetingDay(String s) {
		
		Pattern p = Pattern.compile("\\b[MTWRF]{1,5}\\b");
		
		if (p.matcher(s).matches()) {
			return true;
		}
		return false;
	}
	
	
	
	
	
	public String getCNum() {
		return this.cNum;
	}
	
	
	public String getCName() {
		return this.cName;
	}
	
	public ArrayList<String> getLocation() {
		return this.location;
	}
	
	
	public ArrayList<String> getDays() {
		return this.days;
	}
	
	public ArrayList<String> getMeetTimes() {
		return this.meetTime;
	}
	
	public ArrayList<String> getStartDays() {
		return this.startDate;
	}

	/**
	 * Overrides the built in toString method to print the class variables.
	 */
	public String toString() {
		return "Class: " + this.cid + "\ncNum: " + cNum + "\ncName: " + cName + "\nCampus: " + campus + "\nCredits: "
				+ credits + "\nLevel: " + level + "\nTime: " + meetTime + "\nDays: " + days + "\nLocation: " + location
				+ "\nStart Date: " + startDate + "\nEnd Date: " + endDate + "\nProf: " + professor + "\n---------------------------\n";
	}

	public static void main(String[] args) {

	}

}
