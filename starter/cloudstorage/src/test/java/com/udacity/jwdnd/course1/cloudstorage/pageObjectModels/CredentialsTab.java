package com.udacity.jwdnd.course1.cloudstorage.pageObjectModels;

import java.util.ArrayList;
import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class CredentialsTab {

    @FindBy(id="nav-credentials-tab")
    private WebElement credentialsTabLink;

    @FindBy(id="add-credential-button")
    private WebElement addCredentialButton;
   
    @FindBy(id="url")
    private WebElement urlInput;

    @FindBy(id="username")
    private WebElement usernameInput;

    @FindBy(id="password")
    private WebElement passwordInput;

    @FindBy(id="credentialSubmit")
    private WebElement saveCredentialButton;

    @FindBy(id="edit-credential")
    private List<WebElement> editCredentialButton;

    @FindBy(id="delete-credential")
    private List<WebElement> deleteCredentialButton;

    @FindBy(id="credential-url")
    private List<WebElement> credentialUrl;

    @FindBy(id="credential-username")
    private List<WebElement> credentialUsername;

    @FindBy(id="credential-password")
    private List<WebElement> credentialPassword;

    private final WebDriverWait wait;

    public CredentialsTab(final WebDriver driver) {
        this.wait = new WebDriverWait(driver, 1000);

        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.id("nav-credentials-tab")));

        PageFactory.initElements(driver, this);
    }

    public void showCredentials(){
        wait.until(ExpectedConditions.elementToBeClickable(credentialsTabLink));
        credentialsTabLink.click();
    }

    public void newCredential(String url, String username, String password) throws InterruptedException {
        wait.until(ExpectedConditions
                .elementToBeClickable(addCredentialButton));

        addCredentialButton.click();

        Thread.sleep(500);

        populateCredential(url, username, password);

    }

    public boolean editCredential(int index, String newUrl, String newUsername, String newPassword) throws InterruptedException {
        if(editCredentialButton.size() <= index){
            return false;
        }

        wait.until(ExpectedConditions
                .elementToBeClickable(editCredentialButton.get(index)));

        editCredentialButton.get(index).click();

        Thread.sleep(500);

        populateCredential(newUrl, newUsername, newPassword);



        return true;
    }



    public boolean deleteCredential(int index) {

        if(deleteCredentialButton.size() <= index){
            return false;
        }

        wait.until(ExpectedConditions
                .elementToBeClickable(deleteCredentialButton.get(index)));

        deleteCredentialButton.get(index).click();

        return true;
    }

    public int getCredentialListSize() {
       return credentialUrl.size();
    }

    private void populateCredential(String url, String username, String password) {
        wait.until(ExpectedConditions
                .elementToBeClickable(saveCredentialButton));

        urlInput.clear();
        urlInput.sendKeys(url);

        usernameInput.clear();
        usernameInput.sendKeys(username);

        passwordInput.clear();
        passwordInput.sendKeys(password);

        saveCredentialButton.click();
    }

}