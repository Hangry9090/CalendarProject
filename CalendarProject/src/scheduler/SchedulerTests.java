package scheduler;


import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests the input and output functionality of GVSU Scheduler.
 * 
 * @author Alan
 *
 */
class SchedulerTests {

	/**
	 * Adds a filename to the test directory path.
	 * 
	 * @param fileName
	 *            Name of the file to be added to test path
	 * @return The file path including the specified file
	 */
	public String findTestDirectory(final String fileName) {

		String path = System.getProperty("user.home") + File.separator + "Documents";
		path += File.separator + "SchedulerTesting";

		File customDir = new File(path);

		// Finds if the test directory exists
		if (customDir.exists()) {

			path += File.separator + fileName;

			return path;

			// Creates the directory if it does not exist
		} else {

			customDir.mkdirs();
			path += File.separator + fileName;

			return path;

		}

	}

	/**
	 * Tests the creation of an ICS object.
	 */
	@Test
	void testICSCreation() {

		String properICSEvent = "BEGIN:VCALENDAR\n" + "VERSION:2.0\n" + "BEGIN:VEVENT\n"
				+ "DTSTART;TZID=America/Detroit:20180108T120000\n" + "DTEND;TZID=America/Detroit:20180108T125000\n"
				+ "RRULE:FREQ=WEEKLY;UNTIL=20180428T000000;INTERVAL=1;BYDAY=MO,WE,FR\n" + "SUMMARY:Waffle Science\n"
				+ "DESCRIPTION:WFL 225\\nWaffle Center\n" + "END:VEVENT\n" + "END:VCALENDAR\n";

		ICSEventBuilder ics = new ICSEventBuilder();
		ics.beginEvent();
		ics.setDateStartEnd("Jan 08, 2018", "12:00 pm - 12:50 pm", "MWF");
		ics.setRule("April 28, 2018", "MWF");
		ics.setSummary("Waffle Science");
		ics.setDescription("WFL 225\\nWaffle Center");
		ics.endEvent();
		ics.endCalendar();

		assertTrue(ics.toString().equals(properICSEvent));

	}

	/**
	 * Testing the input and output of a schedule w/o a hybrid class.
	 * 
	 * @throws IOException
	 *             Error in reading or writing file
	 * @throws NoSuchFieldException
	 *             Error in outputting before inputting file
	 */
	@Test
	void testNonHybridSched() throws IOException, NoSuchFieldException {

		String icsBegin = "BEGIN:VCALENDAR";
		String icsEnd = "END:VCALENDAR";

		Scheduler jakeSched = new Scheduler();

		jakeSched.inputFile("sched.html");

		String icsDir = findTestDirectory("JakeTest.ics");

		jakeSched.outputFile(icsDir);

		assertTrue(jakeSched.toStringICS().contains(icsBegin) && jakeSched.toStringICS().contains(icsEnd));

	}

	/**
	 * Testing the input and output of a schedule with a hybrid class.
	 * 
	 * @throws NoSuchFieldException
	 *             Error in outputting before inputting
	 * @throws IOException
	 *             Error in reading or writing file
	 */
	@Test
	void testHybridSched() throws NoSuchFieldException, IOException {

		Scheduler rodSched = new Scheduler();

		rodSched.inputFile("RodSched.html");

		String icsDir = findTestDirectory("RodTest.ics");

		rodSched.outputFile(icsDir);

	}

	/**
	 * Tests inputting multiple different schedules to see if it changes the output.
	 * 
	 * @throws IOException
	 *             Error in outputting before inputting
	 * @throws NoSuchFieldException
	 *             Error in reading or writing file
	 */
	@Test
	void testMultipleInputSchedules() throws IOException, NoSuchFieldException {
		Scheduler jakeSched = new Scheduler();

		jakeSched.inputFile("sched.html");
		jakeSched.inputFile("CSS-Marshal.html");
		jakeSched.inputFile("sched.html");

		// Only Jake is taking CIS 654
		assertTrue(jakeSched.toString().contains("CIS 654"));

	}

	/**
	 * Tests the input of a valid HTML schedule file.
	 * 
	 * @throws IOException
	 *             Error in reading file
	 */
	@Test
	void testGoodHTMLFile() throws IOException {

		Scheduler testSched = new Scheduler();

		testSched.inputFile("sched.html");
	}

	/**
	 * Tests the input of a valid HTM schedule file.
	 * 
	 * @throws IOException
	 *             Error in reading file
	 */
	@Test
	void testGoodHTMFile() throws IOException {

		Scheduler testSched = new Scheduler();

		testSched.inputFile("sched.htm");
	}

