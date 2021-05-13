package com.udacity.jwdnd.course1.cloudstorage.pageObjectModels;

import java.util.ArrayList;
import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class NotesTab {

    @FindBy(id="nav-notes-tab")
    private WebElement notesTabLink;

    @FindBy(id="add-note-button")
    private WebElement addNoteButton;
   
    @FindBy(id="noteTitle")
    private WebElement titleInput;

    @FindBy(id="noteDescription")
    private WebElement descriptionInput;

    @FindBy(id="noteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(id="note-edit")
    private List<WebElement> noteEdit;

    @FindBy(id="note-delete")
    private List<WebElement> noteDelete;

    @FindBy(id="note-title")
    private List<WebElement> noteTitle;

    @FindBy(id="note-description")
    private List<WebElement> noteDescription;

    private final JavascriptExecutor javascriptExecutor;

    public NotesTab(final WebDriver driver) {
        this.javascriptExecutor = (JavascriptExecutor)driver;
        PageFactory.initElements(driver, this);
    }

    public void showNotes() {
        javascriptExecutor
                .executeScript("arguments[0].click();", notesTabLink);
    }

    public void newNote(String title, String description) throws InterruptedException {
        javascriptExecutor
                .executeScript("arguments[0].click();", addNoteButton);

        Thread.sleep(500);

        populateNote(title, description);
    }

    public boolean editNote(int index, String newTitle, String newDescription) throws InterruptedException {

        if(noteEdit.size() <= index){
            return false;
        }

        javascriptExecutor
                .executeScript("arguments[0].click();", noteEdit.get(index));

        Thread.sleep(500);

        populateNote(newTitle, newDescription);

        return true;
    }

    public boolean deleteNote(int index) {
        if(noteDelete.size() <= index){
            return false;
        }

        javascriptExecutor
                .executeScript("arguments[0].click();", noteDelete.get(index));

        return true;
    }

    public int getNoteListSize() {
        return noteTitle.size();
    }

    private void populateNote(String title, String description) {
        titleInput.clear();
        titleInput.sendKeys(title);

        descriptionInput.clear();
        descriptionInput.sendKeys(description);

        javascriptExecutor
                .executeScript("arguments[0].click();", noteSubmitButton);

    }


}