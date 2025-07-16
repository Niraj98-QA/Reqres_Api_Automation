import Core.BaseTest;
import Utils.Routes;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import Enum.StatusCode;

public class PerformanceTests extends BaseTest
{
    @Test(description = "Test to get users within given threshold")
    public void testGetUsersDelayedResponse(){
        Response response =
                requestSpecification
                        .queryParam("delay",4)
                        .when()
                        .get(Routes.USERS)
                        .then()
                        .extract()
                        .response();

        softAssert.assertEquals(response.getStatusCode(),StatusCode.SUCCESS.code,"Expected status code was 200 but actual status code was " + response.getStatusCode());
        softAssert.assertTrue(!response.jsonPath().getList("data").isEmpty(),"The user list is empty");
        System.out.println("testGetUsersDelayedResponse passed successfully");
    }
}
