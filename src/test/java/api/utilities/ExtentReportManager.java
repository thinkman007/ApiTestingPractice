package api.utilities;

//Extent report 5.x

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String repName;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//time stamp
        repName = "Test-Report-" + timeStamp + ".html";

        //sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);//specify location of the report
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "//reports//" + repName);


        sparkReporter.config().setDocumentTitle("ApiTestingPractice"); // Title of report
        sparkReporter.config().setReportName("Pet Store Users API"); // name of the report
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "Pet Store Users API");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environemnt", "QA");
        extent.setSystemInfo("user", "thinkman");
    }

    public void onTestSuccess(ITestResult result)
    {
        test=extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.PASS, "Test Passed");
    }

    public void onTestFailure(ITestResult result)
    {
        test=extent.createTest(result.getName());
        test.createNode(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, "Test Failed");
        test.log(Status.FAIL, result.getThrowable().getMessage());
    }
    @Override
    public void onFinish(ITestContext testContext) {
        if (extent != null) {
            extent.flush(); // This line actually creates and saves the HTML file!
        }
    }


}

