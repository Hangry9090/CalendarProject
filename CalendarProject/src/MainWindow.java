import java.awt.BorderLayout;
import java.awt.Composite;
import java.awt.Frame;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MainWindow {

	protected Shell shell;
	private JTextField dialogTextField;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
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
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Composite composite = new Composite(shell, SWT.EMBEDDED);
		composite.setBounds(10, 10, 596, 425);
		
		Frame frame = SWT_AWT.new_Frame(composite);
		
		Panel panel = new Panel();
		frame.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JRootPane rootPane = new JRootPane();
		panel.add(rootPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		rootPane.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		panel_1.setLayout(null);
		
		JButton importButton = new JButton("Import HTML");
		importButton.setBounds(100, 137, 182, 73);
		panel_1.add(importButton);
		
		JButton exportButton = new JButton("Export to calendar");
		exportButton.setBounds(322, 137, 182, 73);
		panel_1.add(exportButton);
		
		dialogTextField = new JTextField();
		dialogTextField.setHorizontalAlignment(SwingConstants.LEFT);
		dialogTextField.setText("Welcome!");
		dialogTextField.setBounds(100, 223, 404, 159);
		panel_1.add(dialogTextField);
		dialogTextField.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_2, null);

	}
}
