package testCase;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups="Datadriven")
    public void verify_loginDDT(String email, String pwd, String exp) throws InterruptedException {
        logger.info("*** Starting TC003_LoginDDT ***");

        try{
            // Homepage actions
            HomePage hp = new HomePage(driver);
            hp.clickMyaccount();
            hp.clickLogin();

            // Login page actions
            LoginPage lp = new LoginPage(driver);
            lp.setEmail(email);
            lp.setPassword(pwd);
            lp.clickLogin();

            // My Account page actions
            MyAccountPage macc = new MyAccountPage(driver);

            boolean targetpage = macc.isMyAccountPageExist();

            if (exp.equalsIgnoreCase("Valid")) {
                if (targetpage)
                {
                    macc.clickLogout();
                    Assert.assertTrue(true);

                }
                else
                {
                    Assert.assertTrue(false);
                }
            }
            if (exp.equalsIgnoreCase("Invalid"))
            {
                if (targetpage )
                {
                    macc.clickLogout();
                    Assert.assertTrue(false);
                }
                else
                {

                    Assert.assertTrue(true);
                }
            }

        }
        catch (Exception e)
        {
            Assert.fail();
        }
        logger.info("**** Finished TC003_LoginDDT ****");
        }
    }

