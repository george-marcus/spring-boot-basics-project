package com.udacity.jwdnd.course1.cloudstorage.pageObjectModels;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement usernameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "login-error-msg")
    private WebElement loginError;

    private final WebDriverWait wait;

    public LoginPage(final WebDriver driver) {
        this.wait = new WebDriverWait(driver, 60);

        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.id("submit-button")));

        PageFactory.initElements(driver, this);
    }

    public void login(final String username, final String password) {

        wait.until(ExpectedConditions
                .elementToBeClickable(submitButton));

        this.usernameInput.clear();
        this.usernameInput.sendKeys(username);

        this.passwordInput.clear();
        this.passwordInput.sendKeys(password);

        submitButton.click();
    }

    public Boolean hasErrorLoggingIn(){

        wait.until(ExpectedConditions
                .elementToBeClickable(loginError));

        return loginError.isDisplayed();
    }
}