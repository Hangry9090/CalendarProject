/**
 * Name: GVSU Scheduler
 * Description: Takes a schedule from GVSU's banner website,
 * and creates a .ics file to be imported into other
 * calendar apps.
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
	 */
	public ArrayList<String> parseFile(String fileName) throws IOException {
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
	 * Main function to parse file.
	 * @param args Command Line input. Unused.
	 * @throws IOException If wrong file?
	 */
	public static void main(final String[] args) throws IOException {
		Scheduler schedule = new Scheduler();
		ArrayList<String> mySchedule = new ArrayList<String>();

		mySchedule = schedule.parseFile("sched.html");
		
		
		for (String s: mySchedule) {
			System.out.println(s);
		}
		
	}

}
