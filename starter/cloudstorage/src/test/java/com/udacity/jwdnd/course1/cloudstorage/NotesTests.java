package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pageObjectModels.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pageObjectModels.NotesTab;
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
class NotesTests {

	@LocalServerPort
	private int port;
	private String baseURL;

	private WebDriver driver;

	private static final String username = "username";
	private static final String password = "password";

	private static final String title = "Title";
	private static final String description = "description";

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
	public void createNote() throws InterruptedException {
		NotesTab notesTab = new NotesTab(driver);

		notesTab.showNotes();
		notesTab.newNote(title, description);

		Thread.sleep(1000);

		int noteSize = notesTab.getNoteListSize();
		assertEquals(1, noteSize);
	}

	@Test
	public void editNote() throws InterruptedException {
		String newTitle = "New Title";
		String newDescription = "New Description";

		NotesTab notesTab = new NotesTab(driver);
		notesTab.showNotes();
		notesTab.newNote(title, description);

		Thread.sleep(1000);

		assertTrue(notesTab.editNote(0, newTitle, newDescription));

		Thread.sleep(1000);

		int noteSize = notesTab.getNoteListSize();
		assertEquals(1, noteSize);
	}

	@Test
	public void deleteNote() throws InterruptedException {
		NotesTab notesTab = new NotesTab(driver);
		notesTab.showNotes();
		notesTab.newNote(title, description);

		Thread.sleep(1000);

		assertTrue(notesTab.deleteNote(0));

		Thread.sleep(1000);

		int noteSize = notesTab.getNoteListSize();
		assertEquals(0, noteSize);


	}
}
