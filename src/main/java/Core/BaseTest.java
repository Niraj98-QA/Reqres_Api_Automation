package Core;

import Utils.ExtentReportListener;
import Utils.LoggingFilter;
import Utils.PropertyReader;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseTest {

    public RequestSpecification requestSpecification;
    public SoftAssert softAssert;

    @BeforeMethod
    public void setupMethod(Method method) throws IOException {
        softAssert = new SoftAssert();
        requestSpecification = RestAssured
                .given()
                .filter(new LoggingFilter())
                .baseUri(PropertyReader.getProperty("serverAddress"))
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1");
    }

}