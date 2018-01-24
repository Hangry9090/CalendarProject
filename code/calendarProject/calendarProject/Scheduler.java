/**
 * 
 */
package calendarProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author jacobwalton
 *
 */
public class Scheduler {

	/**
	 * Constructor
	 */
	public Scheduler() {

	}

	/**
	 * This function parses a file.
	 * 
	 */
	public void parseFile() {

	}

	/**
	 * Open and read a file, and return the lines in the file as a list of
	 * Strings. (Demonstrates Java FileReader, BufferedReader, and Java5.)
	 */
	private ArrayList<String> readFile(String filename) {
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
			System.err.format("Exception occurred trying to read '%s'.", filename);
			e.printStackTrace();
			return null;
			}
	}

	public static void main(String args[]) {
		Scheduler schedule = new Scheduler();

		ArrayList<String> testList = schedule.readFile("sched.html");
		
		int count = 0;
		for (String str : testList){
			System.out.println(count + ": " + str);
			count++;
		}
	}

}
