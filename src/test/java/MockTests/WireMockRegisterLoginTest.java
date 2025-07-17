package MockTests;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class WireMockRegisterLoginTest {

    private static WireMockServer wireMockServer;

    @BeforeClass
    public static void setWireMockServer() {

        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();

        wireMockServer.stubFor(post(urlEqualTo("/register"))
                .withRequestBody(containing("Sam23@mail.com"))
                .withRequestBody(containing("Sam@9908"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\":\"User registered successfully\"}")));


        wireMockServer.stubFor(post(urlEqualTo("/login"))
                .withRequestBody(containing("Sam23@mail.com"))
                .withRequestBody(containing("Sam@9908"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\":\"User logged in successfully\"}")));

        wireMockServer.stubFor(post(urlEqualTo("/login"))
                .withRequestBody(containing("Sam23@mail.com"))
                .withRequestBody(notContaining("Sam@9908"))
                .willReturn(aResponse()
                        .withStatus(401)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\":\"Invalid credentials password\"}")));

    }

    @Test
    public void testRegisterUser() {

        RestAssured.baseURI = "http://localhost:8090";

        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"email\":\"Sam23@mail.com\",\"password\":\"Sam@9908\"}")
                .when()
                .post("/register")
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 201);
        System.out.println("Response : " + response.getBody().asString());
    }

    @Test
    public void testLoginUser() {

        RestAssured.baseURI = "http://localhost:8090";

        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"email\":\"Sam23@mail.com\",\"password\":\"Sam@9908\"}")
                .when()
                .post("/login")
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Response : " + response.getBody().asString());
    }

    @Test
    public void testLoginUserWithInvalidPassword() {

        RestAssured.baseURI = "http://localhost:8090";

        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"email\":\"Sam23@mail.com\",\"password\":\"Sam@1907\"}")
                .when()
                .post("/login")
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 401);
        System.out.println("Response : " + response.getBody().asString());
    }

    @AfterClass
    public static void stopServer()
    {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
        }
    }
}