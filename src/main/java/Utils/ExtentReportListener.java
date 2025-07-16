package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

public class ExtentReportListener implements ITestListener {

    private static ExtentReports extent;

    static {
        try {
            extent = ExtentReportManager.getInstance();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize ExtentReports", e);
        }
    }

    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return test.get();
    }

    @Override
    public void onStart(ITestContext context) {
        LogManager.initRunFolder();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
        test.get().info("Starting: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Testcase Passed Successfully");
        attachLogLink();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail("Testcase Failed: " + result.getThrowable());
        attachLogLink();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("⏭ Testcase Skipped");
        attachLogLink();
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    private void attachLogLink() {
        String logFilePath = LogManager.getCurrentTestLog();
        if (logFilePath != null) {
            String folderName = LogManager.getRunFolder();
            String logName = new File(logFilePath).getName();
            String relativePath = folderName + "/" + logName;
            test.get().info("<a href='" + relativePath + "' target='_blank'>📄 View API Log</a>");
        }
    }
}