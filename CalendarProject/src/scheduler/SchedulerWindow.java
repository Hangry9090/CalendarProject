package scheduler;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class SchedulerWindow extends JFrame implements ActionListener {

	/** Navigation Buttons */

	private Scheduler scheduler;
	private LoginView loginView;

	private JPanel contentPane;
	private JButton viewButton;
	private JButton compareButton;
	private JButton classInfoButton;
	private JButton helpButton;

	private JPanel cardPanel;
	private JPanel viewPanel;
	private JPanel comparePanel;
	private JPanel classInfoPanel;
	private JPanel helpPanel;

	private CardLayout cardLayout;

	// View Fields
	private JButton viewImportButton;
	private JButton viewExportButton;
	private JTable viewScheduleTable;
	private JLabel viewScheduleLabel;

	// Compare Fields
	private JTextField compareSchedulesText;
	private JTable compareScheduleTable;
	private JButton compareImportButton;
	private JLabel compareScheduleLabel;

	// ClassInfo Fields
	private JTextField classInfoText;
	private JLabel classInfoLabel;

	// Help Fields
	private JTextField helpText;
	private JLabel helpLabel;

	public SchedulerWindow() {
		setTitle("Grand Valley Scheduler");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 530);

		this.scheduler = new Scheduler();

		// this.setPreferredSize(new Dimension(600, 365));
		// this.setMaximumSize(new Dimension(600,375));
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel sidePanel = new JPanel();

		sidePanel.setBackground(new Color(25, 25, 112));
		contentPane.add(sidePanel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		sidePanel.setLayout(gbl_panel);

		JPanel navButtonPanel = new JPanel();

		navButtonPanel.setBackground(new Color(25, 25, 112));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		sidePanel.add(navButtonPanel, gbc_panel_2);
		navButtonPanel.setLayout(new GridLayout(0, 1, 0, 0));

		viewButton = new JButton("View");
		compareButton = new JButton("Compare");
		classInfoButton = new JButton("Class Info");
		helpButton = new JButton("Help");

		setButtonStyle(viewButton);
		setButtonStyle(compareButton);
		setButtonStyle(classInfoButton);
		setButtonStyle(helpButton);

		navButtonPanel.add(viewButton);
		navButtonPanel.add(compareButton);
		navButtonPanel.add(classInfoButton);
		navButtonPanel.add(helpButton);

		cardPanel = new JPanel();

		contentPane.add(cardPanel, BorderLayout.CENTER);

		cardLayout = new CardLayout();
		cardPanel.setLayout(cardLayout);

		viewPanel = createViewPanel();
		comparePanel = createComparePanel();
		classInfoPanel = createClassInfoPanel();
		helpPanel = createHelpPanel();

		cardPanel.add(viewPanel, "1");
		cardPanel.add(comparePanel, "2");
		cardPanel.add(classInfoPanel, "3");
		cardPanel.add(helpPanel, "4");

		cardLayout.show(cardPanel, "1");

		this.addMouseListener(viewButton);
		this.addMouseListener(compareButton);
		this.addMouseListener(classInfoButton);
		this.addMouseListener(helpButton);

		viewButton.addActionListener(this);
		compareButton.addActionListener(this);
		classInfoButton.addActionListener(this);
		helpButton.addActionListener(this);
		viewImportButton.addActionListener(this);
		viewExportButton.addActionListener(this);

		// TODO: Add actionlisteners to rest of the buttons
		
		setVisible(true);
	}

	/**
	 * Sets the default font and color for the navigation buttons.
	 * 
	 * @param button
	 */
	private void setButtonStyle(JButton button) {
		button.setFont(new Font("Verdana", Font.BOLD, 16));
		button.setOpaque(true);
		button.setBackground(new Color(25, 25, 112));
		button.setForeground(Color.WHITE);
		button.setBorderPainted(false);

	}

	/**
	 * Allows for buttons to change colors on mouse hover.
	 * 
	 * @param button
	 */
	private void addMouseListener(JButton button) {
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(new Color(25, 25, 140));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(new Color(25, 25, 112));
			}
		});
	}

	/**
	 * Creates the view panel
	 * 
	 * @return
	 */
	private JPanel createViewPanel() {

		JPanel viewPanel = new JPanel();
		viewPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		viewPanel.add(panel, BorderLayout.SOUTH);

		this.viewImportButton = new JButton("Import Schedule");
		panel.add(this.viewImportButton);

		this.viewExportButton = new JButton("Export as .ICS");
		panel.add(this.viewExportButton);

		this.viewScheduleLabel = new JLabel("No Schedule");
		this.viewScheduleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		viewPanel.add(this.viewScheduleLabel, BorderLayout.NORTH);

		this.viewScheduleTable = new JTable(18, 6);
		this.viewScheduleTable.setRowHeight(25);

		this.viewScheduleTable.setEnabled(false);
		this.viewScheduleTable.setGridColor(Color.GRAY);

		viewPanel.add(this.viewScheduleTable, BorderLayout.CENTER);

		return viewPanel;
	}

	/**
	 * Creates the Compare panel.
	 * 
	 * @return
	 */
	private JPanel createComparePanel() {
		JPanel compare = new JPanel();

		compare.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		compare.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		this.compareImportButton = new JButton("Import Schedules");
		panel.add(this.compareImportButton);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Schedules", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		this.compareSchedulesText = new JTextField();
		this.compareSchedulesText.setHorizontalAlignment(SwingConstants.CENTER);
		this.compareSchedulesText.setEditable(false);
		this.compareSchedulesText.setText("No Schedules");
		panel_1.add(this.compareSchedulesText, BorderLayout.CENTER);
		this.compareSchedulesText.setColumns(10);

		this.compareScheduleLabel = new JLabel("No Schedules");
		this.compareScheduleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		compare.add(this.compareScheduleLabel, BorderLayout.NORTH);

		this.compareScheduleTable = new JTable();

		this.compareScheduleTable = new JTable(18, 6);
		this.compareScheduleTable.setRowHeight(25);
		this.compareScheduleTable.setGridColor(Color.gray);

		this.compareScheduleTable.setEnabled(false);

		compare.add(this.compareScheduleTable, BorderLayout.CENTER);
		return compare;
	}

	/**
	 * Creates the ClassInfo panel.
	 * 
	 * @return
	 */
	private JPanel createClassInfoPanel() {

		JPanel classInfo = new JPanel();

		classInfo.setLayout(new BorderLayout(0, 0));

		this.classInfoLabel = new JLabel("Class Info");
		classInfo.add(this.classInfoLabel, BorderLayout.NORTH);

		this.classInfoText = new JTextField();
		this.classInfoText.setEditable(false);
		this.classInfoButton.setText("No Info");
		classInfo.add(this.classInfoText, BorderLayout.CENTER);
		this.classInfoText.setColumns(10);
		return classInfo;
	}

	/**
	 * Creates the Help panel.
	 * 
	 * @return
	 */
	private JPanel createHelpPanel() {

		JPanel help = new JPanel();

		help.setLayout(new BorderLayout(0, 0));

		this.helpLabel = new JLabel("Help");
		help.add(this.helpLabel, BorderLayout.NORTH);

		this.helpText = new JTextField();
		this.helpText.setEditable(false);
		this.helpText.setHorizontalAlignment(SwingConstants.LEFT);
		this.helpText.setText("Instructions:");
		help.add(this.helpText, BorderLayout.CENTER);
		this.helpText.setColumns(10);

		return help;

	}

	/**
	 * Controls the actions of all of the buttons.
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.viewButton) {
			this.cardLayout.show(cardPanel, "1");

		} else if (e.getSource() == this.compareButton) {
			this.cardLayout.show(cardPanel, "2");

		} else if (e.getSource() == this.classInfoButton) {
			this.cardLayout.show(cardPanel, "3");

		} else if (e.getSource() == this.helpButton) {
			this.cardLayout.show(cardPanel, "4");
		} else if (e.getSource() == this.viewImportButton) {
			System.out.println("Pressed");
			this.loginView = LoginView.getInstance(this.scheduler);
		} else if (e.getSource() == this.viewExportButton) {

			// TODO: Fix this to properly export ICS file

			// FileDialog dialog = new FileDialog(this, .SAVE);
			// dialog.setFilterNames(new String[] {"ICS Files (.ics)" });
			// dialog.setFilterExtensions(new String[] {"*.ics" });
			// String savePath = dialog.open();
			// try {
			// scheduler.outputFile(savePath);
			// messageTextField.setText("\nCalendar file created sucessfully!");
			// messageTextField.setText("\nSaved to: " + savePath);
			// } catch (IOException e1) {
			// messageTextField.setText("\nFile not loaded.");
			// } catch (NoSuchFieldException e1) {
			// messageTextField.setText("\nPlease input a file first.");
			// } catch (NullPointerException e1) {
			// messageTextField.setText("\nFile not loaded.");
			// }

		}

	}

	/**
	 * For creating the window.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		SchedulerWindow window = new SchedulerWindow();

	}

}
