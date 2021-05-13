package com.udacity.jwdnd.course1.cloudstorage.pageObjectModels;

import java.util.ArrayList;
import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    private final JavascriptExecutor javascriptExecutor;

    public CredentialsTab(final WebDriver driver) {
        this.javascriptExecutor = (JavascriptExecutor)driver;
        PageFactory.initElements(driver, this);
    }

    public void showCredentials(){
        javascriptExecutor
                .executeScript("arguments[0].click();", credentialsTabLink);
    }

    public void newCredential(String url, String username, String password) throws InterruptedException {

        javascriptExecutor
                .executeScript("arguments[0].click();", addCredentialButton);

        Thread.sleep(500);

        populateCredential(url, username, password);

    }

    public boolean editCredential(int index, String newUrl, String newUsername, String newPassword) throws InterruptedException {
        if(editCredentialButton.size() <= index){
            return false;
        }

        javascriptExecutor
                .executeScript("arguments[0].click();", editCredentialButton.get(index));

        Thread.sleep(500);

        populateCredential(newUrl, newUsername, newPassword);

        return true;
    }



    public boolean deleteCredential(int index) {

        if(deleteCredentialButton.size() <= index){
            return false;
        }

        javascriptExecutor
                .executeScript("arguments[0].click();", deleteCredentialButton.get(index));

        return true;
    }

    public int getCredentialListSize() {
       return credentialUrl.size();
    }

    private void populateCredential(String url, String username, String password) {

        urlInput.clear();
        urlInput.sendKeys(url);

        usernameInput.clear();
        usernameInput.sendKeys(username);

        passwordInput.clear();
        passwordInput.sendKeys(password);

        javascriptExecutor
                .executeScript("arguments[0].click();", saveCredentialButton);
    }

}