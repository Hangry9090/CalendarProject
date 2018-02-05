import java.util.ArrayList;

/**
 * 
 */

/**
 * @author jacobwalton
 *
 */
public class Course {
	private int cid;
	private String cNum;
	private String cName;
	private String campus;
	private double credits;
	private String level;
	private String days;
	private String meetTime;
	private String location;
	private String professor;
	
	/**
	 * Empty Constructor.
	 */
	public Course(){
		cid = 0;
		cNum = "";
		cName = "";
		campus = "";
		credits = 0.0;
		level = "";
		days = "";
		meetTime = "";
		location = "";
		professor = "";
	}
	
	/**
	 * Constructor to fill all data values.
	 * 
	 */
	public Course(int cid, String cNum, String cName, String campus, double credits,
			String level, String days, String meetTime, String location, String professor){
		this.cid = cid;
		this.cNum =cNum;
		this.cName = cName;
		this.campus = campus;
		this.credits = credits;
		this.level = level;
		this.days = days;
		this.meetTime = meetTime;
		this.location = location;
		this.professor = professor;
	}
	
	
	
	public void loadCourse(ArrayList<String> list) {
		this.cid = Integer.parseInt(list.get(0));
		this.cNum = list.get(1);
		this.cName = list.get(2);
		this.campus = list.get(3);
		this.credits = Double.parseDouble(list.get(4));
		this.level = list.get(5);
		this.days = list.get(8);
		this.meetTime = list.get(9);
		this.location = list.get(10);
		this.professor = list.get(11);	
	}
	
	public String toString() {
		return "Class: " + this.cid + "\ncNum: " + cNum + "\ncName: " + cName +
				"\nCampus: " + campus + "\nCredits: " + credits + "\nLevel: " +
				level + "\nTime: " + meetTime + "\nDays: " + days + "\nLocation: " + location +
				"\nProf: " + professor + "\n---------------------------\n";
	}
	
	public static void main(String[] args) {
		
	}

}
