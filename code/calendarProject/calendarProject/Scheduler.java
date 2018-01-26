/**
 * 
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

	public static void main(String args[]) throws IOException {
		Scheduler schedule = new Scheduler();

//		ArrayList<String> testList = schedule.readFile("sched.html");
//		
//		int count = 0;
//		for (String str : testList){
//			System.out.println(count + ": " + str);
//			count++;
//		}
		
		
		ArrayList<String> mySchedule = new ArrayList<String>();
		
		
		File input = new File("sched.html");
	
		
		// Creates the schedule html into a Jsoup document object
		Document doc = Jsoup.parse(input, "UTF-8");
		
		// All elements inside the datadisplaytable are stored in a Jsoup elements object
		Elements schedTable = doc.getElementsByClass("datadisplaytable");
				
		// It will now separate the rows and columns of the table
		Elements rows = schedTable.select("tr");
		
		for (Element row: rows) {
			
			Elements cells=row.select("td");
			for (Element cell: cells) {
				if (!cell.text().isEmpty()) {
				//System.out.println(cell.text());
					mySchedule.add(cell.text());
				
				}
			}
			
		}
		
		for (String s: mySchedule) {
			System.out.println(s);
		}
		
	}

}
