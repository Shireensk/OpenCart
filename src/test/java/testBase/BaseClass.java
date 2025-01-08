package testBase;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseClass {

    public WebDriver driver;

    public Logger logger;

    public Properties p;

    @BeforeClass(groups={"Sanity","Regression","Master"})
    @Parameters({"os","browser"})
    public void setUp(@Optional("default") String os, @Optional("default") String br) throws IOException {
        logger=LogManager.getLogger(this.getClass());

        FileReader file=new FileReader("./src//test//resources//config.properties");
        p=new Properties();
        p.load(file);

        if (p.getProperty("exp_environment").equalsIgnoreCase("remote"))
        {
            DesiredCapabilities capabilities=new DesiredCapabilities();

            //os
            switch (os.toLowerCase())
            {
                case "windows":capabilities.setPlatform(Platform.WIN10); break;
                case "mac":capabilities.setPlatform(Platform.MAC); break;
                case "linux":capabilities.setPlatform(Platform.LINUX); break;
                default:System.out.println("No matching os"); return;

            }

            //browser
            switch (br.toLowerCase())
            {
                case "chrome":capabilities.setBrowserName("chrome"); break;
                case "firefox":capabilities.setBrowserName("firefox"); break;
                case "edge":capabilities.setBrowserName("MicrosoftEdge"); break;
                default:System.out.println("No matching browser"); return;

            }

            driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);

        }

        if (p.getProperty("exp_environment").equalsIgnoreCase("local")) {

            switch (br.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                default:
                    System.out.println("Invalid browser");
                    return;

            }
        }
        //driver=new ChromeDriver;

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(p.getProperty("appurl2"));
        //driver.get("https://tutorialsninja.com/demo/");
        driver.manage().window().maximize();

    }

    @AfterClass(groups={"Sanity","Regression","Master"})
    public void tearDown()
    {
        if (driver != null)
        {
            driver.quit();
        }
        else
        {
            logger.warn("Driver is not initialized. Skipping quit.");
        }
    }

    public String randomNumber()
    {
        String generatednumber= RandomStringUtils.randomNumeric(10);
        return generatednumber;
    }

    public String randomString() {
         String generatedstring = RandomStringUtils.randomAlphabetic(5);
         return generatedstring;
    }

    public String randomAlphaNumeric()
    {
        String generatednumber=RandomStringUtils.randomNumeric(3);
        String generatedString=RandomStringUtils.randomAlphabetic(3);

        return (generatednumber+"@"+generatedString);
    }

    public String captureScreen(String tname) throws IOException {
        String timestamp=new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss").format(new Date());

        TakesScreenshot takescreenshot=(TakesScreenshot) driver;
        File sourcefile=takescreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath=System.getProperty("user.dir"+"\\screenshots\\"+tname+" "+timestamp+".png");

        //String targetFilePath = "D:\\Selenium Project\\opencart\\screenshots\\" + tname + " " + timestamp + ".png";

        //String targetFilePath=System.getProperty("user.dir"+File.separator+"screenshots"+File.separator+tname+" "+timestamp+".png");

        File targetFile=new File(targetFilePath);

        sourcefile.renameTo(targetFile);

        return targetFilePath;
    }

}