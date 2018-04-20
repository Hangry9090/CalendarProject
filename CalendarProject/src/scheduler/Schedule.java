package scheduler;

import java.util.ArrayList;

/**
 * Title: Schedule Description: Class to hold information about a schedule. Main
 * data structure is an ArrayList of Courses.
 * 
 * @author Jacob Walton
 *
 **/
public class Schedule {

	/**
	 * courseList - Main data structure to store a bunch of courses.
	 */
	private ArrayList<Course> courseList;

	/**
	 * Constructor for the class.
	 * 
	 * @param list List of courses.
	 */
	public Schedule(ArrayList<Course> list) {
		this.courseList = list;
	}

	/**
	 * Empty constructor.
	 */
	public Schedule() {
		courseList = new ArrayList<Course>();
	}

	/**
	 * Getter for courseList.
	 *
	 * @return The courseList.
	 */
	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	/**
	 * Appends a course to the course list.
	 * 
	 * @param c Course
	 */
	public void appendCourseList(Course c) {
		this.courseList.add(c);
	}

}
