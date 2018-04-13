package scheduler;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class LoginView implements ActionListener {

	private BannerSchedScrapper scrapper;

	private String[] values = { "201920", "201910", "201830", "201820", "201810", "201730", "201720", "201710",
			"201630", "201620", "201610", "201530", "201520", "201510", "201430", "201420", "201410", "201330",
			"201320", "201310", "201230", "201220", "201210", "201130", "201120", "201110", "201030", "201020",
			"201010", "200930", "200920", "200910", "200830", "200820", "200810" };
	private String[] display = { "Winter 2019", "Fall 2018", "Spring/Summer 2018", "Winter 2018", "Fall 2017",
			"Spring/Summer 2017", "Winter 2017", "Fall 2016", "Spring/Summer 2016", "Winter 2016", "Fall 2015",
			"Spring/Summer 2015", "Winter 2015", "Fall 2014", "Spring/Summer 2014", "Winter 2014", "Fall 2013",
			"Spring/Summer 2013", "Winter 2013", "Fall 2012", "Spring/Summer 2012", "Winter 2012", "Fall 2011",
			"Spring/Summer 2011", "Winter 2011", "Fall 2010", "Spring/Summer 2010", "Winter 2010", "Fall 2009",
			"Spring/Summer 2009", "Winter 2009", "Fall 2008", "Spring/Summer 2008", "Winter 2008", "Fall 2007" };

	private JFrame frame;

	private JLabel userLabel;
	private JLabel passwordLabel;
	private JLabel loginDesc;
	private JLabel loginStatus;
	private JTextField userText;
	private JPasswordField passwordText;
	private JButton loginButton;
	private JComboBox<String> semList;

	public LoginView() {

		frame = new JFrame("Grand Valley Scheduler");
		frame.setSize(300, 190);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		JPanel panel = new JPanel();
		frame.add(panel);

		panel.setLayout(null);

		loginDesc = new JLabel("Login using your network ID and password");
		loginDesc.setBounds(10, 10, 300, 25);
		panel.add(loginDesc);

		userLabel = new JLabel("Username");
		userLabel.setBounds(10, 40, 80, 25);
		panel.add(userLabel);

		userText = new JTextField(20);
		userText.setBounds(100, 40, 160, 25);
		panel.add(userText);

		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 70, 80, 25);
		panel.add(passwordLabel);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 70, 160, 25);
		panel.add(passwordText);

		semList = new JComboBox<String>(this.display);
		semList.setBounds(40, 130, 130, 25);
		panel.add(semList);

		loginButton = new JButton("Login");
		loginButton.setBounds(180, 130, 80, 25);
		loginButton.addActionListener(this);
		panel.add(loginButton);

		loginStatus = new JLabel("Not logged in");
		loginStatus.setBounds(10, 97, 300, 25);
		loginStatus.setForeground(Color.RED);
		panel.add(loginStatus);
		
		frame.getRootPane().setDefaultButton(loginButton);
		loginButton.requestFocus();

		frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == loginButton) {
			System.out.println("Login");
			System.out.println("User: " + userText.getText());
			// System.out.println("Pw: " + String.valueOf(passwordText.getPassword()));

			if (userText.getText().isEmpty()) {
				loginStatus.setText("No username input");
			} else if (passwordText.getPassword().length == 0) {
				loginStatus.setText("No password input");
			} else {

				try {
					if (scrapper == null) {
						scrapper = new BannerSchedScrapper(userText.getText(),
								String.valueOf(passwordText.getPassword()));
					}
					System.out.println(scrapper.getScheduleAsText(this.values[semList.getSelectedIndex()]));
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				} catch (FailingHttpStatusCodeException e1) {
					// if a wrong page is retrieved
					e1.printStackTrace();
				} catch (MalformedURLException e1) {
					// If an invalid URL is opened
					e1.printStackTrace();
				} catch (IOException e1) {
					// Happens when a wrong username or pw is input
					System.out.println("Invalid Username and Password");
					loginStatus.setText("Invalid Username/Password");

				} catch (IndexOutOfBoundsException e1) {
					// Happens when an invalid semester is input
					System.out.println("Invalid Semester");
					loginStatus.setText("Invalid Semester");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		}

	}

	public static void main(String[] args) {
		LoginView login = new LoginView();
	}

}