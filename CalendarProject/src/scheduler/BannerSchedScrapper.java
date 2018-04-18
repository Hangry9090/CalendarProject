package scheduler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

/**
 * Creates a scraping tool for retrieving HTML elements from Banner.
 * 
 * @author Alan Sisouphone
 * @author Marshal Brummel
 * @author Jake Walton
 * @version 2.0
 *
 */
public class BannerSchedScrapper {

	/** Client which navigates GVSU's Banner page. */
	private final WebClient webClient = new WebClient(BrowserVersion.CHROME);

	/** Current login address for Banner without Recaptcha. */
	private String loginURL = "https://gvsu.edu/s/0DE";

	/** Address to view Concise Student Schedule. */
	private String schedURL = "https://mybanner.gvsu.edu/PROD/bwskcrse.P_CrseSchdDetl";

	/** Address to change the current Concise Student Schedule Semester. */
	private String changeSchedURL = "https://mybanner.gvsu.edu/PROD/bwskflib.P_SelDefTerm";

	/** Address to return to the main menu of Banner. */
	private String mainMenuURL = "https://mybanner.gvsu.edu/PROD/twbkwbis.P_GenMenu?name=bmenu.P_MainMnu";

	/**
	 * Uses the input credentials to login to GVSU's Banner website.
	 * 
	 * @param username
	 *            The Banner username of the student.
	 * @param password
	 *            The Banner password of the student
	 * @throws FailingHttpStatusCodeException
	 *             If the page cannot be retrieved
	 * @throws MalformedURLException
	 *             If the URL fails to load
	 * @throws IOException
	 *             If the user credentials are invalid
	 */
	public BannerSchedScrapper(final String username, final String password)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {

		disableLogs();
		login(username, password);
	}

	/**
	 * Logins to GVSU Banner with the input credentials.
	 * 
	 * @param username
	 *            The Banner username of the student
	 * @param password
	 *            The Banner password of the student
	 * @throws FailingHttpStatusCodeException
	 *             If the page cannot be retrieved
	 * @throws MalformedURLException
	 *             If the URL fails to load
	 * @throws IOException
	 *             If the user credentials are invalid
	 */
	private void login(final String username, final String password)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {

		// Allows client to build cookies on login
		webClient.getCookieManager().setCookiesEnabled(true);
		webClient.getOptions().setJavaScriptEnabled(true);

		HtmlPage page = (HtmlPage) webClient.getPage(this.loginURL);

		// Finds login form and inputs login info
		HtmlForm form = page.getHtmlElementById("loginForm");
		form.getInputByName("username").setValueAttribute(username);
		form.getInputByName("password").setValueAttribute(password);

		// Finds the sessionID number from current HTML page
		String sessionDiv = page.getElementByName("sessionDataKey").asXml();
		String[] divInfo = sessionDiv.split(" ");
		sessionDiv = divInfo[3].replaceAll("value=", "").replaceAll("\"", "");

		// Inputs sessionID along with username and password to login
		form.getInputByName("sessionDataKey").setValueAttribute(sessionDiv);

		// Clicks the submit button
		page = form.getInputByValue("Sign In").click();

		if (!page.getBaseURI().toString().equals(this.mainMenuURL)) {
			throw new IOException();
		}
	}

	/**
	 * Disables unnecessary logging information.
	 */
	private void disableLogs() {

		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http.impl.execchain.RetryExec").setLevel(Level.OFF);
	}

	/**
	 * Finds the concise student schedule table of a particular semester.
	 * 
	 * @param semValue
	 *            A valid semester year value
	 * @return A HTMLTable Element which holds the student's schedule
	 * @throws FailingHttpStatusCodeException
	 *             If the page cannot be retrieved
	 * @throws MalformedURLException
	 *             If the URL fails to load
	 * @throws IOException
	 *             If the user credentials are invalid
	 */
	private HtmlTable getScheduleTable(final String semValue)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {

		HtmlPage page = (HtmlPage) webClient.getPage(this.schedURL);

		// Not the semester selection page
		if (page.getForms().size() == 1) {

			// Redirects to the semester selection page
			page = (HtmlPage) webClient.getPage(this.changeSchedURL);
		}

		// Selects the appropriate semester from dropdown menu
		HtmlForm form = (HtmlForm) page.getForms().get(1);
		HtmlSelect select = (HtmlSelect) page.getElementByName("term_in");
		select.setSelectedAttribute(select.getOptionByValue(semValue), true);

		// Must be enabled in order to proceed to next page
		webClient.getOptions().setUseInsecureSSL(true);
		page = form.getInputByValue("Submit").click();

		// Ensure we are on the schedule page
		page = (HtmlPage) webClient.getPage(this.schedURL);
		webClient.getOptions().setUseInsecureSSL(false);

		HtmlTable table = (HtmlTable) page.getByXPath("//table[@class='datadisplaytable']").get(1);

		return table;
	}

	/**
	 * Retrieves the student schedule as just text.
	 * 
	 * @param semValue
	 *            A valid semester year value
	 * @throws FailingHttpStatusCodeException
	 *             If the page cannot be retrieved
	 * @throws MalformedURLException
	 *             If the URL fails to load
	 * @throws IOException
	 *             If the user credentials are invalid
	 * @return The student schedule table as readable text
	 */
	public String getScheduleAsText(final String semValue)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		return getScheduleTable(semValue).asText();
	}

	/**
	 * Retrieves the student schedule as an HTML output.
	 * 
	 * @param semValue
	 *            A valid semester year value
	 * @return The student schedule as a HTML code
	 * @throws FailingHttpStatusCodeException
	 *             If the page cannot be retrieved
	 * @throws MalformedURLException
	 *             If the URL fails to load
	 * @throws IOException
	 *             If the user credentials are invalid
	 */
	public String getScheduleAsHTML(final String semValue)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		return getScheduleTable(semValue).asXml();
	}

	/**
	 * Sees if the input semester value is a valid semester of the student.
	 * 
	 * @param semValue A valid semester year value
	 * @return Whether the schedule is valid or not
	 */
	public boolean isValidSemesterValue(final String semValue) {
		try {
			getScheduleTable(semValue);
		} catch (Exception e) {
			return false;
		}
		return true;

	}

	/**
	 * Closes the current web client.
	 */
	public void closeClient() {
		this.webClient.close();
	}

}
