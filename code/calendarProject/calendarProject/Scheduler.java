/**
 * Name: GVSU Scheduler
 * Description: Takes a schedule from GVSU's banner website,
 * and creates a .ics file to be imported into other
 * calendar apps.
 * 
 * @authors Marshal Brummel, Alan Sisouphone, Jake Walton
 */
package calendarProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

	/**
	 * Constructor.
	 */
	public Scheduler() {

	}

	/**
	 * This function parses a file.
	 * 
	 * @param fileName Name of file to be parsed.
	 * @throws IOException When the filename doesn't exist.
	 * @return The arrayList containing parsed HTML.
	 */
	public ArrayList<String> parseFile(final String fileName)
			throws IOException {
		ArrayList<String> mySchedule = new ArrayList<String>();
		
		
		File input = new File(fileName);
	
		
		// Creates the schedule html into a Jsoup document object
		Document doc = Jsoup.parse(input, "UTF-8");
		
		// All elements inside the datadisplaytable are stored in a 
		//Jsoup elements object
		Elements schedTable = 
				doc.getElementsByClass("datadisplaytable");
				
		// It will now separate the rows and columns of the table
		Elements rows = schedTable.select("tr");
		
		for (Element row: rows) {
			
			Elements cells = row.select("td");
			for (Element cell: cells) {
				if (!(cell.text().isEmpty())) {
					mySchedule.add(cell.text());
				
				}
			}
			
		}
		return mySchedule;
		
	}

	/**
	 * Open and read a file, and return the lines in the file as a list of
	 * Strings. (Demonstrates Java FileReader, BufferedReader, and Java5.)
	 * 
	 * @param filename The name of the file to be parsed.
	 * @return records The arrayList containing the parsed schedule.
	 */
	private ArrayList<String> readFile(final String filename) {
		ArrayList<String> records = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				records.add(line);
			}
			reader.close();
			return records;
		} catch (Exception e) {
			System.err.format("Exception occurred "
					+ "trying to read '%s'.", filename);
			e.printStackTrace();
			return null;
			}
	}
	
	
	
	/**
	 * This function extracts class information from a schedule
	 * string list and separates the classes into a list of lists
	 * 
	 * @param schedule List of strings extracted from the banner schedule HTML
	 * @return classes An arraylist of arraylists consisting of classes from the schedule.
	 */
	private ArrayList<ArrayList<String>> extractClasses(ArrayList<String> schedule) {
		
		ArrayList<ArrayList<String>> classes = new ArrayList<ArrayList<String>>();
		
		int classCount = 0;
		boolean start = false;
		for (int i = 0; i < schedule.size(); i++) {
			
			// Find a string that's of length 5 "32636" and where the line after is of length 10 or 11 "CIS 290 02"
			if (schedule.get(i).length()==5 && schedule.get(i+1).length()>9 && schedule.get(i+1).length() <12) {
				
				// Start adding classes from this line
				classes.add(new ArrayList<String>());
				
				// Add to class count (This is not keeping track of the number of classes)
				classCount++;
				
				// Add the line from the schedule to the class arrayList
				classes.get(classCount-1).add(schedule.get(i));
				
				// Start adding classes
				start = true;
			}
			
			// Stop adding classes once it finds "Total Credits"
			else if (schedule.get(i).contains("Total Credits:")) {
				start = false;
			}
			
			// Keep adding lines to the class list as long as start is true
			else if (start) {
				classes.get(classCount-1).add(schedule.get(i));
			}
			
		}
		start = false;
		
		return classes;
		
	}
	
	


	/**
	 * Main function to parse file.
	 * @param args Command Line input. Unused.
	 * @throws IOException If wrong file?
	 */
	public static void main(final String[] args) throws IOException {
		Scheduler schedule = new Scheduler();
		ArrayList<String> mySchedule = new ArrayList<String>();
		
		ArrayList<ArrayList<String>> classes = new ArrayList<ArrayList<String>>();

		mySchedule = schedule.parseFile("sched.html");
		
		classes = schedule.extractClasses(mySchedule);
		
		
//		for (String s: mySchedule) {
//			System.out.println(s);
//		}
		
		
		for (ArrayList<String> str: classes) {
			System.out.println(str);
		}
		
	}

}
