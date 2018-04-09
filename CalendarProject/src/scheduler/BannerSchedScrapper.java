package scheduler;

import java.io.Console;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

public class BannerSchedScrapper {
	
	/** Client which navigates GVSU's Banner page. */
	private final WebClient webClient = new WebClient(BrowserVersion.CHROME);

	/** Current login address for Banner without Recaptcha. */
	private String loginURL = "https://gvsu.edu/s/0DE";
	
	/** Address to view Concise Student Schedule. */
	private String schedURL = "https://mybanner.gvsu.edu/PROD/bwskcrse.P_CrseSchdDetl";
	
	/** Address to change the current Concise Student Schedule Semester. */
	private String changeSchedURL = "https://mybanner.gvsu.edu/PROD/bwskflib.P_SelDefTerm";

	private String mainMenuURL = "https://mybanner.gvsu.edu/PROD/twbkwbis.P_GenMenu?name=bmenu.P_MainMnu";

	
	/**
	 * Uses the input credentials to login to GVSU's Banner website.
	 * @param username
	 * @param password
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public BannerSchedScrapper(String username, String password)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {

		disableLogs();
		login(username, password);
	}

	/**
	 * Logins to GVSU Banner with the input credentials.
	 * @param username
	 * @param password
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private void login(String username, String password)
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
	 * @param semValue
	 * @return
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private HtmlTable getScheduleTable(String semValue)
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
	 * Finds the valid semester input values from the dropdown menu bar.
	 * @return
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public ArrayList<String> getSemesterValues()
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {

		ArrayList<String> values = new ArrayList<String>();

		HtmlPage page = (HtmlPage) webClient.getPage(this.schedURL);

		HtmlSelect select = (HtmlSelect) page.getElementByName("term_in");

		Document doc = Jsoup.parse(select.asXml());

		Elements options = doc.getElementsByAttributeValue("name", "term_in").get(0).children();

		for (Element option : options) {
			values.add(option.attr("value"));
		}

		return values;

	}

	/**
	 * Finds semester option details of the dropdown menu.
	 * @return
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public ArrayList<String> getSemesterOptions()
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		ArrayList<String> semOptions = new ArrayList<String>();

		HtmlPage page = (HtmlPage) webClient.getPage(this.schedURL);

		HtmlSelect select = (HtmlSelect) page.getElementByName("term_in");

		Document doc = Jsoup.parse(select.asXml());

		Elements options = doc.select("select > option");

		for (Element option : options) {

			semOptions.add(option.text().replace(" (View only)", ""));
		}

		return semOptions;
	}

	/**
	 * Retrieves the student schedule as just text.
	 * @param semValue
	 * @return
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public String getScheduleAsText(String semValue)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		return getScheduleTable(semValue).asText();
	}

	/**
	 * Retrieves the student schedule as an HTML output.
	 * @param semValue
	 * @return
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public String getScheduleAsHTML(String semValue)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		return getScheduleTable(semValue).asXml();
	}

	/**
	 * Sees if the input semester value is a valid semester of the student.
	 * @param semValue
	 * @return
	 */
	public boolean isValidSemesterValue(String semValue) {
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
	


	public static void main(String[] args) {

		// Please enter your Banner username and password for "username" and "password" below

		
		try {
			BannerSchedScrapper login = new                                                                   BannerSchedScrapper("", "");

			System.out.println("These are valid input values to specify a semester:");
			System.out.println(login.getSemesterValues());
			System.out.println("===========================================");
			System.out.println("These are the descriptors for the semester values:");
			System.out.println(login.getSemesterOptions());
			System.out.println("===========================================");
			System.out.println("This is the HTML of the table element:");
			System.out.println(login.getScheduleAsHTML("201920"));
			System.out.println("===========================================");
			System.out.println("This is the table of the student schedule as a string:");
			System.out.println(login.getScheduleAsText("201920"));

			login.closeClient();
			System.out.println("=========Success========");

		} catch (Exception e) {
			 e.printStackTrace();
		}

	}

}
