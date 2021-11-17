package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pojo.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pojo.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pojo.NotePoj;
import com.udacity.jwdnd.course1.cloudstorage.pojo.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:drop_all.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseURL;

	private String firstname;
	private String lastname;
	private String username;
	private String password;

	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;

	@Autowired
	private UserService userService;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.baseURL = "http://localhost:" + this.port;
		this.driver = new ChromeDriver();

		this.firstname = "Selenium";
		this.lastname = "Test";
		this.username = "jasetest";
		this.password = "password";

		this.signupPage = new SignupPage(driver);
		this.loginPage = new LoginPage(driver);
		this.homePage = new HomePage(driver);
	}

	@AfterEach
	public void afterEach() throws InterruptedException {
		if (this.driver != null) {
			Thread.sleep(5000);
			driver.quit();
		}
	}

	@Test
	void getLoginPage() {
		driver.get(this.baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	void testUnauthorizedAccess() {
		String[] urls = {"home", "files", "credentials", "notes"};
		for (String url: urls) {
			driver.get(this.baseURL + "/"+ url); //wondering how I could use a wild card instead
			Assertions.assertEquals("Login", driver.getTitle());
		}

	}

	@Test
	void testAuthentication() {
		driver.get(baseURL+"/signup");
		signupPage.signup(firstname, lastname, username, password);

		driver.get(baseURL + "/login");
		loginPage.login(username, password);

		//check that user has been redirected to home
		Assertions.assertEquals("Home", driver.getTitle());

		//check that user is redirected to login page on logout
		homePage.logout();
		Assertions.assertEquals("Login", driver.getTitle());

		//check that home page is no longer accessible
		driver.get(this.baseURL + "/home"); //wondering how I could use a wild card instead
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * Test that note is created and verify it is displayed.
	 */
	@Test
	void testNoteCreationAndVisiblity() {
		signUpAndLogin();

		String noteTitle = "My note title";
		String noteDesc  = "My note description";

		NotePoj notePoj = new NotePoj(driver);
		notePoj.addNote(noteTitle, noteDesc);

		//check that note is added successfully
		driver.get(baseURL + "/home");
		NotePoj notePoj1 = new NotePoj(driver);
		notePoj1.openTab();

		Assertions.assertEquals(true, notePoj1.isNoteTableDataExists());
		Assertions.assertEquals(noteTitle, notePoj1.getNoteTitle().getText());
		Assertions.assertEquals(noteDesc, notePoj1.getNoteDesc().getText());

	}

	/**
	 * Test that note is edited and still visible
	 */
	@Test
	void testNoteEditAndVisibility() {
		signUpAndLogin();
		String noteTitle = "My note title";
		String noteDesc  = "My note description";

		NotePoj notePoj = new NotePoj(driver);
		notePoj.addNote(noteTitle, noteDesc);

		driver.get(baseURL + "/home");

		notePoj.editNote(noteTitle + " edited", noteDesc);
//		//check that note displays
		driver.get(baseURL + "/home");
		NotePoj notePoj1 = new NotePoj(driver);
		notePoj1.openTab();

		Assertions.assertEquals(noteTitle + " edited", notePoj1.getNoteTitle().getText());
	}

	/**
	 * Test note delete and verify it no longer displays.
	 */
	@Test
	public void testNoteDeleteAndVisiblity() {
		signUpAndLogin();
		String noteTitle = "My note title";
		String noteDesc  = "My note description";

		NotePoj notePoj = new NotePoj(driver);
		//add note
		notePoj.addNote(noteTitle, noteDesc);

		//navigate to home page
		driver.get(baseURL + "/home");

		//delete note
		notePoj.deleteNote();

		//navigate to home page
		driver.get(baseURL + "/home");

		NotePoj notePoj1 = new NotePoj(driver);

    	//check that note no longer exists
		Assertions.assertFalse(notePoj1.isNoteTableDataExists());

	}

	private void signUpAndLogin() {
		driver.get(baseURL+"/signup");
		signupPage.signup(firstname, lastname, username, password);

		driver.get(baseURL + "/login");
		loginPage.login(username, password);
	}

}
