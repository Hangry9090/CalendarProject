import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import javax.swing.JTabbedPane;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.SWT;
import java.awt.Panel;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import org.eclipse.swt.widgets.Composite;
import java.awt.Frame;
import org.eclipse.swt.awt.SWT_AWT;
import java.awt.BorderLayout;
import javax.swing.JRootPane;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class MainWindowV2 {

	protected Shell shell;
	private Text messageTextField;


	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindowV2 window = new MainWindowV2();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
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

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(840, 580);
		shell.setText("SWT Application");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 10, 802, 513);
		composite.setLayout(null);
		
		TabFolder tabFolder = new TabFolder(composite, SWT.NONE);
		tabFolder.setBounds(0, 0, 801, 511);
		
		TabItem homeTab = new TabItem(tabFolder, SWT.NONE);
		homeTab.setText("Home");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		homeTab.setControl(composite_1);
		composite_1.setLayout(null);
		
		Button inputButton = new Button(composite_1, SWT.NONE);
		inputButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
					JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
			        int returnVal = fileChooser.showOpenDialog(fileChooser);
			}
		});
		inputButton.setBounds(129, 355, 243, 76);
		inputButton.setText("Input");
		
		Button outputButton = new Button(composite_1, SWT.NONE);
		outputButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
		        
			}
		});
		outputButton.setBounds(426, 355, 243, 76);
		outputButton.setText("Output");
		
		messageTextField = new Text(composite_1, SWT.BORDER);
		messageTextField.setText("Welcome!");
		messageTextField.setBounds(129, 52, 540, 273);
		
		TabItem instructionsTab = new TabItem(tabFolder, SWT.NONE);
		instructionsTab.setText("Instructions");
		
		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		instructionsTab.setControl(composite_2);

	}
	
}
