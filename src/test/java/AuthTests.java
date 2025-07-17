import Core.BaseTest;
import Utils.JsonReader;
import Utils.RetryAnalyzer;
import Utils.Routes;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import Enum.StatusCode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthTests extends BaseTest
{
    @Test(description = "Testcase to test user login",retryAnalyzer = RetryAnalyzer.class)
    public void testLoginUser() throws IOException, ParseException {
        Map<String,String> loginBody = new HashMap<>();
        loginBody.put("email",JsonReader.getJsonData("email"));
        loginBody.put("password",JsonReader.getJsonData("password"));
        Response response =
                        requestSpecification
                        .when()
                        .body(loginBody)
                        .post(Routes.LOGIN)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(),StatusCode.SUCCESS.code,"Expected status code was 200 but actual status code was "+response.getStatusCode());
        softAssert.assertNotNull(response.jsonPath().getString("token"));
        softAssert.assertAll();
        System.out.println("testLoginUser passed successfully");
    }

    @Test(description = "Testcase to test user registration",retryAnalyzer = RetryAnalyzer.class)
    public void testRegisterUser() throws IOException, ParseException {
        Map<String,String> registerBody = new HashMap<>();
        registerBody.put("email",JsonReader.getJsonData("email"));
        registerBody.put("password",JsonReader.getJsonData("password"));
        Response response =
                        requestSpecification
                        .when()
                        .body(registerBody)
                        .post(Routes.REGISTER)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(),StatusCode.SUCCESS.code,"Expected status code was 200 but actual status code was "+response.getStatusCode());
        softAssert.assertNotNull(response.jsonPath().getString("id"));
        softAssert.assertNotNull(response.jsonPath().getString("token"));
        softAssert.assertAll();
        System.out.println("testRegisterUser passed successfully");
    }
}
