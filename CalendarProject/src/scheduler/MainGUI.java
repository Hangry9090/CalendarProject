/**
 * 
 */
package scheduler;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.*;

/**
 * @author jacobwalton
 *
 */
public class MainGUI extends NetbeansGUI {
	/**
	 * Generate serial version UID.
	 */
	private static final long serialVersionUID = 3633097333594705611L;
	private Scheduler mainScheduler;
	private LoginView loginView;
	private Schedule mainViewSchedule;
	private ArrayList<Schedule> compareList = new ArrayList<Schedule>();

	
	/**
	 * 
	 */
	public MainGUI() {
		super();
		
		mainScheduler = new Scheduler();
		
		//add Export action listener
		this.viewExportIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportToIcs();
            }
        });
		
		//add import action listener in view window
		this.viewImportIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                importViewSchedule();
            }
        });
		
		//add import action listener in view window
		this.compareImportIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                importCompareSchedule();
            }
        });
		
	}

	
	/*
	 * Method to respond when import is hit in the compare tab.
	 */
	protected void importCompareSchedule() {
		Scheduler sched = new Scheduler();
		loginView = LoginView.getInstance(sched);
		//TODO wait on input
		compareList.add(sched.createSchedule());
		System.out.println("Compare List: " + compareList);
		Schedule compareSched = compareSchedules();
		displaySchedule(viewScheduleTable2, compareSched);
	}

	/*
	 * Method to compare schedules and create a new one.
	 */
	private Schedule compareSchedules() {
		Schedule sched = new Schedule();
		for (Schedule s : compareList) {
			for (Course c : s.getCourseList()) {
				sched.appendCourseList(c);
			}
		}
		return sched;		
	}


	/*
	 * Method to respond when import button is hit in view window.
	 */
	protected void importViewSchedule() {
		loginView = LoginView.getInstance(mainScheduler);
		this.mainViewSchedule = mainScheduler.createSchedule();
		displaySchedule(viewScheduleTable, mainViewSchedule);
        JOptionPane.showMessageDialog(null, "Import successful!", "Import Successful", JOptionPane.INFORMATION_MESSAGE);

	}

	/*
	 * Method to respond when export button is clicked.
	 */
    protected void exportToIcs() {
		System.out.println("Export hit");
		String savePath;
		JFileChooser dialog = new JFileChooser();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("ICS Files", ".ics");
		dialog.setFileFilter(filter);
		int returnVal = dialog.showSaveDialog(getParent());
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			savePath = dialog.getSelectedFile().getAbsolutePath();
			
			//make sure .ics is the extension
			if (!savePath.endsWith(".ics")) {
		        savePath += ".ics";
			}
			try {
				mainScheduler.outputFile(savePath);
		        JOptionPane.showMessageDialog(null, "Export successful! File saved to: " + savePath, "Export Successful", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
		        JOptionPane.showMessageDialog(null, "Export unsuccessful. Make sure to import a schedule first.", "Error", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	}
    
    /*
     * Function to display a schedule on a table.
     */
    public void displaySchedule(JTable t, Schedule sched) {
    	
    	ArrayList<String> times = new ArrayList<String>();
    	
    	for (Course c : sched.getCourseList()) {
    		for(String time : c.getMeetTimes()) {
    			times.add(time);
    		}
    	}
    	
    	times = (ArrayList<String>) times.stream().distinct().collect(Collectors.toList());
    	
    	System.out.print("Times: " +times);
    	
    }

	/**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       //set look and feel
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

}
