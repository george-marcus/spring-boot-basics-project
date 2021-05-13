package com.udacity.jwdnd.course1.cloudstorage.pageObjectModels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(className = "logout")
    private WebElement logoutButton;

    private final WebDriverWait wait;

    public HomePage(final WebDriver driver){
        this.wait = new WebDriverWait(driver, 60);

        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.className("logout")));

        PageFactory.initElements(driver, this);
    }
    public void logout() {

        wait.until(ExpectedConditions
                .elementToBeClickable(logoutButton));

        logoutButton.click();
    }
}