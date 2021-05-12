package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.pageObjectModels.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	private String baseURL;
	private static final String username = "username";
	private static final String password = "password";

	private static final String title = "Title";
	private static final String description = "description";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	//Authentication And Authorization
	@Test
	public void getLoginPage() throws InterruptedException {
		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("George", "Marcus", username, password);

		driver.get(baseURL + "/login");
		assertEquals("Login", driver.getTitle());

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		assertEquals(baseURL + "/home", driver.getCurrentUrl());
	}

	@Test
	public void redirectLoginWhenUnAuthorizedAccess() {
		driver.get(baseURL + "/home");
		assertEquals(baseURL + "/login", driver.getCurrentUrl());
		assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/file-upload");
		assertEquals(baseURL + "/login", driver.getCurrentUrl());
		assertEquals("Login", driver.getTitle());
	}
}
