package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountRegistrationPage extends BasePage {

    public AccountRegistrationPage(WebDriver driver)
    {
        super(driver);
    }

    @FindBy(xpath="//input[@name='firstname']")
    WebElement txtFirstname;

    @FindBy(xpath="//input[@name='lastname']")
    WebElement txtLastname;

    @FindBy(xpath="//input[@name='email']")
    WebElement txtEmail;

    @FindBy(xpath="//input[@name='telephone']")
    WebElement txtTelephone;

    @FindBy(xpath="//input[@name='password']")
    WebElement txtPassword;

    @FindBy(xpath="//input[@id='input-confirm']")
    WebElement txtConfirmpassword;

    @FindBy(xpath="//div[@class='pull-right']/input[@name='agree']")
    WebElement chkPolicy;

    @FindBy(xpath="//input[@value='Continue']")
    WebElement btnContinue;

    @FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
    WebElement msgConfirmation;


    public void setFirstname(String fname)
    {
        txtFirstname.sendKeys(fname);
    }

    public void setLastname(String lname)
    {
        txtLastname.sendKeys(lname);
    }


    public void setEmail(String email)
    {
       txtEmail.sendKeys(email);
    }


    public void setTelephone(String tel)
    {
        txtTelephone.sendKeys(tel);
    }


    public void setPassword(String pwd)
    {
        txtPassword.sendKeys(pwd);
    }


    public void setConfirmpassword(String pwd)
    {
        txtConfirmpassword.sendKeys(pwd);
    }

    public void setPrivacypolicy()
    {
        JavascriptExecutor js= (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();",chkPolicy);
        //chkPolicy.click();
    }


    public String getConfirmationMsg()
    {
        try
        {
            WebDriverWait mywait=new WebDriverWait(driver, Duration.ofSeconds(10));
            mywait.until(ExpectedConditions.visibilityOf(msgConfirmation));
            return msgConfirmation.getText();
        }
        catch (Exception e)
        {
            return(e.getMessage());

        }

    }

    public void clickContinue()
    {
        JavascriptExecutor js=(JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();",btnContinue);
    }

}
