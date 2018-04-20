//package scheduler;
//
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.FileDialog;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.widgets.Shell;
//
//import java.awt.Frame;
//import java.io.File;
//import java.io.IOException;
//import javax.swing.UIManager;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.TabFolder;
//import org.eclipse.swt.widgets.TabItem;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Text;
//import org.eclipse.swt.events.MouseAdapter;
//import org.eclipse.swt.events.MouseEvent;
//
///**
// * Class to create the GUI window.
// * 
// * @author Jacob Walton
// * @author Marshal Brummel
// * @author Alan Sisouphone
// *
// */
//public class MainWindowV2 {
//
//	/**
//	 * shell The GUI shell.
//	 */
//	private Shell shell;
//
//	/**
//	 * 
//	 */
//	private Text messageTextField;
//
//	/**
//	 * userSchedule The schedule to be used by GUI.
//	 */
//	private Scheduler userSchedule = new Scheduler();
//	
//	private LoginView login;
//
//	/**
//	 * Launch the application.
//	 * 
//	 * @param args
//	 *            For command line input.
//	 */
//	public static void main(final String[] args) {
//		try {
//			// Set cross-platform Java L&F (also called "Metal")
//			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//		} catch (Exception e) {
//			// handle exception
//			System.out.println("Problem setting LAF.");
//		}
//		try {
//			MainWindowV2 window = new MainWindowV2();
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * Open the window.
//	 */
//	public void open() {
//		Display display = Display.getDefault();
//		createContents();
//		shell.open();
//		shell.layout();
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch()) {
//				display.sleep();
//			}
//		}
//	}
//	
//
//	/**
//	 * Create contents of the window.
//	 */
//	protected void createContents() {
//		shell = new Shell();
//		shell.setSize(840, 580);
//		shell.setText("GVSU Scheduler");
//
//		Composite composite = new Composite(shell, SWT.NONE);
//		composite.setBounds(10, 10, 802, 513);
//		composite.setLayout(null);
//
//		TabFolder tabFolder = new TabFolder(composite, SWT.NONE);
//		tabFolder.setBounds(0, 0, 801, 511);
//
//		TabItem homeTab = new TabItem(tabFolder, SWT.NONE);
//		homeTab.setText("Home");
//
//		Composite composite1 = new Composite(tabFolder, SWT.NONE);
//		homeTab.setControl(composite1);
//		composite1.setLayout(null);
//
//		Button inputButton = new Button(composite1, SWT.NONE);
//		inputButton.setBounds(129, 355, 243, 76);
//		inputButton.setText("Input");
//
//		inputButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseDown(final MouseEvent e) {
//
//				// code for filedialog found here:
//				// https://stackoverflow.com/questions/5703825/does-swing-support-windows-7-style-file-choosers
////				FileDialog dialog = new FileDialog(shell, SWT.OPEN);
////				String[] filterNames = new String[] {"HTML (.html)" };
////				String[] filterExtensions = new String[] {"*.html" };
////				dialog.setFilterNames(filterNames);
////				dialog.setFilterExtensions(filterExtensions);
////				dialog.open();
////				String fileName = dialog.getFileName();
////				String path = dialog.getFilterPath();
////
////				// change to file so the path separator is included in the path + filename
////				File selectedFile = new File(path, fileName);
////				String fullName = selectedFile.getAbsolutePath();
//				
//				login = LoginView.getInstance(userSchedule);
//				
//				
////				if (login == null) {
////					login = new LoginView(userSchedule);
////				} else {
////					// TODO: Login already created
////					System.out.println(login.toString());
////				}
//
////				try {
////					//userSchedule.inputFile(fullName);
////					messageTextField.setText("\nFile imported sucessfully!");
////				} catch (IllegalArgumentException e1) {
////					messageTextField.setText("\nError opening file.");
////				
////				}
//
//			}
//		});
//
//		Button outputButton = new Button(composite1, SWT.NONE);
//		outputButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseDown(final MouseEvent e) {
//				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
//				dialog.setFilterNames(new String[] {"ICS Files (.ics)" });
//				dialog.setFilterExtensions(new String[] {"*.ics" });
//				String savePath = dialog.open();
//				try {
//					userSchedule.outputFile(savePath);
//					messageTextField.setText("\nCalendar file created sucessfully!");
//					messageTextField.setText("\nSaved to: " + savePath);
//				} catch (IOException e1) {
//					messageTextField.setText("\nFile not loaded.");
//				} catch (NoSuchFieldException e1) {
//					messageTextField.setText("\nPlease input a file first.");
//				} catch (NullPointerException e1) {
//					messageTextField.setText("\nFile not loaded.");
//				}
//			}
//		});
//		outputButton.setBounds(426, 355, 243, 76);
//		outputButton.setText("Output");
//
//		messageTextField = new Text(composite1, SWT.BORDER);
//		messageTextField.setText("Welcome to GVSU Scheduler!\r");
//		messageTextField.setBounds(129, 52, 540, 273);
//
//		TabItem instructionsTab = new TabItem(tabFolder, SWT.NONE);
//		instructionsTab.setText("Instructions");
//
//		Composite composite2 = new Composite(tabFolder, SWT.NONE);
//		instructionsTab.setControl(composite2);
//
//		Label lblInstructions = new Label(composite2, SWT.NONE);
//		lblInstructions.setBounds(10, 10, 773, 458);
//		lblInstructions.setText("Instructions:\n\nBefore Use:\n\n\t");
//		lblInstructions.setText(lblInstructions.getText() + "Download the HTML file from myBanner under ");
//		lblInstructions.setText(lblInstructions.getText() + "Student->Registration->Student Schedule\n\n");
//		lblInstructions.setText(
//				lblInstructions.getText() + "Input:\n\n\tPress the 'Input' button and select the HTML file.\n\n\t");
//		lblInstructions.setText(lblInstructions.getText()
//				+ "Confirm in the text box that the file loaded sucessfully\n\nOutput:\n\n\t");
//		lblInstructions.setText(lblInstructions.getText() + "Press the 'Output' button and name the file.\n\n\t");
//		lblInstructions.setText(lblInstructions.getText() + "Confirm in the text box that the file was created.\n");
//
//	}
//
//}
