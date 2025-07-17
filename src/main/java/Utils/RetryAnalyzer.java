package Utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer
{
    private int retryCount = 0;
    private static final int maxRetryCount = 3;
    @Override
    public boolean retry(ITestResult iTestResult) {
        if(retryCount<maxRetryCount)
        {
            retryCount++;
            System.out.println("Retrying test "+iTestResult.getName()+" again "+retryCount);
            ExtentReportListener.createTestForRetry(iTestResult);
            return true;
        }
        return false;
    }
}
