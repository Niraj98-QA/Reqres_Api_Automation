package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.util.Arrays;

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

    public static void setTest(ExtentTest extentTest) {
        test.set(extentTest);
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Build a unique name if parameters exist (for DataProvider clarity)
        String methodName = result.getMethod().getMethodName();
        Object[] params = result.getParameters();
        if (params != null && params.length > 0) {
            methodName += " - Params: " + Arrays.toString(params);
        }

        ExtentTest extentTest = extent.createTest(methodName);
        setTest(extentTest);
        test.get().info("Starting: " + methodName);
    }

    public static void createTestForRetry(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName() + " (Retry-" + (result.getMethod().getCurrentInvocationCount()) + ")");
        setTest(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Testcase Passed Successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail("Testcase Failed: " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Testcase Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        ExtentReportManager.copyLatestReportToIndex();
    }
}
