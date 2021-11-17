package com.udacity.jwdnd.course1.cloudstorage.pojo;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPoj {

    @FindBy(id = "cred-modal-btn")
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

    public CredentialPoj(WebDriver driver) {
        PageFactory.initElements(driver, this);
        webDriver = driver;
        wait = new WebDriverWait(webDriver, 10);
    }

    public void openTab() {
        wait.until(ExpectedConditions.visibilityOf(navTab));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", navTab);
    }
}
