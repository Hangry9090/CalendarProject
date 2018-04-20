/**
 * 
 */
package scheduler;

import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author jacobwalton
 *
 */
public class MainGUI extends NetbeansGUI {
	/**
	 * Generate serial version UID.
	 */
	private static final long serialVersionUID = 3633097333594705611L;
	/**
	 * The main scheduler used for the app.
	 */
	private Scheduler mainScheduler;
	/**
	 * The login view used for the app.
	 */
	private LoginView loginView;
	/**
	 * the main schedule. 
	 */
	private Schedule mainViewSchedule;

	/**
	 * List to store schedules that will be compared.
	 */
	private Scheduler compareScheduler;
	/**
	 * The schedule displayed in compare view.
	 */
	private Schedule compareSchedule;

	/**
	 * 
	 */
	public MainGUI() {
		super();

		mainScheduler = new Scheduler();
		compareScheduler = new Scheduler();

		// add Export action listener
		this.viewExportIcon.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				exportToIcs();
			}
		});

		// add import action listener in view window
		this.viewImportIcon.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				importViewSchedule();
			}
		});

		// add import action listener in view window
		this.compareImportIcon.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				importCompareSchedule();
			}
		});

	}

	/**
	 * Method to respond when import is hit in the compare tab.
	 */
	protected void importCompareSchedule() {
		compareScheduler = new Scheduler();
		loginView = LoginView.getInstance(compareScheduler, this, 1);
	}

	/**
	 * Method to respond when import button is hit in view window.
	 */
	protected void importViewSchedule() {
		loginView = LoginView.getInstance(mainScheduler, this, 0);
	}

	/**
	 * Method to respond when export button is clicked.
	 */
	protected void exportToIcs() {
		System.out.println("Export hit");
		String savePath;
		JFileChooser dialog = new JFileChooser();

		FileNameExtensionFilter filter = new FileNameExtensionFilter("ICS Files", ".ics");
		dialog.setFileFilter(filter);
		int returnVal = dialog.showSaveDialog(getParent());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			savePath = dialog.getSelectedFile().getAbsolutePath();

			// make sure .ics is the extension
			if (!savePath.endsWith(".ics")) {
				savePath += ".ics";
			}
			try {
				mainScheduler.outputFile(savePath);
				JOptionPane.showMessageDialog(null, "Export successful! File saved to: " + savePath,
						"Export Successful", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Export unsuccessful. Make sure to import a schedule first.",
						"Error", JOptionPane.INFORMATION_MESSAGE);
			}
		}

	}

	/**
	 * Function to display a schedule on a table.
	 *
	 * @param sched The schedule to display.
	 */
	public void displayCompareSchedule(Schedule sched) {

		for (int i = 0; i < this.viewScheduleTable.getRowCount(); i++) {
			for (int j = 1; j < this.viewScheduleTable.getColumnCount(); j++) {
				this.viewScheduleTable.setValueAt("", i, j);
			}

		}

		for (Course c : sched.getCourseList()) {
			ArrayList<String> days = c.getDays();
			ArrayList<String> times = c.getMeetTimes();
			int j = 1;

			int spot = 0;

			for (String dayList : days) {
				for (char ch : dayList.toCharArray()) {
					// find column
					switch (ch) {
					case 'M':
						j = 1;
						break;
					case 'T':
						j = 2;
						break;
					case 'W':
						j = 3;
						break;
					case 'R':
						j = 4;
						break;
					case 'F':
						j = 5;
						break;
					default:
						break;
					}

					// set rows of times[spot] in column j
					String temp = times.get(spot);
					// temp = temp.replaceAll(":", "");

					String[] hourMin = temp.split(" - ");

					String startStr = hourMin[0];
					String endStr = hourMin[1];

					String[] startSplit = startStr.split(" ");
					startStr = startSplit[0];
					String startSuffix = startSplit[1];

					String[] endSplit = endStr.split(" ");
					endStr = endSplit[0];

					String[] startTimeSplit = startStr.split(":");
					String[] endTimeSplit = endStr.split(":");

					int end = Integer.parseInt(endTimeSplit[0]);
					int start = Integer.parseInt(startTimeSplit[0]);

					// for pm
					if (startSuffix.equals("pm") && start != 12) {
						start += 12;
						end += 12;
					} else if (start == 12) {
						if (end != 12) {
							end += 12;
						}
					}

					// for minutes
					int endMinutes = Integer.parseInt(endTimeSplit[1]);
					int startMinutes = Integer.parseInt(startTimeSplit[1]);

					int minuteRows = endMinutes - startMinutes;

					int numRows = 0;

					if (minuteRows <= 30 && minuteRows != 0) {
						numRows += 1;
					} else if (minuteRows > 30) {
						numRows += 2;
					}

					// now numbers are in hundreds format
					numRows += (end - start) * 2;

					System.out.println("Day: " + ch + "\nNum rows: " + numRows);

					if (startMinutes == 0 || startMinutes == 15 || startMinutes == 50) {
						for (int k = 0; k < numRows; k++) {
							viewScheduleTable2.setValueAt("Busy", ((start * 2) - 14) + k, j);
						}
					} else if (startMinutes == 30 || startMinutes == 45) {
						for (int k = 0; k < numRows; k++) {
							viewScheduleTable2.setValueAt("Busy", (((start * 2) - 14) + 1) + k, j);
						}
					}
				}
				spot++;

			}
			System.out.println("Meet times: " + c.getMeetTimes());
			System.out.println("Meet days: " + c.getDays());

		}

	}

	/**
	 * Function to display a schedule on a table.
	 *
	 * @param sched The schedule to display.
	 */
	public void displayViewSchedule(Schedule sched) {

		// Resets schedule
		for (int i = 0; i < this.viewScheduleTable.getRowCount(); i++) {
			for (int j = 1; j < this.viewScheduleTable.getColumnCount(); j++) {
				this.viewScheduleTable.setValueAt("", i, j);
			}

		}

		//

		for (Course c : sched.getCourseList()) {
			ArrayList<String> days = c.getDays();
			ArrayList<String> times = c.getMeetTimes();
			int j = 1;

			int spot = 0;

			for (String dayList : days) {
				for (char ch : dayList.toCharArray()) {
					// find column
					switch (ch) {
					case 'M':
						j = 1;
						break;
					case 'T':
						j = 2;
						break;
					case 'W':
						j = 3;
						break;
					case 'R':
						j = 4;
						break;
					case 'F':
						j = 5;
						break;
					default:
						break;
					}

					// set rows of times[spot] in column j
					String temp = times.get(spot);
					// temp = temp.replaceAll(":", "");

					String[] hourMin = temp.split(" - ");

					String startStr = hourMin[0];
					String endStr = hourMin[1];

					String[] startSplit = startStr.split(" ");
					startStr = startSplit[0];
					String startSuffix = startSplit[1];

					String[] endSplit = endStr.split(" ");
					endStr = endSplit[0];

					String[] startTimeSplit = startStr.split(":");
					String[] endTimeSplit = endStr.split(":");

					int end = Integer.parseInt(endTimeSplit[0]);
					int start = Integer.parseInt(startTimeSplit[0]);

					// for pm
					if (startSuffix.equals("pm") && start != 12) {
						start += 12;
						end += 12;
					} else if (start == 12) {
						if (end != 12) {
							end += 12;
						}
					}

					// for minutes
					int endMinutes = Integer.parseInt(endTimeSplit[1]);
					int startMinutes = Integer.parseInt(startTimeSplit[1]);

					int minuteRows = endMinutes - startMinutes;

					int numRows = 0;

					if (minuteRows <= 30 && minuteRows != 0) {
						numRows += 1;
					} else if (minuteRows > 30) {
						numRows += 2;
					}

					// now numbers are in hundreds format
					numRows += (end - start) * 2;

					System.out.println("Day: " + ch + "\nNum rows: " + numRows);

					if (startMinutes == 0 || startMinutes == 15 || startMinutes == 50) {
						for (int k = 0; k < numRows; k++) {
							viewScheduleTable.setValueAt(c.getCNum(), ((start * 2) - 14) + k, j);
						}
					} else if (startMinutes == 30 || startMinutes == 45) {
						for (int k = 0; k < numRows; k++) {
							viewScheduleTable.setValueAt(c.getCNum(), (((start * 2) - 14) + 1) + k, j);
						}
					}
				}
				spot++;

			}
			// System.out.println("Meet times: " + c.getMeetTimes());
			// System.out.println("Meet days: " + c.getDays());

		}

	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		// set look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				MainGUI mainFrame = new MainGUI();
				mainFrame.setResizable(true);
				mainFrame.setVisible(true);
			}
		});
	}

	/**
	 * Function to signify the Gui that loginview is done.
	 */
	public void callCompare() {


		this.compareSchedule = compareScheduler.createSchedule();

		displayCompareSchedule(compareSchedule);

		JOptionPane.showMessageDialog(null, "Import successful!", "Import Successful", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Function to signify the Gui that loginview is done.
	 */
	public void callView() {
		// TODO Auto-generated method stub
		this.mainViewSchedule = mainScheduler.createSchedule();
		displayViewSchedule(mainViewSchedule);

		this.infoHTMLView.setContentType("text/html");
		this.infoHTMLView.setText(this.mainScheduler.getScheduleHTML());

		JOptionPane.showMessageDialog(null, "Import successful!", "Import Successful", JOptionPane.INFORMATION_MESSAGE);

	}

}
