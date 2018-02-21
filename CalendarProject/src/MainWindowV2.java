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

/**
 * @author Marshal Brummel, Jacob Walton, and Alan Sisouphone
 *
 */
public class MainWindowV2 {

	/***********************************************************
	 * shell The overarching container for the UI.
	 **********************************************************/
	private Shell shell;
	
	/***********************************************************
	 * messageTextField The textfield for user feedback.
	 **********************************************************/
	private Text messageTextField;
	
	/***********************************************************
	 * userSchedule The instance of Scheduler created by HTML.
	 **********************************************************/
	private Scheduler userSchedule = new Scheduler();

	/***********************************************************
	 * Launch the application.
	 * 
	 * @param args String of arguments
	 **********************************************************/
	public static void main(final String[] args) {
		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			// handle exception
			System.out.println("Problem setting LAF.");
		}
		try {
			MainWindowV2 window = new MainWindowV2();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/********************************************************
	 * Open the window.
	 *******************************************************/
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

	/*******************************************************
	 * Create contents of the window.
	 ******************************************************/
	protected void createContents() {
		shell = new Shell();
		shell.setSize(840, 580);
		shell.setText("GVSU Scheduler");

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 10, 802, 513);
		composite.setLayout(null);

		TabFolder tabFolder = new TabFolder(composite, SWT.NONE);
		tabFolder.setBounds(0, 0, 801, 511);

		TabItem homeTab = new TabItem(tabFolder, SWT.NONE);
		homeTab.setText("Home");

		Composite composite1 = new Composite(tabFolder, SWT.NONE);
		homeTab.setControl(composite1);
		composite1.setLayout(null);

		Button inputButton = new Button(composite1, SWT.NONE);
		inputButton.setBounds(129, 355, 243, 76);
		inputButton.setText("Input");

		inputButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(final MouseEvent e) {

				// code for filedialog found here:
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

		Button outputButton = new Button(composite1, SWT.NONE);
		outputButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(final MouseEvent e) {
				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
				dialog.setFilterNames(new String[] {"ICS Files (.ics)"});
				dialog.setFilterExtensions(new String[] {"*.ics"});
				//System.out.println("Save to: " + dialog.open());
				String savePath = dialog.open();
				System.out.println("Save path: " + savePath);
				try {
					userSchedule.outputFile(savePath);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//file could not be opened
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					//file could not be parsed
					e1.printStackTrace();
				} catch (NoSuchFieldException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					//no file loaded yet
				}
			}
		});
		outputButton.setBounds(426, 355, 243, 76);
		outputButton.setText("Output");

		messageTextField = new Text(composite1, SWT.BORDER);
		messageTextField.setText("Welcome!");
		messageTextField.setBounds(129, 52, 540, 273);

		TabItem instructionsTab = new TabItem(tabFolder, SWT.NONE);
		instructionsTab.setText("Instructions");

		Composite composite2 = new Composite(tabFolder, SWT.NONE);
		instructionsTab.setControl(composite2);

	}

}
