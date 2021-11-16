package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pojo.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pojo.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pojo.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseURL;

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
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
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
		String firstname = "Selenium";
		String lastname = "Test";
		String username = "jasetest";
		String password = "password";

		driver.get(baseURL+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname, lastname, username, password);

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		//check that user has been redirected to home
		Assertions.assertEquals("Home", driver.getTitle());

		//check that user is redirected to login page on logout
		HomePage homePage = new HomePage(driver);
		homePage.logout();
		Assertions.assertEquals("Login", driver.getTitle());
	}

}
