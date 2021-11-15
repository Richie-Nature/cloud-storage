package com.udacity.jwdnd.course1.cloudstorage.pojo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
   @FindBy(id = "inputFirstName")
   WebElement firstnameField;

   @FindBy(id = "inputLastName")
   WebElement lastnameField;

   @FindBy(id = "inputUsername")
   WebElement usernameField;

   @FindBy(id = "inputPassword")
   WebElement passwordField;

   @FindBy(xpath = "//button[@type='submit']")
   private WebElement submitButton;

   public SignupPage(WebDriver driver) {
       PageFactory.initElements(driver, this);
   }

   public String getFirstnameField() {
      return firstnameField.getText();
   }

   public void setFirstnameField(String firstname) {
      this.firstnameField.sendKeys(firstname);
   }

   public String getLastnameField() {
      return lastnameField.getText();
   }

   public void setLastnameField(String lastname) {
      this.lastnameField.sendKeys(lastname);
   }

   public String getUsernameField() {
      return usernameField.getText();
   }

   public void setUsernameField(String username) {
      this.usernameField.sendKeys(username);
   }

   public String getPasswordField() {
      return passwordField.getText();
   }

   public void setPasswordField(String password) {
      this.passwordField.sendKeys(password);
   }

   public void signup(String fname, String lname, String uname, String pass) {
      setFirstnameField(fname);
      setLastnameField(lname);
      setUsernameField(uname);
      setPasswordField(pass);

      submitButton.submit();
   }
}
