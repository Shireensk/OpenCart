package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseClass;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String repName;

    public void onStart(ITestContext testContext)
    {
        String timestamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        repName="Test-Report-"+timestamp+".html";

        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator + "reports" + File.separator + repName);

        //sparkReporter=new ExtentSparkReporter("\\.reports\\"+repName);
        sparkReporter.config().setDocumentTitle("Opencart Automation Report");
        sparkReporter.config().setReportName("Opencart functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent=new ExtentReports();

        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Application","Opencart");
        extent.setSystemInfo("Module","Admin");
        extent.setSystemInfo("Sub Module","Customers");
        extent.setSystemInfo("User Name",System.getProperty("user.name"));
        extent.setSystemInfo("Environment","QA");

        String os=testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System",os);

        String browser=testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser",browser);

        List<String> includesGroups=testContext.getCurrentXmlTest().getIncludedGroups();
        if(!includesGroups.isEmpty())
        {
            extent.setSystemInfo("Groups",includesGroups.toString());

        }
    }

    public void onTestSuccess(ITestResult result)
    {
        test=extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS,result.getName()+"got successfully executed");

    }

    public void onTestFailure(ITestResult result)
    {
        test=extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL,result.getName()+"got failed");
        test.log(Status.INFO,result.getThrowable().getMessage());

        //calling captureScreen() and attaching screenshot to report

        try
        {
           String imgpath=new BaseClass().captureScreen(result.getName());
           test.addScreenCaptureFromPath(imgpath);

        } catch (Exception e1)
        {
            e1.printStackTrace();
        }
    }

    public void onSkipped(ITestResult result)
    {
        test=extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP,result.getMethod()+"got skipped");
        test.log(Status.SKIP,result.getThrowable().getMessage());

    }

    public void onFinish(ITestContext testContext)
    {
        extent.flush();

        //openning report automatically

        String pathofExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;

        File extentReport=new File((pathofExtentReport));

        try
        {
            Desktop.getDesktop().browse(extentReport.toURI());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