	/**
	 * Tests if an empty input results in an exception.
	 */
	@Test
	void testNoInput() {
		Scheduler testSched = new Scheduler();

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			testSched.inputFile("");
		});
	}

	
	/**
	 * Tests if the input of non HTML file results in an exception.
	 */
	@Test
	void testNotHTML() {

		Scheduler testSched = new Scheduler();

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			testSched.inputFile("Sched.ht");
		});
	}

	/**
	 * Tests if the input of a non-existent file throws an exception.
	 */
	@Test
	void testFileDoesNotExist() {

		Scheduler testSched = new Scheduler();

		Assertions.assertThrows(IOException.class, () -> {
			testSched.inputFile("NotExist.html");
		});
	}

	/**
	 * Tests if the input of an invalid HTML file results in an exception.
	 */
	@Test
	void testInvalidHTML() {
		Scheduler testSched = new Scheduler();

		Assertions.assertThrows(IOException.class, () -> {
			testSched.inputFile("BadFile.html");
		});
	}

	/**
	 * Tests if an empty output file results in an exception.
	 */
	@Test
	void testNoOutput() {
		Scheduler testSched = new Scheduler();

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			String fileName = "sched.html";

			testSched.inputFile(fileName);

			testSched.outputFile("");
		});
	}

	/**
	 * Tests if a null output file results in an exception.
	 */
	@Test
	void testNullPath() {
		Scheduler testSched = new Scheduler();

		Assertions.assertThrows(NullPointerException.class, () -> {
			String fileName = "sched.html";
			String filePath = null;

			testSched.inputFile(fileName);

			testSched.outputFile(filePath);
		});
	}

	/**
	 * Test if an invalid file extension results in an exception.
	 */
	@Test
	void testWrongExtension() {
		Scheduler testSched = new Scheduler();

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			String fileName = "sched.html";

			String filePath = findTestDirectory("sched.css");
			testSched.inputFile(fileName);

			testSched.outputFile(filePath);
		});
	}

	/**
	 *  Tests if outputting before inputting results an exception.
	 */
	@Test
	void outputBeforeInput() {
		Scheduler testSched = new Scheduler();

		Assertions.assertThrows(NoSuchFieldException.class, () -> {

			testSched.outputFile("test.ics");
		});
	}

	/**
	 * Tests the creation of course from its constructor.
	 */
	@Test
	void createCourseTest() {

		int cID = 33333;
		String cNum = "WFL 355 02";
		String cName = "Intro to Waffle Engineering";
		String campus = "ALL";
		Double credits = 4.0;
		String level = "U";

		ArrayList<String> days = new ArrayList<String>();
		days.add("MWF");

		ArrayList<String> meetTime = new ArrayList<String>();
		meetTime.add("1:00 pm - 1:50 pm");
		ArrayList<String> location = new ArrayList<String>();
		location.add("222 Not Pancakes Hall");
		ArrayList<String> startDate = new ArrayList<String>();
		startDate.add("Jan 08, 2018");
		ArrayList<String> endDate = new ArrayList<String>();
		endDate.add("April 28, 2018");
		String prof = "Eggo";

		Course course = new Course(cID, cNum, cName, campus, credits, level, days, meetTime, location, startDate,
				endDate, prof);

	}

	/**
	 * Tests the creation of a second course with its constructor.
	 */
	@Test
	void createCourseTest1() {

		int cID = 33333;
		String cNum = "WFL 355 02";
		String cName = "Intro to Waffle Engineering";
		String campus = "ALL";
		Double credits = 4.0;
		String level = "U";

		ArrayList<String> days = new ArrayList<String>();
		days.add("W");

		ArrayList<String> meetTime = new ArrayList<String>();
		meetTime.add("1:00 pm - 1:50 pm");
		ArrayList<String> location = new ArrayList<String>();
		location.add("222 Not Pancakes Hall");
		ArrayList<String> startDate = new ArrayList<String>();
		startDate.add("Jan 17, 2018");
		ArrayList<String> endDate = new ArrayList<String>();
		endDate.add("April 28, 2018");
		String prof = "Eggo";

		Course course = new Course(cID, cNum, cName, campus, credits, level, days, meetTime, location, startDate,
				endDate, prof);

	}

	/** 
	 * Tests the creation of an ICS object with its contructor.
	 */
	@Test
	void createICS() {

		ArrayList<String> icsFormat = new ArrayList<String>();
		icsFormat.add("BEGIN:VCALENDAR");
		icsFormat.add("VERSION:2.0");
		icsFormat.add("DTSTART;TZID=America/Detroit:20180108T120000");
		icsFormat.add("DTEND;TZID=America/Detroit:20180108T125000");
		icsFormat.add("RRULE:FREQ=WEEKLY;UNTIL=20180428T000000;INTERVAL=1;BYDAY=MO,WE,FR");
		icsFormat.add("SUMMARY:Waffle Science");
		icsFormat.add("DESCRIPTION:WFL 225\\nWaffle Center");
		icsFormat.add("END:VEVENT");
		icsFormat.add("END:VCALENDAR");

		ICSEventBuilder ics = new ICSEventBuilder(icsFormat);
	}

}
