package com.udacity.jwdnd.course1.cloudstorage.pojo;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;


public class CredentialPoj {

    @FindBy(id = "cred-modal-btn")
    WebElement modalBtn;

    @FindBy(id = "nav-credentials-tab")
    WebElement navTab;

    @FindBy(className = "cred-edit-btn")
    WebElement editBtn;

    @FindBy(className = "cred-del-btn")
    WebElement delBtn;

    @FindBy(className = "cred-url")
    WebElement credUrl;

    @FindBy(className = "cred-uname")
    WebElement credUname;

    @FindBy(className = "cred-pass")
    WebElement credPass;

    @FindBy(id = "credentialModal")
    WebElement modal;

    @FindBy(id = "credential-url")
    WebElement credUrlField;

    @FindBy(id = "credential-username")
    WebElement credUnameField;

    @FindBy(id = "credential-password")
    WebElement credPassField;

    @FindBy(id = "save-cred-btn")
    WebElement submitBtn;

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

    public void addCredential(String url, String username, String password) {
        openTab();
        wait.until(ExpectedConditions.visibilityOf(modalBtn));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", modalBtn);
        wait.until(ExpectedConditions.visibilityOf(credUrlField));
        fillForm(url, username, password);
    }

    public void openEditModal() {
        openTab();
        wait.until(ExpectedConditions.visibilityOf(editBtn));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", editBtn);
    }

    public void editCredential(String url, String username, String password) {
        wait.until(ExpectedConditions.visibilityOf(credUrlField));
        fillForm(url, username, password);
    }

    public void deleteCredential() {
        openTab();
        wait.until(ExpectedConditions.visibilityOf(delBtn));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", delBtn);
    }

    public WebElement getCredUrl() {
        wait.until(ExpectedConditions.visibilityOf(credUrl));
        return credUrl;
    }

    public WebElement getCredUname() {
        wait.until(ExpectedConditions.visibilityOf(credUname));
        return credUname;
    }

    public WebElement getCredPass() {
        wait.until(ExpectedConditions.visibilityOf(credPass));
        return credPass;
    }

    public WebElement getCredPassField() {
        wait.until(ExpectedConditions.visibilityOf(modal));
        return credPassField;
    }

    public Credential getCredential(String username, String id, UserService userService,
                                    CredentialService credentialService) {
        User user =  userService.getUser(username);
        return credentialService.getCredential(Integer.parseInt(id),user.getUserid());

    }

    public String getCredentialId() {
        wait.until(ExpectedConditions.visibilityOf(editBtn));
        return editBtn.getAttribute("data-credId");
    }

    public boolean isCredTableDataExists() {
        return isElementPresentBy(By.id("cred-row"));
    }

    private boolean isElementPresentBy(By by) {
        try {
            webDriver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void fillForm(String url, String username, String password) {
        wait.until(ExpectedConditions.visibilityOf(credUrlField));
        credUrlField.clear();
        credPassField.clear();
        credUnameField.clear();

        credUrlField.sendKeys(url);
        credUnameField.sendKeys(username);
        credPassField.sendKeys(password);
        wait.until(ExpectedConditions.visibilityOf(submitBtn));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", submitBtn);
    }
}
