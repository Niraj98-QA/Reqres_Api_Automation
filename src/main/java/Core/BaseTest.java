package Core;

import Utils.FileLoggingFilter;
import Utils.LogManager;
import Utils.PropertyReader;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {

    public RequestSpecification requestSpecification;
    public SoftAssert softAssert;

    @BeforeMethod
    public void setupMethod(Method method) throws IOException {
        softAssert = new SoftAssert();

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String logsDir = System.getProperty("user.dir") + "/Reports/" + LogManager.getRunFolder() + "/";
        new File(logsDir).mkdirs();

        String logFileName = method.getName() + "-" + timestamp + ".txt";
        String logFilePath = logsDir + logFileName;
        LogManager.setCurrentTestLog(logFilePath);

        requestSpecification = RestAssured
                .given()
                .filter(new FileLoggingFilter(logFilePath))
                .baseUri(PropertyReader.getProperty("serverAddress"))
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        LogManager.clearCurrentTestLog();
    }
}