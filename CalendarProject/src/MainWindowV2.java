import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.UIManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class MainWindowV2 {

	protected Shell shell;
	private Text messageTextField;
	private Scheduler userSchedule = new Scheduler();

	/**********************************************************************************
	 * Launch the application.
	 * 
	 * @param args
	 *********************************************************************************/
	public static void main(String[] args) {
		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			// handle exception
			System.out.println("Problem setting LAF.");
		}
		try {
			//Try to open the window
			MainWindowV2 window = new MainWindowV2();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**********************************************************************************
	 * Open the window.
	 *********************************************************************************/
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/********************************************************************************
	 * Create contents of the window.
	 *******************************************************************************/
	protected void createContents() {
		
		//Create the window
		shell = new Shell();
		shell.setSize(840, 580);
		shell.setText("GVSU Scheduler");

		//Create the container inside the window
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 10, 802, 513);
		composite.setLayout(null);

		//Add a tab-bar to container
		TabFolder tabFolder = new TabFolder(composite, SWT.NONE);
		tabFolder.setBounds(0, 0, 801, 511);

		//Create home tab
		TabItem homeTab = new TabItem(tabFolder, SWT.NONE);
		homeTab.setText("Home");

		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		homeTab.setControl(composite_1);
		composite_1.setLayout(null);

		//Adding input button
		Button inputButton = new Button(composite_1, SWT.NONE);
		inputButton.setBounds(129, 355, 243, 76);
		inputButton.setText("Input");

		//Action Listener for input button
		inputButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {

				// Code to open file choser found here:
				// https://stackoverflow.com/questions/5703825/does-swing-support-windows-7-style-file-choosers
				FileDialog dialog = new FileDialog(shell, SWT.OPEN);
				String[] filterNames = new String[] {"HTML (.html)"};
				String[] filterExtensions = new String[] {"*.html"};
				dialog.setFilterNames(filterNames);
				dialog.setFilterExtensions(filterExtensions);
				dialog.open();
				String fileName = dialog.getFileName();
				String path = dialog.getFilterPath();

				// change to file so the path separator is included in the path + filename
				File selectedFile = new File(path, fileName);
				fileName = selectedFile.getAbsolutePath();

				//Try to open the file in the scheduler object
				try {
					System.out.println(fileName);
					userSchedule.inputFile(fileName);
				} catch (IOException e1) {
					System.out.println("Error opening file.");
					System.out.println(e1);
				}

				// for if there were multiple files
				/*
				 * System.out.println ("Selected file: "); for(String fileName :
				 * selectedFileNames) { System.out.println("  " + fileName); }
				 */

			}
		});

		//Create the output button
		Button outputButton = new Button(composite_1, SWT.NONE);
		outputButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				
				//Set the file explorer filter to only show ICS files
				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
				dialog.setFilterNames(new String[] {"ICS Files (.ics)"});
				dialog.setFilterExtensions(new String[] {"*.ics"});
				
				//Open the file chooser
				String savePath = dialog.open();
				System.out.println("Save path: " + savePath);
				
				//Try to save the file using the scheduler object
				try {
					userSchedule.outputFile(savePath);
				} catch (IOException e1) {
					//file could not be opened
					e1.printStackTrace();
				} catch (ParseException e1) {
					//file could not be parsed
					e1.printStackTrace();
				} catch (NoSuchFieldException e1) {
					//no file loaded yet
					e1.printStackTrace();
				}
			}
		});
		
		//Set location and label of output
		outputButton.setBounds(426, 355, 243, 76);
		outputButton.setText("Output");

		//Creates the text field above the buttons
		messageTextField = new Text(composite_1, SWT.BORDER);
		messageTextField.setText("Welcome!");
		messageTextField.setBounds(129, 52, 540, 273);

		//Creates instructions tab
		TabItem instructionsTab = new TabItem(tabFolder, SWT.NONE);
		instructionsTab.setText("Instructions");

		//Creates container for the instructions tab
		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		instructionsTab.setControl(composite_2);

	}
}
