package scheduler;

import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Title: Schedule
 * Description: Class to hold information about a schedule. Main data 
 * structure is an ArrayList of Courses.
 * 
 * @author Jacob Walton
 *
 **/
public class Schedule {
	
	/**
	 * mySchedule The list of strings that makeup the schedule.
	 */
	private ArrayList<String> mySchedule = new ArrayList<String>();
	
	/**
	 * classes The list of string that makeup the classes.
	 */
	private ArrayList<ArrayList<String>> classes = new ArrayList<ArrayList<String>>();
	
	/*
	 * courseList - Main data structure to store a bunch of courses.
	 */
	private ArrayList<Course> courseList = new ArrayList<Course>();
	
	
	
	/*
	 * Constructor for the class.
	 * 
	 * @param list List of courses.
	 */
	public Schedule(ArrayList<Course> list) {
		this.courseList = list;
	}
	

	
	/*
	 * Getter for courseList
	 * 
	 */
	public ArrayList<Course> getCourseList() {
		return courseList;
	}
	

}
