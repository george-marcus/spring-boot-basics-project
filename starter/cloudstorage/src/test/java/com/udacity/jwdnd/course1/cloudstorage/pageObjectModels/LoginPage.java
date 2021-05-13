package com.udacity.jwdnd.course1.cloudstorage.pageObjectModels;

import org.openqa.selenium.*;
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
    private final JavascriptExecutor javascriptExecutor;

    public LoginPage(final WebDriver driver) {
        this.wait = new WebDriverWait(driver, 1000);
        this.javascriptExecutor = (JavascriptExecutor)driver;

        PageFactory.initElements(driver, this);
    }

    public void login(final String username, final String password) {

        submitButton = wait.until(ExpectedConditions
                                .elementToBeClickable(submitButton));

        this.usernameInput.clear();
        this.usernameInput.sendKeys(username);

        this.passwordInput.clear();
        this.passwordInput.sendKeys(password);

        javascriptExecutor
                .executeScript("arguments[0].click();", submitButton);
    }
}