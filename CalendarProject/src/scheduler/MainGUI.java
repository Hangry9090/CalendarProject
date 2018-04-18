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
	
	
	/**
	 * List to store schedules that will be compared.
	 */
	private ArrayList<Schedule> compareList = new ArrayList<Schedule>();
	private Scheduler compareSched;


	
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
		compareSched = new Scheduler();
		loginView = LoginView.getInstance(compareSched, this, 1);		
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
		loginView = LoginView.getInstance(mainScheduler, this, 0);
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
    public void displayCompareSchedule(Schedule sched) {
    	
    	ArrayList<String> times = new ArrayList<String>();
    	
    	for (Course c : sched.getCourseList()) {
    		for(String time : c.getMeetTimes()) {
    			times.add(time);
    		}
    	}
    	
    	times = (ArrayList<String>) times.stream().distinct().collect(Collectors.toList());
    	
    	//System.out.print("Times: " + times);
    	
    }
    
    /*
     * Function to display a schedule on a table.
     */
    public void displayViewSchedule(Schedule sched) {
    	viewScheduleTable.setValueAt("Test", 2, 2);
    	
    	
    	for (Course c : sched.getCourseList()) {
    		ArrayList<String> days = c.getDays();
    		ArrayList<String> times = c.getMeetTimes();
    		int i;
    		int j;
    		
    		int spot = 0;
    		
    		for (String dayList : days) {
    			for (char ch : dayList.toCharArray()) {
	    			//find column
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
	    			}
	    			
	    			//set rows of times[spot] in column j
	    			String temp = times.get(spot);
	    			//temp = temp.replaceAll(":", "");
	    			
	    			
	    			String[] hourMin = temp.split(" - ");
	    			
	    			String startStr = hourMin[0];
	    			String endStr = hourMin[1];
	    			
	    			String[] startSplit = startStr.split(" ");
	    			startStr = startSplit[0];
	    			String startSuffix = startSplit[1];
	    			
	    			String[] endSplit = endStr.split(" ");
	    			endStr = endSplit[0];
	    			String endSuffix = endSplit[1];
	    			
	    			String[] startTimeSplit = startStr.split(":");
	    			String[] endTimeSplit = endStr.split(":");


	    			int end = Integer.parseInt(endTimeSplit[1]);
	    			int start = Integer.parseInt(startTimeSplit[1]);
	    		

	    			//for pm
	    			if (startSuffix.equals("pm") && start != 12){
	    				start += 12;
	    				end += 12;
	    			}
	    			
	    			//adjust end
	    			if (end % 50 == 0 && ((end/50)%2 != 0)) {
	    				end += 10;
	    			}
	    			
	    			//now numbers are in hundreds format
	    			int numRows = (end - start) * 2;
	    			
	    			
	    			System.out.println("Day: " + ch + "\nNum rows: " + numRows);
	    			
	    			
    			}
    			spot++;
    			
    		}
    		System.out.println("Meet times: " + c.getMeetTimes());
    		System.out.println("Meet days: " + c.getDays());

    	}

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


	public void callCompare() {
		// TODO Auto-generated method stub
		compareList.add(compareSched.createSchedule());
		System.out.println("Compare List: " + compareList);
		Schedule compareSched = compareSchedules();
		displayCompareSchedule(compareSched);
	}


	public void callView() {
		// TODO Auto-generated method stub
		this.mainViewSchedule = mainScheduler.createSchedule();
		displayViewSchedule(mainViewSchedule);
        JOptionPane.showMessageDialog(null, "Import successful!", "Import Successful", JOptionPane.INFORMATION_MESSAGE);
	
	}

}
