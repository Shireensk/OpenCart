package testCase;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(groups={"Regression","Master"})
    public void verify_account_registration()
    {
        logger.info("**** starting TC001_AccountRegistrationTest ****");
        try {
            HomePage hp = new HomePage(driver);

            hp.clickMyaccount();
            hp.clickRegister();

            logger.info("Clicked on Register Link");

            AccountRegistrationPage regpage = new AccountRegistrationPage(driver);

            logger.info("Providing Customer details.....");

            regpage.setFirstname(randomString().toUpperCase());
            regpage.setLastname(randomString().toUpperCase());
            regpage.setEmail(randomString() + "@gmail.com");
            regpage.setTelephone(randomNumber());

            String password = randomAlphaNumeric();

            regpage.setPassword(password);
            regpage.setConfirmpassword(password);

            regpage.setPrivacypolicy();
            regpage.clickContinue();

            logger.info("Validating expected message....");

            String confmsg = regpage.getConfirmationMsg();
            //Assert.assertEquals(confmsg,"Your Account Has Been Created!");

            if (confmsg.equals("Your Account Has Been Created!")) {
                Assert.assertTrue(true);
            } else {
                logger.error("Test failed....");
                logger.debug("Debug logs......");
                Assert.assertTrue(false);
            }
        }
        catch (Exception e)
        {
            Assert.fail();
        }

        logger.info("**** Finished TC001_AccountRegistrationTest ****");

    }
}
