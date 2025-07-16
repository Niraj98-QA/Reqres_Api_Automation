package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager
{
    private static ExtentReports extent;

    private ExtentReportManager(){}

    public static ExtentReports getInstance() throws IOException {
        if(extent == null)
        {
            createInstance();
        }
        return extent;
    }

    public static void createInstance() throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
        String reportFilePath = System.getProperty("user.dir") + "/Reports/testOutput-report"+timestamp+".html";

        ExtentSparkReporter reporter = new ExtentSparkReporter(reportFilePath);
        reporter.config().setReportName("REQRES_API");
        reporter.config().setDocumentTitle("API AUTOMATION REPORT");
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setOfflineMode(true);
        reporter.config().setTimelineEnabled(true);

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Framework","RestAssured+testNG");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Build", "1.0.5");
        extent.setSystemInfo("Base URL", PropertyReader.getProperty("serverAddress"));
    }
}