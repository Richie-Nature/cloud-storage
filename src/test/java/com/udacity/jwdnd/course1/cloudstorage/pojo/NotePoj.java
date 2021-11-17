package com.udacity.jwdnd.course1.cloudstorage.pojo;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotePoj {

    @FindBy(id = "note-modal-btn")
    WebElement modalBtn;

    @FindBy(id = "note-title")
    WebElement noteTitleField;

    @FindBy(id = "note-description")
    WebElement noteDescField;

    @FindBy(id = "save-btn")
    WebElement submitBtn;

    @FindBy(className = "d-note-title")
    WebElement noteTitle;

    @FindBy(className = "d-note-desc")
    WebElement noteDesc;

    @FindBy(className = "note-edit-btn")
    WebElement editBtn;

    @FindBy(className = "note-del-btn")
    WebElement deleteBtn;

    @FindBy(className = "alert-success")
    WebElement successDiv;

    @FindBy(id = "nav-notes-tab")
    WebElement navTab;

    @FindBy(id = "noteModal")
    WebElement noteModal;

    private WebDriver webDriver;
    private WebDriverWait wait;

    public NotePoj(WebDriver driver) {
        PageFactory.initElements(driver, this);
        webDriver = driver;
        wait = new WebDriverWait(webDriver, 10);
    }

    public void openTab() {
        wait.until(ExpectedConditions.visibilityOf(navTab));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", navTab);
    }

    public void addNote(String title, String desc) {
        openTab();
        wait.until(ExpectedConditions.visibilityOf(modalBtn));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", modalBtn);
        wait.until(ExpectedConditions.visibilityOf(noteTitleField));
        fillForm(title, desc);
    }

    public void editNote(String title, String desc) {
        openTab();

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", editBtn);
        wait.until(ExpectedConditions.visibilityOf(noteTitleField));
        fillForm(title, desc);
    }

    public void deleteNote() {
        openTab();
        wait.until(ExpectedConditions.visibilityOf(deleteBtn));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();",deleteBtn);
    }

    public WebElement getNoteTitle() {
        wait.until(ExpectedConditions.visibilityOf(noteTitle));
        return noteTitle;
    }

    public WebElement getNoteDesc() {
        return noteDesc;
    }

    public boolean isNoteTableDataExists() {
        return isElementPresentBy(By.id("note-row"));
    }

    private boolean isElementPresentBy(By by) {
        try {
            webDriver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void fillForm(String title, String desc) {
        noteTitleField.clear();
        noteDescField.clear();
        noteTitleField.sendKeys(title);
        noteDescField.sendKeys(desc);
        wait.until(ExpectedConditions.visibilityOf(submitBtn));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", submitBtn);
    }
}
