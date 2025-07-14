import Core.BaseTest;
import Pojo.User;
import Utils.JsonReader;
import Utils.Routes;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import Enum.StatusCode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NegativeTests extends BaseTest {
    @Test(description = "Test to retrieve a user with invalid ID")
    public void testGetSingleUserByInvalidID() {
        Response response =
                        requestSpecification
                        .when()
                        .pathParam("id", 23)
                        .get(Routes.USERS_ID)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(), StatusCode.NOT_FOUND.code, "Expected status code was 404 but actual status code was " + response.getStatusCode());
        String responseBody = response.getBody().asString().trim();
        softAssert.assertTrue(responseBody.isEmpty() || responseBody.equals("{}"), "Expected empty response body, but got: " + responseBody);
        System.out.println(response.body().asString());
        System.out.println("testGetSingleUserByInvalidID passed successfully");
    }

    @Test(description = "Testcase to check user login with missing password")
    public void testLoginUserWithMissingPassword() throws IOException, ParseException {
        Map<String, String> loginBody = new HashMap<>();
        loginBody.put("email", JsonReader.getJsonData("email"));
        Response response =
                        requestSpecification
                        .when()
                        .body(loginBody)
                        .post(Routes.LOGIN)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(), StatusCode.BAD_REQUEST.code, "Expected status code was 400 but actual status code was " + response.getStatusCode());
        System.out.println(response.jsonPath().getString("error"));
        System.out.println("testLoginUserWithMissingPassword passed successfully");
    }

    @Test(description = "Testcase to check user register with missing emailID")
    public void testRegisterUserWithMissingEmail() throws IOException, ParseException {
        Map<String, String> registerBody = new HashMap<>();
        registerBody.put("password", JsonReader.getJsonData("password"));
        Response response =
                        requestSpecification
                        .when()
                        .body(registerBody)
                        .post(Routes.REGISTER)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(), StatusCode.BAD_REQUEST.code, "Expected status code was 400 but actual status code was " + response.getStatusCode());
        System.out.println(response.jsonPath().getString("error"));
        System.out.println("testRegisterUserWithMissingEmail passed successfully");
    }

    @Test(description = "Testcase to check user register with missing password")
    public void testRegisterUserWithMissingPassword() throws IOException, ParseException {
        Map<String, String> registerBody = new HashMap<>();
        registerBody.put("email", JsonReader.getJsonData("email"));
        Response response =
                        requestSpecification
                        .when()
                        .body(registerBody)
                        .post(Routes.REGISTER)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(), StatusCode.BAD_REQUEST.code, "Expected status code was 400 but actual status code was " + response.getStatusCode());
        System.out.println(response.jsonPath().getString("error"));
        System.out.println("testRegisterUserWithMissingPassword passed successfully");
    }

    @Test(description = "Test to get a user with invalid ID format")
    public void testGetUserWithInvalidID() {
        Response response =
                        requestSpecification
                        .when()
                        .pathParam("id", "abc")
                        .get(Routes.USERS_ID)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(), StatusCode.NOT_FOUND.code, "Expected status code was 404 but actual status code was " + response.getStatusCode());
        System.out.println("testGetUserWithInvalidID passed successfully");
    }

    @Test(description = "Test to fetch a Invalid non user resource")
    public void testFetchNonUserInValidResource() {
        Response response =
                        requestSpecification
                        .when()
                        .pathParam("id", 23)
                        .get(Routes.UNKNOWN_ID)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(), StatusCode.NOT_FOUND.code, "Expected status code was 404 but actual status code was " + response.getStatusCode());
        softAssert.assertTrue(response.getBody().asString().trim().equals("{}") || response.getBody().asString().trim().isEmpty(), "Expected empty JSON");
        System.out.println("testFetchNonUserInValidResource passed successfully");
    }

    @Test(description = "Test to update a user with empty payload")
    public void testUpdateUserWithEmptyPayload() {
        User userBody = new User();
        Response response =
                requestSpecification
                        .when()
                        .body(userBody)
                        .pathParam("id", 2)
                        .put(Routes.USERS_ID)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "Expected status code was 200 but actual status code was " + response.getStatusCode());
        softAssert.assertNotNull(response.jsonPath().getString("updatedAt"), "Expected the updated timestamp but got Null");
        System.out.println("testUpdateUserWithEmptyPayload passed successfully");
    }
}
