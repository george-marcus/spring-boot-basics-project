package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pageObjectModels.CredentialsTab;
import com.udacity.jwdnd.course1.cloudstorage.pageObjectModels.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pageObjectModels.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:cleanDB.sql")
class CredentialsTests {

	@LocalServerPort
	private int port;
	private String baseURL;

	private WebDriver driver;

	private static final String url = "url";
	private static final String username = "username";
	private static final String password = "password";


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() throws InterruptedException {
		driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		Thread.sleep(1000);

		if(loginPage.hasErrorLoggingIn()){

			driver.get(baseURL + "/signup");
			SignupPage signupPage = new SignupPage(driver);
			signupPage.signup("George", "Marcus", username, password);
			loginPage.login(username, password);
		}
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void createCredential() throws InterruptedException {
		CredentialsTab credentialsTab = new CredentialsTab(driver);

		credentialsTab.showCredentials();
		credentialsTab.newCredential(url, username, password);

		Thread.sleep(1000);

		int credentials = credentialsTab.getCredentialListSize();
		assertEquals(1, credentials);
	}

	@Test
	public void editCredential() throws InterruptedException {
		String newUrl = "new url";
		String newUsername = "new username";
		String newPassword = "new password";

		CredentialsTab credentialsTab = new CredentialsTab(driver);
		credentialsTab.showCredentials();
		credentialsTab.newCredential(url, username, password);

		Thread.sleep(1000);

		assertTrue(credentialsTab.editCredential(0, newUrl, newUsername, newPassword));

		Thread.sleep(1000);

		int credentials = credentialsTab.getCredentialListSize();
		assertEquals(1, credentials);
	}

	@Test
	public void deleteCredential() throws InterruptedException {
		CredentialsTab credentialsTab = new CredentialsTab(driver);
		credentialsTab.showCredentials();
		credentialsTab.newCredential(url, username, password);

		Thread.sleep(1000);
		assertTrue(credentialsTab.deleteCredential(0));

		Thread.sleep(1000);
		int credentials = credentialsTab.getCredentialListSize();
		assertEquals(0, credentials);
	}
}
