import Core.BaseTest;
import Pojo.User;
import Utils.*;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import Enum.StatusCode;
import java.util.List;
import java.util.Map;

public class UserTests extends BaseTest {

    @Test(description = "Test to retrieve list of users",retryAnalyzer = RetryAnalyzer.class)
    public void testGetAllUsers() {
        Response response =
                        requestSpecification
                        .when()
                        .queryParam("page", 2)
                        .get(Routes.USERS)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "Expected status code was 200 but actual status code was " + response.getStatusCode());
        List<Map<String, Object>> userList = response.jsonPath().getList("data");
        softAssert.assertTrue(userList != null && !userList.isEmpty(), "Expected non-empty user list under 'data' but found null or empty");
        softAssert.assertAll();
        System.out.println("testGetAllUsers passed successfully");
    }

    @Test(description = "Test to retrieve single user",retryAnalyzer = RetryAnalyzer.class)
    public void testGetSingleUser() {
        Response response =
                        requestSpecification
                        .when()
                        .pathParam("id", 2)
                        .get(Routes.USERS_ID)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "Expected status code was 200 but actual status code was " + response.getStatusCode());
        softAssert.assertNotNull(response.jsonPath().get("data.id"), "User ID should not be null");
        softAssert.assertNotNull(response.jsonPath().get("data.email"), "Email should not be null");
        softAssert.assertFalse(response.jsonPath().getString("data.first_name").isEmpty(), "First name should not be empty");
        softAssert.assertFalse(response.jsonPath().getString("data.last_name").isEmpty(), "Last name should not be empty");
        softAssert.assertFalse(response.jsonPath().getString("data.avatar").isEmpty(), "Avatar URL should not be empty");
        softAssert.assertAll();
        System.out.println("testGetSingleUser passed successfully");
    }

    @Test(description = "Test to create a user",retryAnalyzer = RetryAnalyzer.class,dataProvider = "createUserData",dataProviderClass = DataProviders.class)
    public void testCreateUser(String name, String job) {
        User userBody = new User();
        userBody.setName(name);
        userBody.setJob(job);
        Response response =
                        requestSpecification
                        .when()
                        .body(userBody)
                        .post(Routes.USERS)
                        .then()
                        .extract()
                        .response();
        User responseBody = response.as(User.class);
        softAssert.assertEquals(response.getStatusCode(), StatusCode.CREATED.code, "Expected status code was 201 but actual status code was " + response.getStatusCode());
        softAssert.assertEquals(response.jsonPath().getString("name"), responseBody.getName());
        softAssert.assertEquals(response.jsonPath().getString("job"), responseBody.getJob());
        softAssert.assertAll();
        System.out.println("testCreateUser passed successfully");
    }

    @Test(description = "Test to create a user with name field only",retryAnalyzer = RetryAnalyzer.class)
    public void testCreateUserWithNameOnly() {
        User userBody = new User();
        userBody.setName("Joseph");
        Response response =
                        requestSpecification
                        .when()
                        .body(userBody)
                        .post(Routes.USERS)
                        .then()
                        .extract()
                        .response();
        User responseBody = response.as(User.class);
        softAssert.assertEquals(response.getStatusCode(), StatusCode.CREATED.code, "Expected status code was 201 but actual status code was " + response.getStatusCode());
        softAssert.assertEquals(response.jsonPath().getString("job"), responseBody.getJob());
        softAssert.assertAll();
        System.out.println("testCreateUserWithNameOnly passed successfully");
    }

    @Test(description = "Test to update a user",retryAnalyzer = RetryAnalyzer.class)
    public void testUpdateUser() {
        User userBody = new User();
        userBody.setName("John");
        userBody.setJob("Automation Tester");
        Response response =
                        requestSpecification
                        .when()
                        .body(userBody)
                        .pathParam("id", 2)
                        .put(Routes.USERS_ID)
                        .then()
                        .extract()
                        .response();
        User responseBody = response.as(User.class);
        softAssert.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "Expected status code was 200 but actual status code was " + response.getStatusCode());
        softAssert.assertEquals(response.jsonPath().getString("name"), responseBody.getName());
        softAssert.assertEquals(response.jsonPath().getString("job"), responseBody.getJob());
        softAssert.assertNotNull(response.jsonPath().getString("updatedAt"), "Updated timestamp is Null");
        softAssert.assertAll();
        System.out.println("testUpdateUser passed successfully");
    }

    @Test(description = "Test to delete a user",retryAnalyzer = RetryAnalyzer.class)
    public void testDeleteUser() {
        Response response =
                        requestSpecification
                        .when()
                        .pathParam("id", 2)
                        .delete(Routes.USERS_ID)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(), StatusCode.DELETED.code, "Expected status code was 204 but actual status code was " + response.getStatusCode());
        softAssert.assertAll();
        System.out.println("testDeleteUser passed successfully");
    }

    @Test(description = "Test to get list of users which is empty",retryAnalyzer = RetryAnalyzer.class)
    public void testGetEmptyListOfUsers() {
        Response response =
                        requestSpecification
                        .when()
                        .queryParam("page", 999)
                        .get(Routes.USERS)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "Expected status code was 200 but actual status code was " + response.getStatusCode());
        softAssert.assertTrue(response.jsonPath().getList("data").isEmpty(), "The user list should be empty but got " + response.getBody().asString());
        softAssert.assertAll();
        System.out.println("testGetEmptyListOfUsers passed successfully");
    }

    @Test(description = "Test to partially update a user",retryAnalyzer = RetryAnalyzer.class)
    public void testUpdateUserPartialUsingPatch() {
        User userRequestBody = new User();
        userRequestBody.setJob("Devops Engineer");
        Response response =
                        requestSpecification
                        .when()
                        .body(userRequestBody)
                        .pathParam("id", 2)
                        .patch(Routes.USERS_ID)
                        .then()
                        .extract()
                        .response();
        User userResponseBody = response.as(User.class);
        softAssert.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "Expected status code was 200 but actual status code was " + response.getStatusCode());
        softAssert.assertEquals(response.jsonPath().getString("job"), userResponseBody.getJob());
        softAssert.assertNotNull(response.jsonPath().getString("updatedAt"), "Updated timestamp is Null");
        softAssert.assertAll();
        System.out.println("testUpdateUserPartialUsingPatch passed successfully");

    }

    @Test(description = "Test to fetch a valid non user resource",retryAnalyzer = RetryAnalyzer.class)
    public void testFetchNonUserValidResource() {
        Response response =
                        requestSpecification
                        .when()
                        .pathParam("id", 2)
                        .get(Routes.UNKNOWN_ID)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "Expected status code was 200 but actual status code was " + response.getStatusCode());
        softAssert.assertAll();
        Map<String, Object> resourceList = response.jsonPath().getMap("data");
        System.out.println(resourceList);
        System.out.println("testFetchNonUserValidResource passed successfully");
    }

}
