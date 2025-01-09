package testCase;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;


public class TC002_LoginTest extends BaseClass
{
    @Test(groups={"Sanity","Master"})
    public void verify_login()
    {
        logger.info("**** starting TC002_LoginTest ****");
        //Homepage
        HomePage hp = new HomePage(driver);
        hp.clickMyaccount();
        hp.clickLogin();

        //Login
        LoginPage lp=new LoginPage(driver);
        lp.setEmail(p.getProperty("email"));
        lp.setPassword(p.getProperty("password"));
        lp.clickLogin();

        //MyAccountPage

        MyAccountPage macc=new MyAccountPage(driver);
        boolean targetpage=macc.isMyAccountPageExist();

        try
          {
             Assert.assertTrue(targetpage);
          }
        catch (Exception e)
        {
            Assert.fail();
        }

       logger.info("****Finished TC002_LoginTest***");
    }
}
