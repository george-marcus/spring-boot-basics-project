package com.udacity.jwdnd.course1.cloudstorage.pageObjectModels;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(className = "logout")
    private WebElement logoutButton;

    private final JavascriptExecutor javascriptExecutor;

    public HomePage(final WebDriver driver){
        this.javascriptExecutor = (JavascriptExecutor)driver;
        PageFactory.initElements(driver, this);
    }
    public void logout() {
        javascriptExecutor
                .executeScript("arguments[0].click();", logoutButton);
    }
}