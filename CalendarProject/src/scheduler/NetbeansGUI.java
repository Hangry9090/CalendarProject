/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * This is the Advanced GUI for the Grand Valley Scheduler application.
 * @author Marshal
 * @author Alan
 * @author Jake
 * 
 * @version 2.0
 */
public class NetbeansGUI extends javax.swing.JFrame {

	
	static final long serialVersionUID = -4485445478056388025L;

	/** Navigation Panels. */
	private final JPanel[] sidePanels;

	/**
	 * Creates new form NetbeansGUI.
	 */
	public NetbeansGUI() {
		initComponents();
		sidePanels = new JPanel[] {this.viewPanel, this.comparePanel, this.classInfoPanel, this.helpPanel};
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {

		mainPanel = new javax.swing.JPanel();
		sidePanel = new javax.swing.JPanel();
		viewPanel = new javax.swing.JPanel();
		viewLabel = new javax.swing.JLabel();
		viewIcon = new javax.swing.JLabel();
		comparePanel = new javax.swing.JPanel();
		compareLabel = new javax.swing.JLabel();
		compareIcon = new javax.swing.JLabel();
		classInfoPanel = new javax.swing.JPanel();
		classInfoLabel = new javax.swing.JLabel();
		classInfoIcon = new javax.swing.JLabel();
		helpPanel = new javax.swing.JPanel();
		helpLabel = new javax.swing.JLabel();
		helpIcon = new javax.swing.JLabel();
		changingViewPanel = new javax.swing.JPanel();
		scheduleNameLabel = new javax.swing.JLabel();
		viewImportIcon = new javax.swing.JLabel();
		viewExportIcon = new javax.swing.JLabel();
		jSeparator2 = new javax.swing.JSeparator();
		jScrollPane1 = new javax.swing.JScrollPane();
		viewScheduleTable = new javax.swing.JTable();
		changingComparePanel = new javax.swing.JPanel();
		scheduleNameLabel2 = new javax.swing.JLabel();
		compareImportIcon = new javax.swing.JLabel();
		jSeparator3 = new javax.swing.JSeparator();
		jScrollPane5 = new javax.swing.JScrollPane();
		viewScheduleTable2 = new javax.swing.JTable();
		changingHelpPanel = new javax.swing.JPanel();
		scheduleNameLabel4 = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JSeparator();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		changingInfoPanel = new javax.swing.JPanel();
		scheduleNameLabel3 = new javax.swing.JLabel();
		jScrollPane3 = new javax.swing.JScrollPane();
		infoHTMLView = new javax.swing.JEditorPane();

		

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setLocationByPlatform(true);

		mainPanel.setBackground(new java.awt.Color(255, 255, 255));
		mainPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		sidePanel.setBackground(new java.awt.Color(0, 0, 153));
		sidePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		viewPanel.setBackground(new java.awt.Color(255, 255, 255));
		viewPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		viewLabel.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
		viewLabel.setForeground(new java.awt.Color(0, 0, 153));
		viewLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		viewLabel.setText("View");
		viewLabel.setIconTextGap(0);
		viewLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(final java.awt.event.MouseEvent evt) {
				viewLabelMouseClicked(evt);
			}
		});

		viewIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		viewIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("assests/Blue_Menu_icon.png"))); // NOI18N
		viewIcon.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(final java.awt.event.MouseEvent evt) {
				viewLabelMouseClicked(evt);
			}
		});

		javax.swing.GroupLayout viewPanelLayout = new javax.swing.GroupLayout(viewPanel);
		viewPanel.setLayout(viewPanelLayout);
		viewPanelLayout
				.setHorizontalGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(viewPanelLayout.createSequentialGroup()
								.addComponent(viewIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 67,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(viewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
								.addContainerGap()));
		viewPanelLayout.setVerticalGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(viewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(viewIcon, javax.swing.GroupLayout.Alignment.TRAILING,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		sidePanel.add(viewPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 250, 50));

		comparePanel.setBackground(new java.awt.Color(255, 255, 255));

		compareLabel.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
		compareLabel.setForeground(new java.awt.Color(0, 0, 153));
		compareLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		compareLabel.setText("Compare");
		compareLabel.setIconTextGap(0);
		compareLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(final java.awt.event.MouseEvent evt) {
				compareIconMouseClicked(evt);
			}
		});

		compareIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		compareIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("assests/Blues_scales_icon.png"))); // NOI18N
		compareIcon.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(final java.awt.event.MouseEvent evt) {
				compareIconMouseClicked(evt);
			}
		});

		javax.swing.GroupLayout comparePanelLayout = new javax.swing.GroupLayout(comparePanel);
		comparePanel.setLayout(comparePanelLayout);
		comparePanelLayout
				.setHorizontalGroup(comparePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(comparePanelLayout.createSequentialGroup()
								.addComponent(compareIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 67,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(compareLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
								.addContainerGap()));
		comparePanelLayout.setVerticalGroup(comparePanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(compareLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(compareIcon, javax.swing.GroupLayout.Alignment.TRAILING,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		sidePanel.add(comparePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 250, 50));

		classInfoPanel.setBackground(new java.awt.Color(255, 255, 255));

		classInfoLabel.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
		classInfoLabel.setForeground(new java.awt.Color(0, 0, 153));
		classInfoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		classInfoLabel.setText("Class Info");
		classInfoLabel.setIconTextGap(0);
		classInfoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(final java.awt.event.MouseEvent evt) {
				classInfoIconMouseClicked(evt);
			}
		});

		classInfoIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		classInfoIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("assests/Blue_info_icon.png"))); // NOI18N
		classInfoIcon.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(final java.awt.event.MouseEvent evt) {
				classInfoIconMouseClicked(evt);
			}
		});

		javax.swing.GroupLayout classInfoPanelLayout = new javax.swing.GroupLayout(classInfoPanel);
		classInfoPanel.setLayout(classInfoPanelLayout);
		classInfoPanelLayout.setHorizontalGroup(classInfoPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(classInfoPanelLayout.createSequentialGroup()
						.addComponent(classInfoIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 67,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(classInfoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
						.addContainerGap()));
		classInfoPanelLayout.setVerticalGroup(classInfoPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(classInfoLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(classInfoIcon, javax.swing.GroupLayout.Alignment.TRAILING,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		sidePanel.add(classInfoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 250, 50));

		helpPanel.setBackground(new java.awt.Color(255, 255, 255));

		helpLabel.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
		helpLabel.setForeground(new java.awt.Color(0, 0, 153));
		helpLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		helpLabel.setText("Help");
		helpLabel.setIconTextGap(0);
		helpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(final java.awt.event.MouseEvent evt) {
				helpLabelMouseClicked(evt);
			}
		});

		helpIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		helpIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("assests/Blue_Help_Icon.png"))); // NOI18N
		helpIcon.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(final java.awt.event.MouseEvent evt) {
				helpLabelMouseClicked(evt);
			}
		});

		javax.swing.GroupLayout helpPanelLayout = new javax.swing.GroupLayout(helpPanel);
		helpPanel.setLayout(helpPanelLayout);
		helpPanelLayout
				.setHorizontalGroup(helpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(helpPanelLayout.createSequentialGroup()
								.addComponent(helpIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 67,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(helpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 166,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		helpPanelLayout.setVerticalGroup(helpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(helpLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(helpIcon, javax.swing.GroupLayout.Alignment.TRAILING,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		sidePanel.add(helpPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 250, 50));

		mainPanel.add(sidePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 750));

		changingViewPanel.setBackground(new java.awt.Color(102, 153, 255));

		scheduleNameLabel.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
		scheduleNameLabel.setForeground(new java.awt.Color(0, 0, 153));
		scheduleNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		scheduleNameLabel.setText("View Schedule");

		viewImportIcon.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
		viewImportIcon.setForeground(new java.awt.Color(0, 0, 153));
		viewImportIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("assests/Blue_Add_Schedule_Icon.png"))); // NOI18N
		viewImportIcon.setText("Import Schedule");

		viewExportIcon.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
		viewExportIcon.setForeground(new java.awt.Color(0, 0, 153));
		viewExportIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("assests/Blue_Export_Icon.png"))); // NOI18N
		viewExportIcon.setText("Export to .ics");

		viewScheduleTable.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
		viewScheduleTable.setGridColor(Color.gray);
		viewScheduleTable.getTableHeader().setReorderingAllowed(false);
		viewScheduleTable.setRowHeight(30);

		viewScheduleTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {{"7:00am", null, null, null, null, null, null, null},
						{"7:30am", null, null, null, null, null, null, null},
						{"8:00am", null, null, null, null, null, null, null},
						{"8:30am", null, null, null, null, null, null, null},
						{"9:00am", null, null, null, null, null, null, null},
						{"9:30am", null, null, null, null, null, null, null},
						{"10:00am", null, null, null, null, null, null, null},
						{"10:30am", null, null, null, null, null, null, null},
						{"11:00am", null, null, null, null, null, null, null},
						{"11:30am", null, null, null, null, null, null, null},
						{"12:00pm", null, null, null, null, null, null, null},
						{"12:30pm", null, null, null, null, null, null, null},
						{"1:00pm", null, null, null, null, null, null, null},
						{"1:30pm", null, null, null, null, null, null, null},
						{"2:00pm", null, null, null, null, null, null, null},
						{"2:30pm", null, null, null, null, null, null, null},
						{"3:00pm", null, null, null, null, null, null, null},
						{"3:30pm", null, null, null, null, null, null, null},
						{"4:00pm", null, null, null, null, null, null, null},
						{"4:30pm", null, null, null, null, null, null, null},
						{"5:00pm", null, null, null, null, null, null, null},
						{"5:30pm", null, null, null, null, null, null, null},
						{"6:00pm", null, null, null, null, null, null, null},
						{"6:30pm", null, null, null, null, null, null, null},
						{"7:00pm", null, null, null, null, null, null, null},
						{"7:30pm", null, null, null, null, null, null, null},
						{"8:00pm", null, null, null, null, null, null, null},
						{"8:30pm", null, null, null, null, null, null, null},
						{"9:00pm", null, null, null, null, null, null, null},

				},
				new String[] {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}));

		viewScheduleTable.setAutoscrolls(false);
		viewScheduleTable.setEnabled(false);
		viewScheduleTable.setRowSelectionAllowed(false);
		jScrollPane1.setViewportView(viewScheduleTable);

		javax.swing.GroupLayout changingViewPanelLayout = new javax.swing.GroupLayout(changingViewPanel);
		changingViewPanel.setLayout(changingViewPanelLayout);
		changingViewPanelLayout.setHorizontalGroup(changingViewPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(changingViewPanelLayout.createSequentialGroup().addGap(40, 40, 40)
						.addGroup(changingViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(changingViewPanelLayout.createSequentialGroup()
										.addComponent(scheduleNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 233,
												Short.MAX_VALUE)
										.addGap(466, 466, 466)
										.addGroup(changingViewPanelLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(viewImportIcon, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(viewExportIcon, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
								.addGroup(changingViewPanelLayout.createSequentialGroup().addGap(10, 10, 10)
										.addGroup(changingViewPanelLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jSeparator2).addComponent(jScrollPane1))))
						.addGap(60, 60, 60)));
		changingViewPanelLayout
				.setVerticalGroup(changingViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								changingViewPanelLayout.createSequentialGroup().addGap(40, 40, 40)
										.addGroup(changingViewPanelLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(viewImportIcon, javax.swing.GroupLayout.PREFERRED_SIZE,
														60, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(scheduleNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE,
														60, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 544,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(viewExportIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 53,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(22, 22, 22)));

		mainPanel.add(changingViewPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 1030, 750));

		changingComparePanel.setBackground(new java.awt.Color(102, 153, 255));

		scheduleNameLabel2.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
		scheduleNameLabel2.setForeground(new java.awt.Color(0, 0, 153));
		scheduleNameLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		scheduleNameLabel2.setText("Compare Schedules");

		compareImportIcon.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
		compareImportIcon.setForeground(new java.awt.Color(0, 0, 153));
		compareImportIcon
				.setIcon(new javax.swing.ImageIcon(getClass().getResource("assests/Blue_Add_Schedule_Icon.png"))); // NOI18N
		compareImportIcon.setText("Import Schedule");

		viewScheduleTable2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
		viewScheduleTable2.setGridColor(Color.gray);
		viewScheduleTable2.getTableHeader().setReorderingAllowed(false);
		viewScheduleTable2.setRowHeight(30);

		viewScheduleTable2.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {{"7:00am", null, null, null, null, null, null, null},
						{"7:30am", null, null, null, null, null, null, null},
						{"8:00am", null, null, null, null, null, null, null},
						{"8:30am", null, null, null, null, null, null, null},
						{"9:00am", null, null, null, null, null, null, null},
						{"9:30am", null, null, null, null, null, null, null},
						{"10:00am", null, null, null, null, null, null, null},
						{"10:30am", null, null, null, null, null, null, null},
						{"11:00am", null, null, null, null, null, null, null},
						{"11:30am", null, null, null, null, null, null, null},
						{"12:00pm", null, null, null, null, null, null, null},
						{"12:30pm", null, null, null, null, null, null, null},
						{"1:00pm", null, null, null, null, null, null, null},
						{"1:30pm", null, null, null, null, null, null, null},
						{"2:00pm", null, null, null, null, null, null, null},
						{"2:30pm", null, null, null, null, null, null, null},
						{"3:00pm", null, null, null, null, null, null, null},
						{"3:30pm", null, null, null, null, null, null, null},
						{"4:00pm", null, null, null, null, null, null, null},
						{"4:30pm", null, null, null, null, null, null, null},
						{"5:00pm", null, null, null, null, null, null, null},
						{"5:30pm", null, null, null, null, null, null, null},
						{"6:00pm", null, null, null, null, null, null, null},
						{"6:30pm", null, null, null, null, null, null, null},
						{"7:00pm", null, null, null, null, null, null, null},
						{"7:30pm", null, null, null, null, null, null, null},
						{"8:00pm", null, null, null, null, null, null, null},
						{"8:30pm", null, null, null, null, null, null, null},
						{"9:00pm", null, null, null, null, null, null, null},
						}, new String[] {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}));
		viewScheduleTable2.setAutoscrolls(false);
		viewScheduleTable2.setEnabled(false);
		viewScheduleTable2.setRowSelectionAllowed(false);
		jScrollPane5.setViewportView(viewScheduleTable2);

		javax.swing.GroupLayout changingComparePanelLayout = new javax.swing.GroupLayout(changingComparePanel);
		changingComparePanel.setLayout(changingComparePanelLayout);
		changingComparePanelLayout.setHorizontalGroup(changingComparePanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, changingComparePanelLayout.createSequentialGroup()
						.addContainerGap(50, Short.MAX_VALUE)
						.addGroup(changingComparePanelLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
								.addGroup(changingComparePanelLayout.createSequentialGroup().addGap(14, 14, 14)
										.addComponent(scheduleNameLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 572,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(compareImportIcon))
								.addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 933, Short.MAX_VALUE)
								.addComponent(jSeparator3))
						.addGap(47, 47, 47)));
		changingComparePanelLayout.setVerticalGroup(
				changingComparePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(changingComparePanelLayout.createSequentialGroup().addGap(40, 40, 40)
								.addGroup(changingComparePanelLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(compareImportIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(scheduleNameLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18).addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE,
										548, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(57, Short.MAX_VALUE)));

		mainPanel.add(changingComparePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 1030, 750));

		changingHelpPanel.setBackground(new java.awt.Color(102, 153, 255));

		scheduleNameLabel4.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
		scheduleNameLabel4.setForeground(new java.awt.Color(0, 0, 153));
		scheduleNameLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		scheduleNameLabel4.setText("Help");

		jLabel1.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(0, 0, 153));
		jLabel1.setText("View");

		jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(0, 0, 153));
		jLabel2.setText(
				"The view allows you to upload your schedule in the top right corner by logging into your myBanner account with your Blackboard credentials. ");

		jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
		jLabel3.setForeground(new java.awt.Color(0, 0, 153));
		jLabel3.setText(
				"Once you enter these in, you will see your schedule show up. You can then download your calender as an .ics file to upload to your online calendar.");

		jLabel4.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
		jLabel4.setForeground(new java.awt.Color(0, 0, 153));
		jLabel4.setText("Compare");

		jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
		jLabel5.setForeground(new java.awt.Color(0, 0, 153));
		jLabel5.setText(
				"The compare section allows you and friends to upload your schedules and compare them to find times to meet.  ");

		jLabel7.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
		jLabel7.setForeground(new java.awt.Color(0, 0, 153));
		jLabel7.setText("Info");

		jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
		jLabel8.setForeground(new java.awt.Color(0, 0, 153));
		jLabel8.setText("The info section shows the consise student schedule view. ");

		javax.swing.GroupLayout changingHelpPanelLayout = new javax.swing.GroupLayout(changingHelpPanel);
		changingHelpPanel.setLayout(changingHelpPanelLayout);
		changingHelpPanelLayout.setHorizontalGroup(changingHelpPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(changingHelpPanelLayout.createSequentialGroup().addGap(40, 40, 40)
						.addGroup(changingHelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(scheduleNameLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 133,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(changingHelpPanelLayout.createSequentialGroup().addGap(30, 30, 30)
										.addGroup(changingHelpPanelLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 930,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 159,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(changingHelpPanelLayout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(jSeparator1)
														.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE,
																91, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(jLabel3,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 91,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.PREFERRED_SIZE, 930,
														javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(30, Short.MAX_VALUE)));
		changingHelpPanelLayout
				.setVerticalGroup(
						changingHelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(changingHelpPanelLayout.createSequentialGroup().addGap(40, 40, 40)
										.addComponent(scheduleNameLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(33, 33, 33)
										.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(93, 93, 93)
										.addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(92, 92, 92)
										.addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(158, Short.MAX_VALUE)));

		mainPanel.add(changingHelpPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 1030, 750));

		changingInfoPanel.setBackground(new java.awt.Color(102, 153, 255));

		scheduleNameLabel3.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
		scheduleNameLabel3.setForeground(new java.awt.Color(0, 0, 153));
		scheduleNameLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		scheduleNameLabel3.setText("Class Information");

		infoHTMLView.setEditable(false);

		JScrollPane htmlScroll = new JScrollPane(infoHTMLView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		jScrollPane3.setViewportView(htmlScroll);

		javax.swing.GroupLayout changingInfoPanelLayout = new javax.swing.GroupLayout(changingInfoPanel);
		changingInfoPanel.setLayout(changingInfoPanelLayout);
		changingInfoPanelLayout.setHorizontalGroup(changingInfoPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(changingInfoPanelLayout.createSequentialGroup().addGap(21, 21, 21).addGroup(
						changingInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								changingInfoPanelLayout.createSequentialGroup()
										.addComponent(scheduleNameLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 332,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(658, 658, 658))
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										changingInfoPanelLayout
												.createSequentialGroup().addComponent(jScrollPane3,
														javax.swing.GroupLayout.DEFAULT_SIZE, 997, Short.MAX_VALUE)
												.addContainerGap()))));
		changingInfoPanelLayout.setVerticalGroup(
				changingInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						changingInfoPanelLayout.createSequentialGroup().addGap(40, 40, 40)
								.addComponent(scheduleNameLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
								.addGap(35, 35, 35)));

		mainPanel.add(changingInfoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 1030, 750));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));

		cardPanel = new JPanel();

		mainPanel.add(cardPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 1030, 750));

		cardLayout = new CardLayout();
		cardPanel.setLayout(cardLayout);

		cardPanel.add(this.changingViewPanel, "1");
		cardPanel.add(this.changingComparePanel, "2");
		cardPanel.add(this.changingInfoPanel, "3");
		cardPanel.add(this.changingHelpPanel, "4");

		cardLayout.show(cardPanel, "1");

		pack();
	}

	/**
	 * Changes the view to the View panel.
	 * @param evt The event of clicking the label
	 */
	private void viewLabelMouseClicked(final java.awt.event.MouseEvent evt) {
		setColor(this.sidePanels, this.viewPanel);
		this.cardLayout.show(cardPanel, "1");
	}

	/**
	 * Changes the view to the View panel.
	 * @param evt The event of clicking the label
	 */
	private void compareIconMouseClicked(final java.awt.event.MouseEvent evt) {
		setColor(this.sidePanels, this.comparePanel);
		this.cardLayout.show(cardPanel, "2");
	}

	/**
	 * Changes the view to the View panel.
	 * @param evt The event of clicking the label
	 */
	private void classInfoIconMouseClicked(final java.awt.event.MouseEvent evt) {
		setColor(this.sidePanels, this.classInfoPanel);
		this.cardLayout.show(cardPanel, "3");
	}

	/**
	 * Changes the view to the View panel.
	 * @param evt The event of clicking the label
	 */
	private void helpLabelMouseClicked(final java.awt.event.MouseEvent evt) {
		setColor(this.sidePanels, this.helpPanel);
		this.cardLayout.show(cardPanel, "4");
	}

	/**
	 * Sets the color of a particular navigation panel.
	 * @param panels The navigation panels
	 * @param panel The panel being colored
	 */
	void setColor(final JPanel[] panels, final JPanel panel) {
		for (JPanel panel1 : panels) {
			panel1.setBackground(new Color(255, 255, 255));
		}
		panel.setBackground(new Color(204, 204, 204));
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(final String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(NetbeansGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(NetbeansGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(NetbeansGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(NetbeansGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new NetbeansGUI().setVisible(true);
			}
		});
	}

	/** Compare Panel View. */
	private javax.swing.JPanel changingComparePanel;
	
	/** Help Panel View. */
	private javax.swing.JPanel changingHelpPanel;
	
	/** Info Panel View. */
	private javax.swing.JPanel changingInfoPanel;
	
	/** View Panel View. */
	private javax.swing.JPanel changingViewPanel;
	
	/** Icon for Info button. */
	private javax.swing.JLabel classInfoIcon;
	
	/** Label for Info button. */
	private javax.swing.JLabel classInfoLabel;
	
	/** Panel button for Info. */
	private javax.swing.JPanel classInfoPanel;
	
	/** Icon for Compare button. */
	private javax.swing.JLabel compareIcon;
	
	/** Icon for Import button on Compare page. */
	protected javax.swing.JLabel compareImportIcon;
	
	/** Label for Compare page button. */
	private javax.swing.JLabel compareLabel;
	
	/** Compare panel as button. */
	private javax.swing.JPanel comparePanel;
	
	/** Icon for help button. */
	private javax.swing.JLabel helpIcon;
	
	/** Label for help button. */
	private javax.swing.JLabel helpLabel;
	
	/** Panel as help button. */
	private javax.swing.JPanel helpPanel;
	
	/** HTML view in Class Info page. */
	protected javax.swing.JEditorPane infoHTMLView;
	
	/** Help instruction 1. */
	private javax.swing.JLabel jLabel1;
	
	/** Help instruction 2. */
	private javax.swing.JLabel jLabel2;
	
	/** Help instruction 3. */
	private javax.swing.JLabel jLabel3;
	
	/** Help instruction 4. */
	private javax.swing.JLabel jLabel4;
	
	/** Help instruction 5. */
	private javax.swing.JLabel jLabel5;
	
	/** Help instruction 6. */
	private javax.swing.JLabel jLabel7;
	
	/** Help instruction 7. */
	private javax.swing.JLabel jLabel8;
	
	/** Scroll for View table. */
	private javax.swing.JScrollPane jScrollPane1;
	
	/** Scroll for Compare table. */
	private javax.swing.JScrollPane jScrollPane3;

	/** Scroll for HTML view. */
	private javax.swing.JScrollPane jScrollPane5;
	
	/** Separator line for View header. */
	private javax.swing.JSeparator jSeparator1;
	
	/** Separator line for Compare header. */
	private javax.swing.JSeparator jSeparator2;
	
	/** Separator line for Class info. */
	private javax.swing.JSeparator jSeparator3;
	
	/** Main window panel. */
	private javax.swing.JPanel mainPanel;
	
	/** View header label. */
	private javax.swing.JLabel scheduleNameLabel;
	
	/** Compare header label. */
	private javax.swing.JLabel scheduleNameLabel2;
	
	/** Class info header label. */
	private javax.swing.JLabel scheduleNameLabel3;
	
	/** Class help header label. */
	private javax.swing.JLabel scheduleNameLabel4;
	
	/** Side navigation panel. */
	private javax.swing.JPanel sidePanel;
	
	/** View export button icon. */
	protected javax.swing.JLabel viewExportIcon;
	
	/** Icon for view button. */
	private javax.swing.JLabel viewIcon;
	
	/** View import button icon. */
	protected javax.swing.JLabel viewImportIcon;
	
	/** Label for import button. */
	private javax.swing.JLabel viewLabel;
	
	/** Panel button for view page. */
	private javax.swing.JPanel viewPanel;
	
	/** Schedule table for view page. */
	protected javax.swing.JTable viewScheduleTable;
	
	
	/** Schedule table for compare page. */
	protected javax.swing.JTable viewScheduleTable2;

	/** Manages the navigation pages. */
	protected CardLayout cardLayout;
	
	/** Displays the different pages. */
	protected javax.swing.JPanel cardPanel;

	// End of variables declaration//GEN-END:variables
}
