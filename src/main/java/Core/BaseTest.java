package Core;

import Utils.FileLoggingFilter;
import Utils.PropertyReader;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import java.io.IOException;

public class BaseTest {

    public RequestSpecification requestSpecification;
    public SoftAssert softAssert;

    @BeforeMethod
    public void setupMethod() throws IOException
    {
        softAssert = new SoftAssert();
        requestSpecification =
                RestAssured
                .given()
                .filter(new FileLoggingFilter())
                .baseUri(PropertyReader.getProperty("serverAddress"))
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1");
    }

    @AfterMethod
    public void assertAllSoftAssertions()
    {
        softAssert.assertAll();
    }
}
