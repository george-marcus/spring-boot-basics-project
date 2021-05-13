package com.udacity.jwdnd.course1.cloudstorage.pageObjectModels;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {
    
    @FindBy(id = "inputFirstName")
    private WebElement firstName;

    @FindBy(id = "inputLastName")
    private WebElement lastName;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    private final WebDriver driver;
    private final WebDriverWait wait;

    public SignupPage(final WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 1000);

        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.id("submit-button")));

        PageFactory.initElements(driver, this);
    }

    public void signup(final String firstName, final String lastName, final String username, final String password) {

        submitButton = wait.until(ExpectedConditions
                                .elementToBeClickable(submitButton));

        this.firstName.clear();
        this.firstName.sendKeys(firstName);

        this.lastName.clear();
        this.lastName.sendKeys(lastName);

        this.username.clear();
        this.username.sendKeys(username);

        this.password.clear();
        this.password.sendKeys(password);

        submitButton.click();
    }
}