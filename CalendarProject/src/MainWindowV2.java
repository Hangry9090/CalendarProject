import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import javax.swing.JTabbedPane;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.SWT;
import java.awt.Panel;
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

public class MainWindowV2 {

	protected Shell shell;
	private Text txtWelcome;

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
		
		TabItem tbtmHome = new TabItem(tabFolder, SWT.NONE);
		tbtmHome.setText("Home");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmHome.setControl(composite_1);
		composite_1.setLayout(null);
		
		Button btnNewButton = new Button(composite_1, SWT.NONE);
		btnNewButton.setBounds(129, 355, 243, 76);
		btnNewButton.setText("Input");
		
		Button btnNewButton_1 = new Button(composite_1, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton_1.setBounds(426, 355, 243, 76);
		btnNewButton_1.setText("Output");
		
		txtWelcome = new Text(composite_1, SWT.BORDER);
		txtWelcome.setText("Welcome!");
		txtWelcome.setBounds(129, 52, 540, 273);
		
		TabItem tbtmInstructions = new TabItem(tabFolder, SWT.NONE);
		tbtmInstructions.setText("Instructions");
		
		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmInstructions.setControl(composite_2);

	}
}
