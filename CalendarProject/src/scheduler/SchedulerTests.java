package scheduler;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

/**
 * Tests the input and output functionality of GVSU Scheduler.
 * 
 * @author Alan Sisouphone
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

		String properICSEvent = "BEGIN:VCALENDAR" + "VERSION:2.0" + "BEGIN:VEVENT"
				+ "DTSTART;TZID=America/Detroit:20180108T120000" + "DTEND;TZID=America/Detroit:20180108T125000"
				+ "RRULE:FREQ=WEEKLY;UNTIL=20180428T000000;INTERVAL=1;BYDAY=MO,WE,FR" + "SUMMARY:Waffle Science"
				+ "DESCRIPTION:WFL 225\\nWaffle Center" + "END:VEVENT" + "END:VCALENDAR";

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

		jakeSched.inputFile("../sched.html");

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

		rodSched.inputFile("../RodSched.html");

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

		jakeSched.inputFile("../sched.html");
		jakeSched.inputFile("../rodSched.html");
		jakeSched.inputFile("../sched.html");

		// Only Jake is taking CIS 654
		assertTrue(jakeSched.toString().contains("CIS 654"));

	}

	/**
	 * Tests to make sure that improper HTML files are not read.
	 */
	@Test
	void testBadHTMLFile() {

		Scheduler testSched = new Scheduler();

		Assertions.assertThrows(IOException.class, () -> {
			testSched.inputFile("../badSched.html");
		});

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

		testSched.inputFile("../sched.html");
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

		testSched.inputFile("../sched.htm");
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
			String fileName = "../sched.html";

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
			String fileName = "../sched.html";
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
			String fileName = "../sched.html";

			String filePath = findTestDirectory("sched.css");
			testSched.inputFile(fileName);

			testSched.outputFile(filePath);
		});
	}

	/**
	 * Tests if outputting before inputting results an exception.
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

		String properICS = "Class: 33333\n" + "cNum: WFL 355 02\n" + "cName: Intro to Waffle Engineering\n"
				+ "Campus: ALL\n" + "Credits: 4.0\n" + "Level: U\n" + "Time: [1:00 pm - 1:50 pm]\n" + "Days: [MWF]\n"
				+ "Location: [222 Not Pancakes Hall]\n" + "Start Date: [Jan 08, 2018]\n"
				+ "End Date: [April 28, 2018]\n" + "Prof: Eggo\n" + "---------------------------\n";

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

		assertTrue(course.toString().equals(properICS));
	}

	/**
	 * Tests the creation of a second course with its constructor.
	 */
	@Test
	void createCourseTest1() {

		String properICS = "Class: 33333\n" + "cNum: WFL 355 02\n" + "cName: Intro to Waffle Engineering\n"
				+ "Campus: ALL\n" + "Credits: 4.0\n" + "Level: U\n" + "Time: [1:00 pm - 1:50 pm]\n" + "Days: [W]\n"
				+ "Location: [222 Not Pancakes Hall]\n" + "Start Date: [Jan 17, 2018]\n"
				+ "End Date: [April 28, 2018]\n" + "Prof: Eggo\n" + "---------------------------\n";

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
		assertTrue(course.toString().equals(properICS));

	}

	/**
	 * Tests the creation of an ICS object with its contructor.
	 */
	@Test
	void createICS() {

		String properICS = "BEGIN:VCALENDARVERSION:2.0"
				+ "DTSTART;"
				+ "TZID=America/Detroit:20180108T120000"
				+ "DTEND;"
				+ "TZID=America/Detroit:20180108T125000"
				+ "RRULE:FREQ=WEEKLY;"
				+ "UNTIL=20180428T000000;"
				+ "INTERVAL=1;BYDAY=MO,WE,FR"
				+ "SUMMARY:Waffle Science"
				+ "DESCRIPTION:WFL 225\\nWaffle Center"
				+ "END:VEVENT"
				+ "END:VCALENDAR";

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
		assertTrue(ics.toString().equals(properICS));
	}
	
	@Test
	public void testInputHTMLAsString() {
		String testSchedule = "<table class=\"datadisplaytable\" summary=\"Display course details for a student.,BORDER = 1,\" width=\"100%\">\n" + 
				"<tbody><tr>\n" + 
				"<th class=\"ddheader\" scope=\"col\"><acronym title=\"Course Reference Number\">CRN</acronym></th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Course</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Title</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Campus</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Credits</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Level</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Start Date</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">End Date</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Days</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Time</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Location</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Instructor</th>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">27273</td>\n" + 
				"<td class=\"dddefault\">CIS 163 03</td>\n" + 
				"<td class=\"dddefault\">Computer Science II</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    4.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">MWF</td>\n" + 
				"<td class=\"dddefault\">11:00 am - 11:50 am</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall B2235</td>\n" + 
				"<td class=\"dddefault\">Ferguson</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">F</td>\n" + 
				"<td class=\"dddefault\">1:00 pm - 2:50 pm</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall A1105</td>\n" + 
				"<td class=\"dddefault\">Ferguson</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">23152</td>\n" + 
				"<td class=\"dddefault\">MTH 201 06</td>\n" + 
				"<td class=\"dddefault\">Calculus I</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    4.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">TR</td>\n" + 
				"<td class=\"dddefault\">12:00 pm - 1:50 pm</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall A2165</td>\n" + 
				"<td class=\"dddefault\">Stone</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">23290</td>\n" + 
				"<td class=\"dddefault\">MTH 325 01</td>\n" + 
				"<td class=\"dddefault\">Discrete Structures:  Computer Science 2</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    3.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">MWF</td>\n" + 
				"<td class=\"dddefault\">12:00 pm - 12:50 pm</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall BLL116</td>\n" + 
				"<td class=\"dddefault\">Santana</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">27971</td>\n" + 
				"<td class=\"dddefault\">WRT 350 05</td>\n" + 
				"<td class=\"dddefault\">SWS Business Communication</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    3.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">R</td>\n" + 
				"<td class=\"dddefault\">10:00 am - 11:15 am</td>\n" + 
				"<td class=\"dddefault\">Henry Hall 114</td>\n" + 
				"<td class=\"dddefault\">Dine</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">T</td>\n" + 
				"<td class=\"dddefault\">10:00 am - 11:15 am</td>\n" + 
				"<td class=\"dddefault\">AuSable Hall 1115</td>\n" + 
				"<td class=\"dddefault\">Dine</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\"><b>Total Credits:</b></td>\n" + 
				"<td class=\"dddefault\"><b>   14.000</b></td>\n" + 
				"</tr>\n" + 
				"</tbody></table>";
		
		Scheduler schedulerTest = new Scheduler();
		
		try {
			schedulerTest.inputHTML(testSchedule);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(schedulerTest.getScheduleHTML().length() > 0);
		
	}
	
	@Test
	public void testScheduleAppend() {
		String testSchedule = "<table class=\"datadisplaytable\" summary=\"Display course details for a student.,BORDER = 1,\" width=\"100%\">\n" + 
				"<tbody><tr>\n" + 
				"<th class=\"ddheader\" scope=\"col\"><acronym title=\"Course Reference Number\">CRN</acronym></th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Course</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Title</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Campus</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Credits</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Level</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Start Date</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">End Date</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Days</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Time</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Location</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Instructor</th>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">27273</td>\n" + 
				"<td class=\"dddefault\">CIS 163 03</td>\n" + 
				"<td class=\"dddefault\">Computer Science II</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    4.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">MWF</td>\n" + 
				"<td class=\"dddefault\">11:00 am - 11:50 am</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall B2235</td>\n" + 
				"<td class=\"dddefault\">Ferguson</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">F</td>\n" + 
				"<td class=\"dddefault\">1:00 pm - 2:50 pm</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall A1105</td>\n" + 
				"<td class=\"dddefault\">Ferguson</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">23152</td>\n" + 
				"<td class=\"dddefault\">MTH 201 06</td>\n" + 
				"<td class=\"dddefault\">Calculus I</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    4.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">TR</td>\n" + 
				"<td class=\"dddefault\">12:00 pm - 1:50 pm</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall A2165</td>\n" + 
				"<td class=\"dddefault\">Stone</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">23290</td>\n" + 
				"<td class=\"dddefault\">MTH 325 01</td>\n" + 
				"<td class=\"dddefault\">Discrete Structures:  Computer Science 2</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    3.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">MWF</td>\n" + 
				"<td class=\"dddefault\">12:00 pm - 12:50 pm</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall BLL116</td>\n" + 
				"<td class=\"dddefault\">Santana</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">27971</td>\n" + 
				"<td class=\"dddefault\">WRT 350 05</td>\n" + 
				"<td class=\"dddefault\">SWS Business Communication</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    3.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">R</td>\n" + 
				"<td class=\"dddefault\">10:00 am - 11:15 am</td>\n" + 
				"<td class=\"dddefault\">Henry Hall 114</td>\n" + 
				"<td class=\"dddefault\">Dine</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">T</td>\n" + 
				"<td class=\"dddefault\">10:00 am - 11:15 am</td>\n" + 
				"<td class=\"dddefault\">AuSable Hall 1115</td>\n" + 
				"<td class=\"dddefault\">Dine</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\"><b>Total Credits:</b></td>\n" + 
				"<td class=\"dddefault\"><b>   14.000</b></td>\n" + 
				"</tr>\n" + 
				"</tbody></table>";
		
		String additionalSchedule = "<table class=\"datadisplaytable\" summary=\"Display course details for a student.,BORDER = 1,\" width=\"100%\">\n" + 
				"  <tbody>\n" + 
				"    <tr>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        <acronym title=\"Course Reference Number\">\n" + 
				"          CRN\n" + 
				"        </acronym>\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Course\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Title\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Campus\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Credits\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Level\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Start Date\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        End Date\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Days\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Time\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Location\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Instructor\n" + 
				"      </th>\n" + 
				"    </tr>\n" + 
				"    <tr>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        21923\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        CIS 457 20\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Data Communications\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Allendale\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"            4.000\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        U\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Aug 27, 2018\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Dec 15, 2018\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        MWF\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        10:00 am - 10:50 am\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Mackinac Hall B2235\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Kalafut\n" + 
				"      </td>\n" + 
				"    </tr>\n" + 
				"    <tr>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        24184\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        CIS 457 201\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Data Communications\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Allendale\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"            0.000\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        U\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Aug 27, 2018\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Dec 15, 2018\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        T\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        2:00 pm - 3:50 pm\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Mackinac Hall A1167\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Kalafut\n" + 
				"      </td>\n" + 
				"    </tr>\n" + 
				"    <tr>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        26769\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        HST 342 01\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        History of Buddhism and East Asian Religions\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Allendale\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"            3.000\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        U\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Aug 27, 2018\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Dec 15, 2018\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        MWF\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        1:00 pm - 1:50 pm\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Mackinac Hall D1135\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Wangdi\n" + 
				"      </td>\n" + 
				"    </tr>\n" + 
				"    <tr>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        14302\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        STA 216 02\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Intermediate Applied Statistics\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Allendale\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"            3.000\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        U\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Aug 27, 2018\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Dec 15, 2018\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        TR\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        11:30 am - 12:45 pm\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Mackinac Hall A2111\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Gabrosek\n" + 
				"      </td>\n" + 
				"    </tr>\n" + 
				"    <tr>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"         \n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"         \n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"         \n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        <b>\n" + 
				"          Total Credits:\n" + 
				"        </b>\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        <b>\n" + 
				"             10.000\n" + 
				"        </b>\n" + 
				"      </td>\n" + 
				"    </tr>\n" + 
				"  </tbody>\n" + 
				"</table>";
		
		Scheduler schedulerTest = new Scheduler();
		
		try {
			schedulerTest.inputHTML(testSchedule);
			schedulerTest.inputHTML(additionalSchedule);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(schedulerTest.getScheduleHTML().equals(additionalSchedule));
		
	}
	
	@Test
	public void testInputSchedulerCreateSchedule() {
		String testSchedule = "<table class=\"datadisplaytable\" summary=\"Display course details for a student.,BORDER = 1,\" width=\"100%\">\n" + 
				"<tbody><tr>\n" + 
				"<th class=\"ddheader\" scope=\"col\"><acronym title=\"Course Reference Number\">CRN</acronym></th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Course</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Title</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Campus</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Credits</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Level</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Start Date</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">End Date</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Days</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Time</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Location</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Instructor</th>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">27273</td>\n" + 
				"<td class=\"dddefault\">CIS 163 03</td>\n" + 
				"<td class=\"dddefault\">Computer Science II</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    4.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">MWF</td>\n" + 
				"<td class=\"dddefault\">11:00 am - 11:50 am</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall B2235</td>\n" + 
				"<td class=\"dddefault\">Ferguson</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">F</td>\n" + 
				"<td class=\"dddefault\">1:00 pm - 2:50 pm</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall A1105</td>\n" + 
				"<td class=\"dddefault\">Ferguson</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">23152</td>\n" + 
				"<td class=\"dddefault\">MTH 201 06</td>\n" + 
				"<td class=\"dddefault\">Calculus I</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    4.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">TR</td>\n" + 
				"<td class=\"dddefault\">12:00 pm - 1:50 pm</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall A2165</td>\n" + 
				"<td class=\"dddefault\">Stone</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">23290</td>\n" + 
				"<td class=\"dddefault\">MTH 325 01</td>\n" + 
				"<td class=\"dddefault\">Discrete Structures:  Computer Science 2</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    3.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">MWF</td>\n" + 
				"<td class=\"dddefault\">12:00 pm - 12:50 pm</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall BLL116</td>\n" + 
				"<td class=\"dddefault\">Santana</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">27971</td>\n" + 
				"<td class=\"dddefault\">WRT 350 05</td>\n" + 
				"<td class=\"dddefault\">SWS Business Communication</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    3.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">R</td>\n" + 
				"<td class=\"dddefault\">10:00 am - 11:15 am</td>\n" + 
				"<td class=\"dddefault\">Henry Hall 114</td>\n" + 
				"<td class=\"dddefault\">Dine</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">T</td>\n" + 
				"<td class=\"dddefault\">10:00 am - 11:15 am</td>\n" + 
				"<td class=\"dddefault\">AuSable Hall 1115</td>\n" + 
				"<td class=\"dddefault\">Dine</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\"><b>Total Credits:</b></td>\n" + 
				"<td class=\"dddefault\"><b>   14.000</b></td>\n" + 
				"</tr>\n" + 
				"</tbody></table>";
		
		Scheduler schedulerTest = new Scheduler();
		
		try {
			schedulerTest.inputHTML(testSchedule);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Schedule testSchedule1 = schedulerTest.createSchedule();
		testSchedule1.getCourseList();
		
	}
	
	
	@Test
	public void testCompareSchedules() {
		String testSchedule = "<table class=\"datadisplaytable\" summary=\"Display course details for a student.,BORDER = 1,\" width=\"100%\">\n" + 
				"<tbody><tr>\n" + 
				"<th class=\"ddheader\" scope=\"col\"><acronym title=\"Course Reference Number\">CRN</acronym></th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Course</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Title</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Campus</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Credits</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Level</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Start Date</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">End Date</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Days</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Time</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Location</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Instructor</th>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">27273</td>\n" + 
				"<td class=\"dddefault\">CIS 163 03</td>\n" + 
				"<td class=\"dddefault\">Computer Science II</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    4.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">MWF</td>\n" + 
				"<td class=\"dddefault\">11:00 am - 11:50 am</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall B2235</td>\n" + 
				"<td class=\"dddefault\">Ferguson</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">F</td>\n" + 
				"<td class=\"dddefault\">1:00 pm - 2:50 pm</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall A1105</td>\n" + 
				"<td class=\"dddefault\">Ferguson</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">23152</td>\n" + 
				"<td class=\"dddefault\">MTH 201 06</td>\n" + 
				"<td class=\"dddefault\">Calculus I</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    4.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">TR</td>\n" + 
				"<td class=\"dddefault\">12:00 pm - 1:50 pm</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall A2165</td>\n" + 
				"<td class=\"dddefault\">Stone</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">23290</td>\n" + 
				"<td class=\"dddefault\">MTH 325 01</td>\n" + 
				"<td class=\"dddefault\">Discrete Structures:  Computer Science 2</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    3.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">MWF</td>\n" + 
				"<td class=\"dddefault\">12:00 pm - 12:50 pm</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall BLL116</td>\n" + 
				"<td class=\"dddefault\">Santana</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">27971</td>\n" + 
				"<td class=\"dddefault\">WRT 350 05</td>\n" + 
				"<td class=\"dddefault\">SWS Business Communication</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    3.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">R</td>\n" + 
				"<td class=\"dddefault\">10:00 am - 11:15 am</td>\n" + 
				"<td class=\"dddefault\">Henry Hall 114</td>\n" + 
				"<td class=\"dddefault\">Dine</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">T</td>\n" + 
				"<td class=\"dddefault\">10:00 am - 11:15 am</td>\n" + 
				"<td class=\"dddefault\">AuSable Hall 1115</td>\n" + 
				"<td class=\"dddefault\">Dine</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\"><b>Total Credits:</b></td>\n" + 
				"<td class=\"dddefault\"><b>   14.000</b></td>\n" + 
				"</tr>\n" + 
				"</tbody></table>";
		
		String additionalSchedule = "<table class=\"datadisplaytable\" summary=\"Display course details for a student.,BORDER = 1,\" width=\"100%\">\n" + 
				"  <tbody>\n" + 
				"    <tr>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        <acronym title=\"Course Reference Number\">\n" + 
				"          CRN\n" + 
				"        </acronym>\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Course\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Title\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Campus\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Credits\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Level\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Start Date\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        End Date\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Days\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Time\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Location\n" + 
				"      </th>\n" + 
				"      <th class=\"ddheader\" scope=\"col\">\n" + 
				"        Instructor\n" + 
				"      </th>\n" + 
				"    </tr>\n" + 
				"    <tr>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        21923\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        CIS 457 20\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Data Communications\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Allendale\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"            4.000\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        U\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Aug 27, 2018\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Dec 15, 2018\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        MWF\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        10:00 am - 10:50 am\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Mackinac Hall B2235\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Kalafut\n" + 
				"      </td>\n" + 
				"    </tr>\n" + 
				"    <tr>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        24184\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        CIS 457 201\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Data Communications\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Allendale\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"            0.000\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        U\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Aug 27, 2018\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Dec 15, 2018\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        T\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        2:00 pm - 3:50 pm\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Mackinac Hall A1167\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Kalafut\n" + 
				"      </td>\n" + 
				"    </tr>\n" + 
				"    <tr>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        26769\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        HST 342 01\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        History of Buddhism and East Asian Religions\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Allendale\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"            3.000\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        U\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Aug 27, 2018\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Dec 15, 2018\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        MWF\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        1:00 pm - 1:50 pm\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Mackinac Hall D1135\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Wangdi\n" + 
				"      </td>\n" + 
				"    </tr>\n" + 
				"    <tr>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        14302\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        STA 216 02\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Intermediate Applied Statistics\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Allendale\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"            3.000\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        U\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Aug 27, 2018\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Dec 15, 2018\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        TR\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        11:30 am - 12:45 pm\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Mackinac Hall A2111\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        Gabrosek\n" + 
				"      </td>\n" + 
				"    </tr>\n" + 
				"    <tr>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"         \n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"         \n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"         \n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        <b>\n" + 
				"          Total Credits:\n" + 
				"        </b>\n" + 
				"      </td>\n" + 
				"      <td class=\"dddefault\">\n" + 
				"        <b>\n" + 
				"             10.000\n" + 
				"        </b>\n" + 
				"      </td>\n" + 
				"    </tr>\n" + 
				"  </tbody>\n" + 
				"</table>";
		
		Scheduler schedulerTest = new Scheduler();
		
		try {
			schedulerTest.inputCompareHTML(testSchedule);
			schedulerTest.inputCompareHTML(additionalSchedule);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// The two schedules combined should contain 8 courses
		assertTrue(schedulerTest.createSchedule().getCourseList().size() == 8);
		
	}
	
	@Test
	public void testInputManyHTML() {
		String testSchedule = "<table class=\"datadisplaytable\" summary=\"Display course details for a student.,BORDER = 1,\" width=\"100%\">\n" + 
				"<tbody><tr>\n" + 
				"<th class=\"ddheader\" scope=\"col\"><acronym title=\"Course Reference Number\">CRN</acronym></th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Course</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Title</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Campus</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Credits</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Level</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Start Date</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">End Date</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Days</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Time</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Location</th>\n" + 
				"<th class=\"ddheader\" scope=\"col\">Instructor</th>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">27273</td>\n" + 
				"<td class=\"dddefault\">CIS 163 03</td>\n" + 
				"<td class=\"dddefault\">Computer Science II</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    4.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">MWF</td>\n" + 
				"<td class=\"dddefault\">11:00 am - 11:50 am</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall B2235</td>\n" + 
				"<td class=\"dddefault\">Ferguson</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">F</td>\n" + 
				"<td class=\"dddefault\">1:00 pm - 2:50 pm</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall A1105</td>\n" + 
				"<td class=\"dddefault\">Ferguson</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">23152</td>\n" + 
				"<td class=\"dddefault\">MTH 201 06</td>\n" + 
				"<td class=\"dddefault\">Calculus I</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    4.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">TR</td>\n" + 
				"<td class=\"dddefault\">12:00 pm - 1:50 pm</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall A2165</td>\n" + 
				"<td class=\"dddefault\">Stone</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">23290</td>\n" + 
				"<td class=\"dddefault\">MTH 325 01</td>\n" + 
				"<td class=\"dddefault\">Discrete Structures:  Computer Science 2</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    3.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">MWF</td>\n" + 
				"<td class=\"dddefault\">12:00 pm - 12:50 pm</td>\n" + 
				"<td class=\"dddefault\">Mackinac Hall BLL116</td>\n" + 
				"<td class=\"dddefault\">Santana</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">27971</td>\n" + 
				"<td class=\"dddefault\">WRT 350 05</td>\n" + 
				"<td class=\"dddefault\">SWS Business Communication</td>\n" + 
				"<td class=\"dddefault\">Allendale</td>\n" + 
				"<td class=\"dddefault\">    3.000</td>\n" + 
				"<td class=\"dddefault\">U</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">R</td>\n" + 
				"<td class=\"dddefault\">10:00 am - 11:15 am</td>\n" + 
				"<td class=\"dddefault\">Henry Hall 114</td>\n" + 
				"<td class=\"dddefault\">Dine</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">Jan 08, 2018</td>\n" + 
				"<td class=\"dddefault\">Apr 28, 2018</td>\n" + 
				"<td class=\"dddefault\">T</td>\n" + 
				"<td class=\"dddefault\">10:00 am - 11:15 am</td>\n" + 
				"<td class=\"dddefault\">AuSable Hall 1115</td>\n" + 
				"<td class=\"dddefault\">Dine</td>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\">&nbsp;</td>\n" + 
				"<td class=\"dddefault\"><b>Total Credits:</b></td>\n" + 
				"<td class=\"dddefault\"><b>   14.000</b></td>\n" + 
				"</tr>\n" + 
				"</tbody></table>";
		
		
		Scheduler schedulerTest = new Scheduler();
		
		try {
			schedulerTest.inputHTML(testSchedule);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Schedule testSchedule1 = new Schedule();
		
		testSchedule1 = schedulerTest.createSchedule();
		
		Course testCourse = testSchedule1.getCourseList().get(0);
		
		testSchedule1.appendCourseList(testCourse);
		
		
		assertTrue(testSchedule1.getCourseList().size() == 5);
		
	}
	
	@Test
	public void testScheduleRetrievalasText() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		
		BannerSchedScrapper test = new BannerSchedScrapper("InsertValidUser", "InsertValidPw");
		test.getScheduleAsHTML("201810");
		//test.getScheduleAsText("201810");
		test.closeClient();
		
		
	}
	
	@Test
	public void testScheduleRetrievalasHTML() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		
		BannerSchedScrapper test = new BannerSchedScrapper("InsertValidUser", "InsertValidPw");
		
		test.getScheduleAsText("201810");
		
		assertTrue(test.isValidSemesterValue("201810"));
		
		
	}
	
	@Test
	public void testScheduleRetrieveWrongHTML() {

		Assertions.assertThrows(ElementNotFoundException.class, () -> {
			BannerSchedScrapper test = new BannerSchedScrapper("InsertValidUser", "InsertValidPw");
			test.getScheduleAsHTML("23242");
		});
		
		
	}
	
	@Test
	public void testScheduleRetrieveInvalidSemester() throws FailingHttpStatusCodeException, MalformedURLException, IOException {

		BannerSchedScrapper test = new BannerSchedScrapper("InsertValidUser", "InsertValidPw");
		assertTrue(!test.isValidSemesterValue("23242"));
		
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
